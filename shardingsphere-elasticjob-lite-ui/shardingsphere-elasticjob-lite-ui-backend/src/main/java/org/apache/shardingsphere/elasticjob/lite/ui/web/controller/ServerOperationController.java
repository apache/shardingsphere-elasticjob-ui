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

import org.apache.shardingsphere.elasticjob.lite.lifecycle.domain.JobBriefInfo;
import org.apache.shardingsphere.elasticjob.lite.lifecycle.domain.ServerBriefInfo;
import org.apache.shardingsphere.elasticjob.lite.ui.service.JobAPIService;
import org.apache.shardingsphere.elasticjob.lite.ui.util.SessionRegistryCenterConfiguration;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResult;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Server operation RESTful API.
 */
@RestController
@RequestMapping("/api/servers")
public final class ServerOperationController {
    
    private JobAPIService jobAPIService;
    
    @Autowired
    public ServerOperationController(final JobAPIService jobAPIService) {
        this.jobAPIService = jobAPIService;
    }
    
    /**
     * Get servers total count.
     * 
     * @return servers total count
     */
    @GetMapping("/count")
    public int getServersTotalCount() {
        return jobAPIService.getServerStatisticsAPI().getServersTotalCount();
    }
    
    /**
     * Get all servers brief info.
     * 
     * @return all servers brief info
     */
    @GetMapping("/getAllServersBriefInfo")
    public ResponseResult<Collection<ServerBriefInfo>> getAllServersBriefInfo() {
        Collection<ServerBriefInfo> data =  Objects.nonNull(SessionRegistryCenterConfiguration.getRegistryCenterConfiguration()) ?
                jobAPIService.getServerStatisticsAPI().getAllServersBriefInfo() : Collections.emptyList();
        return ResponseResultUtil.build(data);
    }
    
    /**
     * Disable server.
     *
     * @param serverIp server IP address
     */
    @PostMapping("/{serverIp}/disable")
    public ResponseResult<Boolean> disableServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().disable(null, serverIp);
        return ResponseResultUtil.build(Boolean.TRUE);
    }
    
    /**
     * Enable server.
     *
     * @param serverIp server IP address
     */
    @PostMapping("/{serverIp}/enable")
    public ResponseResult<Boolean> enableServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().enable(null, serverIp);
        return ResponseResultUtil.build(Boolean.TRUE);
    }
    
    /**
     * Shutdown server.
     *
     * @param serverIp server IP address
     */
    @PostMapping("/{serverIp}/shutdown")
    public ResponseResult<Boolean> shutdownServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().shutdown(null, serverIp);
        return ResponseResultUtil.build(Boolean.TRUE);
    }
    
    /**
     * Remove server.
     *
     * @param serverIp server IP address
     */
    @DeleteMapping("/{serverIp}")
    public ResponseResult<Boolean> removeServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().remove(null, serverIp);
        return ResponseResultUtil.build(Boolean.TRUE);
    }
    
    /**
     * Get jobs.
     *
     * @param serverIp server IP address
     * @return Job brief info
     */
    @GetMapping(value = "/{serverIp}/jobs")
    public ResponseResult<Collection<JobBriefInfo>> getJobs(@PathVariable("serverIp") final String serverIp) {
        Collection<JobBriefInfo> data = jobAPIService.getJobStatisticsAPI().getJobsBriefInfo(serverIp);
        return ResponseResultUtil.build(data);
    }
    
    /**
     * Disable server job.
     * 
     * @param serverIp server IP address
     * @param jobName job name
     */
    @PostMapping(value = "/{serverIp}/jobs/{jobName}/disable")
    public ResponseResult<Boolean> disableServerJob(@PathVariable("serverIp") final String serverIp, @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().disable(jobName, serverIp);
        return ResponseResultUtil.build(Boolean.TRUE);
    }
    
    /**
     * Enable server job.
     *
     * @param serverIp server IP address
     * @param jobName job name
     */
    @PostMapping("/{serverIp}/jobs/{jobName}/enable")
    public ResponseResult<Boolean> enableServerJob(@PathVariable("serverIp") final String serverIp, @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().enable(jobName, serverIp);
        return ResponseResultUtil.build(Boolean.TRUE);
    }
    
    /**
     * Shutdown server job.
     *
     * @param serverIp server IP address
     * @param jobName job name
     */
    @PostMapping("/{serverIp}/jobs/{jobName}/shutdown")
    public ResponseResult<Boolean> shutdownServerJob(@PathVariable("serverIp") final String serverIp, @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().shutdown(jobName, serverIp);
        return ResponseResultUtil.build(Boolean.TRUE);
    }
    
    /**
     * Remove server job.
     *
     * @param serverIp server IP address
     * @param jobName job name
     */
    @DeleteMapping("/{serverIp}/jobs/{jobName}")
    public ResponseResult<Boolean> removeServerJob(@PathVariable("serverIp") final String serverIp, @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().remove(jobName, serverIp);
        return ResponseResultUtil.build(Boolean.TRUE);
    }
}
