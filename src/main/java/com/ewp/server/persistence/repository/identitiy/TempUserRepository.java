package com.ewp.server.persistence.repository.identitiy;

import com.ewp.server.persistence.entity.secure.AuthUser;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Repository
public class TempUserRepository {
    private Map<String, AuthUser> userTable = new HashMap<>();

    @PostConstruct
    public void init() {
        AuthUser admin = AuthUser.builder().userId("9527").password("KauNgJikCeo").role("ADMIN").build();
        userTable.put("9527", admin);
        AuthUser user = AuthUser.builder().userId("0000").password("Zero4").role("USER").build();
        userTable.put("0000", user);
    }

    public AuthUser retrieve(String userId) {
        return userTable.get(userId);
    }
}
