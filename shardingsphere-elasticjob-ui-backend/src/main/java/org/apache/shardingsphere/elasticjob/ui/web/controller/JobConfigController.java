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

package org.apache.shardingsphere.elasticjob.ui.web.controller;

import org.apache.shardingsphere.elasticjob.lite.internal.config.pojo.JobConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.ui.service.JobAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Job configuration RESTful API.
 */
@RestController
@RequestMapping("/jobs/config")
public final class JobConfigController {
    
    private JobAPIService jobAPIService;
    
    @Autowired
    public JobConfigController(final JobAPIService jobAPIService) {
        this.jobAPIService = jobAPIService;
    }
    
    /**
     * Get job configuration.
     *
     * @param jobName job name
     * @return job configuration
     */
    @GetMapping(value = "/{jobName}")
    public JobConfigurationPOJO getJobConfig(@PathVariable("jobName") final String jobName) {
        return jobAPIService.getJobConfigurationAPI().getJobConfiguration(jobName);
    }
    
    /**
     * Update job configuration.
     *
     * @param jobConfiguration job configuration
     */
    @PutMapping("/updateJobConfig")
    public void updateJobConfig(@RequestBody final JobConfigurationPOJO jobConfiguration) {
        jobAPIService.getJobConfigurationAPI().updateJobConfiguration(jobConfiguration);
    }
    
    /**
     * Remove job configuration.
     *
     * @param jobName job name
     */
    @DeleteMapping("/{jobName}")
    public void removeJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobConfigurationAPI().removeJobConfiguration(jobName);
    }
}
