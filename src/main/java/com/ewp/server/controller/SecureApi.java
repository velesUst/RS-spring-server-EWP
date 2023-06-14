package com.ewp.server.controller;

import lombok.extern.slf4j.Slf4j;
import com.ewp.server.persistence.entity.secure.AuthToken;
import com.ewp.server.persistence.entity.secure.AuthUser;
import com.ewp.server.secure.jwt.HelloJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class SecureApi {
    @Autowired
    private HelloJwtService helloJwtService;

    @MessageMapping("signin.v1")
    Mono<AuthToken> signin(AuthUser authUser) {
        log.info("signin input: {}", authUser);
        String principal = authUser.getUserId();
        String credential = authUser.getPassword();
        AuthUser user = helloJwtService.authenticate(principal, credential);
        AuthToken token = null;
        if (user != null) 
            token = helloJwtService.signToken(user);
        else {
            token = new AuthToken();
            token.setErrorMessage("No such user, or wrong password. Try again.");
        }   
        return Mono.just(token);
    }

    @MessageMapping("refresh.v1")
    Mono<AuthToken> refresh(String token) {
        String refreshToken = token.replace("\"", "");
        log.info("authenticate refreshToken: {}", refreshToken);
        Mono<AuthUser> mono = helloJwtService.authenticate(refreshToken);
        return mono.map(u -> helloJwtService.signToken(u));
    }

    @MessageMapping("signout.v1")
    public Mono<Void> signout(/*@Headers Map<String, Object> headers*/) {
        helloJwtService.revokeAccessToken();
        return Mono.empty();
    }

}