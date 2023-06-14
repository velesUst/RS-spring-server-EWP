package com.ewp.server.persistence.repository.token;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ewp.server.persistence.entity.secure.AuthUser;
import com.ewp.server.secure.TokenUtils;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author feuyeux@gmail.com
 */
@Repository
public class HelloTokenRepository {
    Cache<String, AuthUser> accessTokenTable = CacheBuilder.newBuilder()
            .expireAfterWrite(TokenUtils.ACCESS_EXPIRE, TimeUnit.MINUTES).build();
    Cache<String, AuthUser> freshTokenTable = CacheBuilder.newBuilder()
            .expireAfterWrite(TokenUtils.REFRESH_EXPIRE, TimeUnit.DAYS).build();

    public void storeAccessToken(String tokenId, AuthUser user) {
        accessTokenTable.put(tokenId, user);
    }

    public void storeFreshToken(String tokenId, AuthUser user) {
        freshTokenTable.put(tokenId, user);
    }

    public AuthUser getAuthFromAccessToken(String tokenId) {
        return accessTokenTable.getIfPresent(tokenId);
    }

    public AuthUser getAuthFromRefreshToken(String tokenId) {
        return freshTokenTable.getIfPresent(tokenId);
    }

    public void deleteAccessToken(String tokenId) {
        accessTokenTable.invalidate(tokenId);
    }

    public void deleteFreshToken(String tokenId) {
        freshTokenTable.invalidate(tokenId);
    }
}
