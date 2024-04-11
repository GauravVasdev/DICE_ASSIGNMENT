package com.dice.commonservice.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class OAuth1Interceptor implements RequestInterceptor {

    private final String consumerKey;
    private final String consumerSecret;
    private final String accessToken;
    private final String accessTokenSecret;

    public OAuth1Interceptor(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    @Override
    public void apply(RequestTemplate template) {
        String nonce = UUID.randomUUID().toString().replaceAll("-", "");
        long timestamp = System.currentTimeMillis() / 1000L;

        String signatureBaseString = // Construct signature base string
                template.method().toUpperCase() + "&" +
                        urlEncode(template.url()) + "&" +
                        urlEncode(
                                "oauth_consumer_key=" + consumerKey +
                                        "&oauth_nonce=" + nonce +
                                        "&oauth_signature_method=HMAC-SHA1" +
                                        "&oauth_timestamp=" + timestamp +
                                        "&oauth_token=" + accessToken +
                                        "&oauth_version=1.0"
                        );

        String signingKey = urlEncode(consumerSecret) + "&" + urlEncode(accessTokenSecret);
        String oauthSignature = generateSignature(signatureBaseString, signingKey);

        template.header("Authorization", // Construct OAuth 1.0a header
                "OAuth oauth_consumer_key=\"" + consumerKey + "\", " +
                        "oauth_nonce=\"" + nonce + "\", " +
                        "oauth_signature_method=\"HMAC-SHA1\", " +
                        "oauth_timestamp=\"" + timestamp + "\", " +
                        "oauth_token=\"" + accessToken + "\", " +
                        "oauth_version=\"1.0\", " +
                        "oauth_signature=\"" + oauthSignature + "\"");
    }

    private String generateSignature(String baseString, String keyString) {
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            byte[] bytes = mac.doFinal(baseString.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(bytes));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String urlEncode(String s) {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8")
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

