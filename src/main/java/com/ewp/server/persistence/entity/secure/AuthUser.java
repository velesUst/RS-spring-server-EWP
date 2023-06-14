package com.ewp.server.persistence.entity.secure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author feuyeux@gmail.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthUser {
    @JsonProperty("user_id")
    private String userId;
    @EqualsAndHashCode.Exclude
    private String password;
    private String role;
}
