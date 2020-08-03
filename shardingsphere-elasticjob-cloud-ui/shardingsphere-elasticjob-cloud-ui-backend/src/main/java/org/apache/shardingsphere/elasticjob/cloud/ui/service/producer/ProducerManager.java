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

package org.apache.shardingsphere.elasticjob.cloud.ui.service.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.cloud.config.pojo.CloudJobConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.cloud.ui.exception.AppConfigurationException;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.app.CloudAppConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.app.pojo.CloudAppConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.job.CloudJobConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.state.disable.job.DisableJobService;
import org.apache.shardingsphere.elasticjob.infra.exception.JobConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Producer manager.
 */
@Slf4j
@Service
public final class ProducerManager {
    
    @Autowired
    private CloudAppConfigurationService appConfigService;
    
    @Autowired
    private CloudJobConfigurationService configService;
    
    @Autowired
    private DisableJobService disableJobService;
    
    /**
     * Register the job.
     * 
     * @param cloudJobConfig cloud job configuration
     */
    public void register(final CloudJobConfigurationPOJO cloudJobConfig) {
        if (disableJobService.isDisabled(cloudJobConfig.getJobName())) {
            throw new JobConfigurationException("Job '%s' has been disable.", cloudJobConfig.getJobName());
        }
        Optional<CloudAppConfigurationPOJO> appConfigFromZk = appConfigService.load(cloudJobConfig.getAppName());
        if (!appConfigFromZk.isPresent()) {
            throw new AppConfigurationException("Register app '%s' firstly.", cloudJobConfig.getAppName());
        }
        Optional<CloudJobConfigurationPOJO> jobConfigFromZk = configService.load(cloudJobConfig.getJobName());
        if (jobConfigFromZk.isPresent()) {
            throw new JobConfigurationException("Job '%s' already existed.", cloudJobConfig.getJobName());
        }
        configService.add(cloudJobConfig);
    }
    
    /**
     * Update the job.
     *
     * @param cloudJobConfig cloud job configuration
     */
    public void update(final CloudJobConfigurationPOJO cloudJobConfig) {
        Optional<CloudJobConfigurationPOJO> jobConfigFromZk = configService.load(cloudJobConfig.getJobName());
        if (!jobConfigFromZk.isPresent()) {
            throw new JobConfigurationException("Cannot found job '%s', please register first.", cloudJobConfig.getJobName());
        }
        configService.update(cloudJobConfig);
    }
    
    /**
     * Deregister the job.
     * 
     * @param jobName job name
     */
    public void deregister(final String jobName) {
        Optional<CloudJobConfigurationPOJO> jobConfig = configService.load(jobName);
        if (jobConfig.isPresent()) {
            disableJobService.remove(jobName);
            configService.remove(jobName);
        }
    }
}
