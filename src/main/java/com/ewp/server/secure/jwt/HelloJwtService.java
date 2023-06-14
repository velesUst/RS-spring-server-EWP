package com.ewp.server.secure.jwt;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.ewp.server.persistence.entity.secure.AuthToken;
import com.ewp.server.persistence.entity.secure.AuthUser;
import com.ewp.server.persistence.entity.secure.UserToken;
import com.ewp.server.persistence.repository.identitiy.TempUserRepository;
import com.ewp.server.persistence.repository.token.HelloTokenRepository;
import com.ewp.server.secure.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class HelloJwtService {
    @Autowired
    private TempUserRepository userRepository;
    @Autowired
    private HelloTokenRepository tokenRepository;
    @Setter
    private String tokenId;

    public AuthUser authenticate(String principal, String credential) {
        log.info("principal={},credential={}", principal, credential);
        try {
            AuthUser user = userRepository.retrieve(principal);
            if (user.getPassword().equals(credential)) {
                return user;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public Mono<AuthUser> authenticate(String refreshToken) {
        ReactiveJwtDecoder reactiveJwtDecoder = TokenUtils.getRefreshTokenDecoder();
        return reactiveJwtDecoder.decode(refreshToken).map(jwt -> {
            try {
                AuthUser user = AuthUser.builder().userId(jwt.getSubject()).role(jwt.getClaim("scope")).build();
                log.info("verify successfully. user:{}", user);
                AuthUser auth = tokenRepository.getAuthFromRefreshToken(jwt.getId());
                if (user.equals(auth)) {
                    return user;
                }
            } catch (Exception e) {
                log.error("", e);
            }
            return new AuthUser();
        });
    }

    public AuthToken signToken(AuthUser user) {
        AuthToken token = new AuthToken();
        UserToken accessToken = TokenUtils.generateAccessToken(user);
        tokenRepository.storeAccessToken(accessToken.getTokenId(), accessToken.getUser());
        UserToken refreshToken = TokenUtils.generateRefreshToken(user);
        tokenRepository.storeFreshToken(refreshToken.getTokenId(), refreshToken.getUser());
        token.setAccessToken(accessToken.getToken());
        token.setRefreshToken(refreshToken.getToken());
        return token;
    }

    public void revokeAccessToken() {
        tokenRepository.deleteAccessToken(tokenId);
    }
}
