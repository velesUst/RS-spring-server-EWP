package com.ewp.server.secure.jwt;

import lombok.extern.slf4j.Slf4j;
import com.ewp.server.secure.HelloSecurityConfig;
import com.ewp.server.secure.TokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;

@Slf4j
@Configuration
@EnableRSocketSecurity
public class HelloJwtSecurityConfig extends HelloSecurityConfig {
    /**
     * authorization
     */
    @Bean
    PayloadSocketAcceptorInterceptor authorization(RSocketSecurity rsocketSecurity) {
        RSocketSecurity security = pattern(rsocketSecurity)
                .jwt(jwtSpec -> {
                    try {
                        jwtSpec.authenticationManager(jwtReactiveAuthenticationManager(jwtDecoder()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return security.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() throws Exception {
        return TokenUtils.jwtAccessTokenDecoder();
    }

    @Bean
    public JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager(ReactiveJwtDecoder decoder) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_");
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        JwtReactiveAuthenticationManager manager = new JwtReactiveAuthenticationManager(decoder);
        manager.setJwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(converter));
        return manager;
    }

    @Bean
    RSocketMessageHandler messageHandler(RSocketStrategies strategies) {
        return getMessageHandler(strategies);
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}