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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import lombok.Setter;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResultUtil;
import org.casbin.casdoor.config.CasdoorConfiguration;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Authentication filter.
 */
public final class AuthenticationFilter implements Filter {
    
    private static final String LOGIN_URI = "/api/login";

    private static final String CASDOOR_LOGIN_URL = "/api/casdoor-login-url";

    private static final String CASDOOR_LOGIN = "/api/casdoor-login";
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Setter
    private UserAuthenticationService userAuthenticationService;

    private CasdoorAuthService casdoorAuthService;

    @Override
    public void init(final FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        CasdoorConfiguration config = ctx.getBean(CasdoorConfiguration.class);
        casdoorAuthService = new CasdoorAuthService(config);
    }
    
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        if (LOGIN_URI.equals(httpRequest.getRequestURI())) {
            handleLogin(httpRequest, httpResponse);
            return;
        }
        if (CASDOOR_LOGIN_URL.equals(httpRequest.getRequestURI())) {
            handleCasdoorLoginUrl(httpRequest, httpResponse);
            return;
        }
        if (CASDOOR_LOGIN.equals(httpRequest.getRequestURI())) {
            handleCasdoorLogin(httpRequest, httpResponse);
            return;
        }
        String accessToken = httpRequest.getHeader("Access-Token");
        if (Strings.isNullOrEmpty(accessToken) || !userAuthenticationService.isValidToken(accessToken) && !isValidCasdoorToken(accessToken)) {
            respondWithUnauthorized(httpResponse);
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }
    
    @Override
    public void destroy() {
    }
    
    private void handleLogin(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse) {
        try {
            UserAccount user = objectMapper.readValue(httpRequest.getReader(), UserAccount.class);
            AuthenticationResult authenticationResult = userAuthenticationService.checkUser(user);
            if (!authenticationResult.isSuccess()) {
                respondWithUnauthorized(httpResponse);
                return;
            }
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            Map<String, Object> result = new HashMap<>(2, 1);
            result.put("username", authenticationResult.getUsername());
            result.put("accessToken", userAuthenticationService.getToken(authenticationResult.getUsername()));
            objectMapper.writeValue(httpResponse.getWriter(), ResponseResultUtil.build(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCasdoorLoginUrl(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse) {
        try {
            String origin = httpRequest.getParameter("origin");
            String url = casdoorAuthService.getSigninUrl(origin + "/login/casdoor");
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            Map<String, Object> result = new HashMap<>(1, 1);
            result.put("casdoorLoginUrl", url);
            objectMapper.writeValue(httpResponse.getWriter(), ResponseResultUtil.build(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCasdoorLogin(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse) {
        try {
            OAuthCode code = objectMapper.readValue(httpRequest.getReader(), OAuthCode.class);
            String token = casdoorAuthService.getOAuthToken(code.getCode(), code.getState());
            CasdoorUser user = casdoorAuthService.parseJwtToken(token);
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            Map<String, Object> result = new HashMap<>(4, 1);
            result.put("username", user.getName());
            result.put("accessToken", token);
            objectMapper.writeValue(httpResponse.getWriter(), ResponseResultUtil.build(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isValidCasdoorToken(final String accessToken) {
        try {
            return null == casdoorAuthService.parseJwtToken(accessToken);
        } catch (final Exception ignored) {
            return false;
        }
    }
    
    private void respondWithUnauthorized(final HttpServletResponse httpResponse) throws IOException {
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(httpResponse.getWriter(), ResponseResultUtil.handleUnauthorizedException("Unauthorized."));
    }
}
