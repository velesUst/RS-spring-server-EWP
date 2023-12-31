package com.ewp.server.persistence.entity.secure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author feuyeux@gmail.com
 */
@Data
public class AuthToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("error_message")
    private String errorMessage;
}
