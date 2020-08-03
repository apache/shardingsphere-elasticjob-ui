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

package org.apache.shardingsphere.elasticjob.cloud.ui.web.controller;

import org.apache.shardingsphere.elasticjob.cloud.config.pojo.CloudJobConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.cloud.ui.exception.AppConfigurationException;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.app.CloudAppConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.app.pojo.CloudAppConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.job.CloudJobConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.producer.ProducerManager;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.state.disable.app.DisableAppService;
import org.apache.shardingsphere.elasticjob.cloud.ui.web.dto.CloudAppConfiguration;
import org.apache.shardingsphere.elasticjob.cloud.ui.web.response.ResponseResult;
import org.apache.shardingsphere.elasticjob.cloud.ui.web.response.ResponseResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Cloud app controller.
 */
@RestController
@RequestMapping("/api/app")
public final class CloudAppController {
    
    @Autowired
    private ProducerManager producerManager;
    
    @Autowired
    private CloudAppConfigurationService appConfigService;
    
    @Autowired
    private DisableAppService disableAppService;
    
    @Autowired
    private CloudJobConfigurationService jobConfigService;
    
    /**
     * Register app config.
     * @param appConfig cloud app config
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody final CloudAppConfigurationPOJO appConfig) {
        Optional<CloudAppConfigurationPOJO> appConfigFromZk = appConfigService.load(appConfig.getAppName());
        if (appConfigFromZk.isPresent()) {
            throw new AppConfigurationException("app '%s' already existed.", appConfig.getAppName());
        }
        appConfigService.add(appConfig);
        return ResponseResultUtil.success();
    }
    
    /**
     * Update app config.
     * @param appConfig cloud app config
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody final CloudAppConfigurationPOJO appConfig) {
        appConfigService.update(appConfig);
        return ResponseResultUtil.success();
    }
    
    /**
     * Query app config.
     * @param appName app name
     * @return cloud app config
     */
    @GetMapping("/{appName}")
    public ResponseResult<CloudAppConfigurationPOJO> detail(@PathVariable("appName") final String appName) {
        Optional<CloudAppConfigurationPOJO> appConfig = appConfigService.load(appName);
        return ResponseResultUtil.build(appConfig.orElse(null));
    }
    
    /**
     * Find all registered app configs.
     * @return collection of registered app configs
     */
    @GetMapping("/list")
    public ResponseResult<Collection<CloudAppConfiguration>> findAllApps() {
        return ResponseResultUtil.build(build(appConfigService.loadAll()));
    }
    
    /**
     * Query the app is disabled or not.
     * @param appName app name
     * @return true is disabled, otherwise not
     */
    @GetMapping("/{appName}/disable")
    public boolean isDisabled(@PathVariable("appName") final String appName) {
        return disableAppService.isDisabled(appName);
    }
    
    /**
     * Disable app config.
     * @param appName app name
     */
    @PostMapping("/{appName}/disable")
    public ResponseResult disable(@PathVariable("appName") final String appName) {
        if (appConfigService.load(appName).isPresent()) {
            disableAppService.add(appName);
        }
        return ResponseResultUtil.success();
    }
    
    /**
     * Enable app.
     * @param appName app name
     */
    @PostMapping("/{appName}/enable")
    public ResponseResult enable(@PathVariable("appName") final String appName) {
        if (appConfigService.load(appName).isPresent()) {
            disableAppService.remove(appName);
        }
        return ResponseResultUtil.success();
    }
    
    /**
     * Deregister app.
     * @param appName app name
     */
    @DeleteMapping("/{appName}")
    public ResponseResult deregister(@PathVariable("appName") final String appName) {
        if (appConfigService.load(appName).isPresent()) {
            removeAppAndJobConfigurations(appName);
        }
        return ResponseResultUtil.success();
    }
    
    private void removeAppAndJobConfigurations(final String appName) {
        for (CloudJobConfigurationPOJO each : jobConfigService.loadAll()) {
            if (appName.equals(each.getAppName())) {
                producerManager.deregister(each.getJobName());
            }
        }
        disableAppService.remove(appName);
        appConfigService.remove(appName);
    }
    
    private Collection<CloudAppConfiguration> build(final Collection<CloudAppConfigurationPOJO> cloudAppConfigurationPOJOS) {
        return cloudAppConfigurationPOJOS.stream().map(each -> convert(each)).collect(Collectors.toList());
    }
    
    private CloudAppConfiguration convert(final CloudAppConfigurationPOJO cloudAppConfigurationPOJO) {
        CloudAppConfiguration cloudAppConfiguration = new CloudAppConfiguration();
        BeanUtils.copyProperties(cloudAppConfigurationPOJO, cloudAppConfiguration);
        cloudAppConfiguration.setDisabled(disableAppService.isDisabled(cloudAppConfigurationPOJO.getAppName()));
        return cloudAppConfiguration;
    }
}
