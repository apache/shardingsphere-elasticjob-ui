/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.elasticjob.lite.ui.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.common.base.Strings;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * User authentication service.
 */
@Component
@ConfigurationProperties(prefix = "auth")
@Setter
public final class UserAuthenticationService {
    
    private static final String JWT_TOKEN_ISSUER = "shardingsphere-elasticjob-ui";
    
    private final Algorithm algorithm = Algorithm.HMAC256(RandomStringUtils.randomAlphanumeric(256));
    
    private final JWTVerifier verifier = JWT.require(algorithm).withIssuer(JWT_TOKEN_ISSUER).build();
    
    private String username;
    
    private String password;
    
    private int tokenExpiresAfterSeconds = 3600;
    
    /**
     * Check user.
     *
     * @param userAccount user account
     * @return check success or failure
     */
    public AuthenticationResult checkUser(final UserAccount userAccount) {
        if (null == userAccount || Strings.isNullOrEmpty(userAccount.getUsername()) || Strings.isNullOrEmpty(userAccount.getPassword())) {
            return new AuthenticationResult(null, null, false);
        }
        if (username.equals(userAccount.getUsername()) && password.equals(userAccount.getPassword())) {
            return new AuthenticationResult(username, password, true);
        }
        return new AuthenticationResult(null, null, false);
    }
    
    /**
     * Get user authentication token.
     *
     * @return authentication token
     */
    public String getToken(final String username) {
        Map<String, Object> payload = new HashMap<>(1, 1);
        payload.put("username", username);
        Date expiresAt = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(tokenExpiresAfterSeconds));
        return JWT.create().withExpiresAt(expiresAt).withIssuer(JWT_TOKEN_ISSUER).withPayload(payload).sign(algorithm);
    }
    
    /**
     * Check if token is valid.
     *
     * @param token token
     * @return is valid
     */
    public boolean isValidToken(final String token) {
        try {
            verifier.verify(token);
        } catch (JWTVerificationException ignored) {
            return false;
        }
        return true;
    }
}
