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

import com.google.common.base.Strings;
import com.google.gson.Gson;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User authentication service.
 */
@Component
@ConfigurationProperties(prefix = "auth")
@Setter
public final class UserAuthenticationService {
    
    private String rootUsername;
    
    private String rootPassword;
    
    private String guestUsername;
    
    private String guestPassword;
    
    private final Base64 base64 = new Base64();
    
    private Gson gson = new Gson();
    
    /**
     * Check user.
     *
     * @param userAccount user account
     * @return check success or failure
     */
    public AuthenticationResult checkUser(final UserAccount userAccount) {
        if (null == userAccount || Strings.isNullOrEmpty(userAccount.getUsername()) || Strings.isNullOrEmpty(userAccount.getPassword())) {
            return new AuthenticationResult(null, null, false, false);
        }
        if (rootUsername.equals(userAccount.getUsername()) && rootPassword.equals(userAccount.getPassword())) {
            return new AuthenticationResult(rootUsername, rootPassword, true, false);
        }
        if (guestUsername.equals(userAccount.getUsername()) && guestPassword.equals(userAccount.getPassword())) {
            return new AuthenticationResult(guestUsername, guestPassword, true, true);
        }
        return new AuthenticationResult(null ,null, false, false);
    }
    
    /**
     * Get user authentication token.
     *
     * @return authentication token
     */
    public String getToken() {
        return base64.encodeToString(gson.toJson(this).getBytes());
    }
}
