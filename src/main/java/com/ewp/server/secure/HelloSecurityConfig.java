package com.ewp.server.secure;

import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;

import static com.ewp.server.persistence.entity.secure.AuthRole.ADMIN;
import static com.ewp.server.persistence.entity.secure.AuthRole.USER;

/**
 * @author feuyeux@gmail.com
 */
public class HelloSecurityConfig {
    protected RSocketSecurity pattern(RSocketSecurity security) {
        return security.authorizePayload(authorize -> authorize
                .route("signin.v1").permitAll()
                .route("echo-test").permitAll()
                .route("fetch.currentVal").permitAll()
                .route("refresh.v1").permitAll()
                .route("tweets.by.authort").permitAll()
				.route("customer.v1").hasAnyRole(USER, ADMIN)
                .route("signout.v1").authenticated()
                .route("hire.v1").hasRole(ADMIN)
                .route("fire.v1").hasRole(ADMIN)
                .route("info.v1").hasAnyRole(USER, ADMIN)
                .route("list.v1").hasAnyRole(USER, ADMIN)
                .anyRequest().authenticated()
                .anyExchange().permitAll()
        );
    }

    protected RSocketMessageHandler getMessageHandler(RSocketStrategies strategies) {
        RSocketMessageHandler mh = new RSocketMessageHandler();
        mh.getArgumentResolverConfigurer().addCustomResolver(
                new org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver());
        mh.setRSocketStrategies(strategies);
        return mh;
    }
}