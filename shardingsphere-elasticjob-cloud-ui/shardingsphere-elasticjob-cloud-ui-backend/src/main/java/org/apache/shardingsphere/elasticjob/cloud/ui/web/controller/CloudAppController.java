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

import org.apache.mesos.Protos.ExecutorID;
import org.apache.mesos.Protos.SlaveID;
import org.apache.shardingsphere.elasticjob.cloud.config.pojo.CloudJobConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.config.app.CloudAppConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.config.app.pojo.CloudAppConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.exception.AppConfigurationException;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.mesos.MesosStateService.ExecutorStateInfo;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.producer.ProducerManager;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.CloudServiceFactory;
import org.apache.shardingsphere.elasticjob.cloud.ui.web.response.ResponseResult;
import org.apache.shardingsphere.elasticjob.cloud.ui.web.response.ResponseResultUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

/**
 * Cloud app controller.
 */
@RestController
@RequestMapping("/api/app")
public final class CloudAppController {
    
    private static ProducerManager producerManager;
    
    /**
     * Register app config.
     * @param appConfig cloud app config
     */
    @PostMapping
    public void register(@RequestBody final CloudAppConfigurationPOJO appConfig) {
        CloudAppConfigurationService appConfigService = CloudServiceFactory.getAppConfigService();
        Optional<CloudAppConfigurationPOJO> appConfigFromZk = appConfigService.load(appConfig.getAppName());
        if (appConfigFromZk.isPresent()) {
            throw new AppConfigurationException("app '%s' already existed.", appConfig.getAppName());
        }
        appConfigService.add(appConfig);
    }
    
    /**
     * Update app config.
     * @param appConfig cloud app config
     */
    @PutMapping
    public void update(@RequestBody final CloudAppConfigurationPOJO appConfig) {
        CloudServiceFactory.getAppConfigService().update(appConfig);
    }
    
    /**
     * Query app config.
     * @param appName app name
     * @return cloud app config
     */
    @GetMapping("/{appName}")
    public ResponseResult<CloudAppConfigurationPOJO> detail(@PathVariable("appName") final String appName) {
        Optional<CloudAppConfigurationPOJO> appConfig = CloudServiceFactory.getAppConfigService().load(appName);
        return ResponseResultUtil.build(appConfig.orElse(null));
    }
    
    /**
     * Find all registered app configs.
     * @return collection of registered app configs
     */
    @GetMapping("/list")
    public ResponseResult<Collection<CloudAppConfigurationPOJO>> findAllApps() {
        return ResponseResultUtil.build(CloudServiceFactory.getAppConfigService().loadAll());
    }
    
    /**
     * Query the app is disabled or not.
     * @param appName app name
     * @return true is disabled, otherwise not
     */
    @GetMapping("/{appName}/disable")
    public boolean isDisabled(@PathVariable("appName") final String appName) {
        return CloudServiceFactory.getDisableAppService().isDisabled(appName);
    }
    
    /**
     * Disable app config.
     * @param appName app name
     */
    @PostMapping("/{appName}/disable")
    public void disable(@PathVariable("appName") final String appName) {
        if (CloudServiceFactory.getAppConfigService().load(appName).isPresent()) {
            CloudServiceFactory.getDisableAppService().add(appName);
            for (CloudJobConfigurationPOJO each : CloudServiceFactory.getJobConfigService().loadAll()) {
                if (appName.equals(each.getAppName())) {
                    producerManager.unschedule(each.getJobName());
                }
            }
        }
    }
    
    /**
     * Enable app.
     * @param appName app name
     */
    @PostMapping("/{appName}/enable")
    public void enable(@PathVariable("appName") final String appName) {
        if (CloudServiceFactory.getAppConfigService().load(appName).isPresent()) {
            CloudServiceFactory.getDisableAppService().remove(appName);
            for (CloudJobConfigurationPOJO each : CloudServiceFactory.getJobConfigService().loadAll()) {
                if (appName.equals(each.getAppName())) {
                    producerManager.reschedule(each.getJobName());
                }
            }
        }
    }
    
    /**
     * Deregister app.
     * @param appName app name
     */
    @DeleteMapping("/{appName}")
    public void deregister(@PathVariable("appName") final String appName) {
        if (CloudServiceFactory.getAppConfigService().load(appName).isPresent()) {
            removeAppAndJobConfigurations(appName);
            stopExecutors(appName);
        }
    }
    
    private void removeAppAndJobConfigurations(final String appName) {
        for (CloudJobConfigurationPOJO each : CloudServiceFactory.getJobConfigService().loadAll()) {
            if (appName.equals(each.getAppName())) {
                producerManager.deregister(each.getJobName());
            }
        }
        CloudServiceFactory.getDisableAppService().remove(appName);
        CloudServiceFactory.getAppConfigService().remove(appName);
    }
    
    private void stopExecutors(final String appName) {
        Collection<ExecutorStateInfo> executorBriefInfo = CloudServiceFactory.getMesosStateService().executors(appName);
        for (ExecutorStateInfo each : executorBriefInfo) {
            producerManager.sendFrameworkMessage(ExecutorID.newBuilder().setValue(each.getId()).build(),
                    SlaveID.newBuilder().setValue(each.getSlaveId()).build(), "STOP".getBytes());
        }
    }
}
