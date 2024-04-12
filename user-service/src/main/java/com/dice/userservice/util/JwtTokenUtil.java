package com.dice.userservice.util;



import com.dice.userservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtil {

    public static final long JWT_TOKEN_VALIDITY = 7 * 24 * 60 * 60;

    public String SECRET_KEY = "Gaurav123";

    /**
     * @param token this method username from token
     * @return username
     */
    public String getUserNameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getAllClaimsFromToken(token);
            username = claims.get("employee_email", String.class);
            return username;
        } catch (MalformedJwtException e) {
            username = "Invalid Token";
        }
        return username;
    }

    /**
     * @param token this method userFullName from token
     * @return userFullName
     */
    public String getUserFullNameFromToken(String token) {
        String userFullName = null;
        try {
            Claims claims = getAllClaimsFromToken(token);
            userFullName = claims.get("full_name", String.class);
            return userFullName;
        } catch (MalformedJwtException e) {
            userFullName ="Invalid token";
        }
        return userFullName;
    }

    public String getUserRoleFromToken(String token) {
        String role = null;
        try {
            Claims claims = getAllClaimsFromToken(token);
            role = claims.get("role", String.class);
            return role;
        } catch (MalformedJwtException e) {
            role = "Invalid Role";
        }
        return role;
    }

    public Boolean getTestUserFromToken(String token) {
        Boolean testUser = null;
        try {
            Claims claims = getAllClaimsFromToken(token);
            testUser = (Boolean) claims.get("TestUser");
        } catch (MalformedJwtException e) {
            testUser = false;
        }
        return testUser;
    }

    public String getUuidFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        var uuid = (String) claims.get("UUID");
        return uuid;
    }

    /**
     * @param token this method extract expiration from token using claims
     * @return Date
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * @param token this method extracts claims set of rules define to help to extract info from token
     *     using secret key
     * @return Claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * @param token check expiration of token
     * @return boolean value
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * this method generate token
     *
     * @param user
     * @return Token
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        String token = doGenerateToken(user);
        return token;
    }

    /**
     * @param
     * @param
     * @return token
     */
    private String doGenerateToken(User user) {

        return Jwts.builder()
                .claim("employee_email", user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}

