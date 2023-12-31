package com.ewp.server.persistence.entity.secure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author feuyeux@gmail.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {
    private String tokenId;
    private String token;
    private AuthUser user;
}
