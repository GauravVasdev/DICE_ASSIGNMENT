package com.dice.userservice.port.persistence;

import com.dice.userservice.model.Token;
import com.dice.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITokenRepository extends JpaRepository<Token, Long> {

    Token getByToken(String token);

    Token getByTokenUuid(String tokenUuid);

    Token findByUser(User user);
}
