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

package org.apache.shardingsphere.elasticjob.lite.ui.web.controller;

import org.apache.shardingsphere.elasticjob.lite.lifecycle.internal.reg.RegistryCenterFactory;
import org.apache.shardingsphere.elasticjob.reg.exception.RegException;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.RegistryCenterConfiguration;
import org.apache.shardingsphere.elasticjob.lite.ui.service.RegistryCenterConfigurationService;
import org.apache.shardingsphere.elasticjob.lite.ui.util.SessionRegistryCenterConfiguration;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResult;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * Registry center RESTful API.
 */
@RestController
@RequestMapping("/api/registry-center")
public final class RegistryCenterController {
    
    public static final String REG_CENTER_CONFIG_KEY = "reg_center_config_key";
    
    private RegistryCenterConfigurationService regCenterService;
    
    @Autowired
    public RegistryCenterController(final RegistryCenterConfigurationService regCenterService) {
        this.regCenterService = regCenterService;
    }
    
    /**
     * Judge whether registry center is activated.
     *
     * @return registry center is activated or not
     */
    @GetMapping("/activated")
    public ResponseResult<RegistryCenterConfiguration> activated() {
        return ResponseResultUtil.build(regCenterService.loadActivated().orElse(null));
    }
    
    /**
     * Load configuration from registry center.
     *
     * @param request HTTP request
     * @return registry center configurations
     */
    @GetMapping("/load")
    public ResponseResult<Collection<RegistryCenterConfiguration>> load(final HttpServletRequest request) {
        regCenterService.loadActivated().ifPresent(regCenterConfig -> setRegistryCenterNameToSession(regCenterConfig, request.getSession()));
        return ResponseResultUtil.build(regCenterService.loadAll().getRegistryCenterConfiguration());
    }
    
    /**
     * Add registry center.
     *
     * @param config registry center configuration
     * @return success to add or not
     */
    @PostMapping("/add")
    public ResponseResult<Boolean> add(@RequestBody final RegistryCenterConfiguration config) {
        return ResponseResultUtil.build(regCenterService.add(config));
    }
    
    /**
     * Delete registry center.
     *
     * @param config registry center configuration
     */
    @DeleteMapping
    public ResponseResult delete(@RequestBody final RegistryCenterConfiguration config) {
        regCenterService.delete(config.getName());
        return ResponseResultUtil.success();
    }
    
    /**
     * Connect to registry center.
     *
     * @param config  config of registry center
     * @param request HTTP request
     * @return connected or not
     */
    @PostMapping(value = "/connect")
    public ResponseResult<Boolean> connect(@RequestBody final RegistryCenterConfiguration config, final HttpServletRequest request) {
        boolean isConnected = setRegistryCenterNameToSession(regCenterService.find(config.getName(), regCenterService.loadAll()), request.getSession());
        if (isConnected) {
            regCenterService.load(config.getName());
        }
        return ResponseResultUtil.build(isConnected);
    }
    
    private boolean setRegistryCenterNameToSession(final RegistryCenterConfiguration regCenterConfig, final HttpSession session) {
        session.setAttribute(REG_CENTER_CONFIG_KEY, regCenterConfig);
        try {
            RegistryCenterFactory.createCoordinatorRegistryCenter(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), regCenterConfig.getDigest());
            SessionRegistryCenterConfiguration.setRegistryCenterConfiguration((RegistryCenterConfiguration) session.getAttribute(REG_CENTER_CONFIG_KEY));
        } catch (final RegException ex) {
            return false;
        }
        return true;
    }
}
