package com.ewp.server.secure.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import com.ewp.server.persistence.entity.secure.AuthUser;
import com.ewp.server.persistence.repository.token.HelloTokenRepository;
import com.ewp.server.utils.BeanUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import reactor.core.publisher.Mono;

public class HelloJwtDecoder implements ReactiveJwtDecoder {
    private final ReactiveJwtDecoder reactiveJwtDecoder;
    
    @Autowired
    HelloTokenRepository tokenRepository;// = BeanUtils.getBean(HelloTokenRepository.class);
    @Autowired
    HelloJwtService helloJwtService;// = BeanUtils.getBean(HelloJwtService.class);

    public HelloJwtDecoder(ReactiveJwtDecoder reactiveJwtDecoder) {
        this.reactiveJwtDecoder = reactiveJwtDecoder;
    }

    @Override
    public Mono<Jwt> decode(String token) throws JwtException {
        return reactiveJwtDecoder.decode(token).doOnNext(jwt -> {
            String id = jwt.getId();
            AuthUser auth = tokenRepository.getAuthFromAccessToken(id);
            if (auth == null) {
                throw new JwtException("Invalid AuthUser");
            }
            //TODO
            helloJwtService.setTokenId(id);
        });
    }
}
