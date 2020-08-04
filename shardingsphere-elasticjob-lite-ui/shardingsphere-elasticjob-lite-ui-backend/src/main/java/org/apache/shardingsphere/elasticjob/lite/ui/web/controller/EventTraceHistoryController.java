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

import org.apache.shardingsphere.elasticjob.lite.ui.dto.request.FindJobExecutionEventsRequest;
import org.apache.shardingsphere.elasticjob.lite.ui.dto.request.FindJobStatusTraceEventsRequest;
import org.apache.shardingsphere.elasticjob.lite.ui.dto.response.BasePageResponse;
import org.apache.shardingsphere.elasticjob.lite.ui.service.EventTraceDataSourceConfigurationService;
import org.apache.shardingsphere.elasticjob.lite.ui.service.EventTraceHistoryService;
import org.apache.shardingsphere.elasticjob.lite.ui.util.SessionEventTraceDataSourceConfiguration;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResult;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResultUtil;
import org.apache.shardingsphere.elasticjob.tracing.event.JobExecutionEvent;
import org.apache.shardingsphere.elasticjob.tracing.event.JobStatusTraceEvent;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Event trace history RESTful API.
 */
@RestController
@RequestMapping("/api/event-trace")
public final class EventTraceHistoryController {
    
    @Autowired
    private EventTraceHistoryService eventTraceHistoryService;
    
    @Autowired
    private EventTraceDataSourceConfigurationService eventTraceDataSourceConfigurationService;
    
    /**
     * Find job execution events.
     *
     * @param requestParams query criteria
     * @return job execution event trace result
     */
    @PostMapping(value = "/execution")
    public ResponseResult<BasePageResponse<JobExecutionEvent>> findJobExecutionEvents(@RequestBody final FindJobExecutionEventsRequest requestParams) {
        Page<JobExecutionEvent> jobExecutionEvents = eventTraceHistoryService.findJobExecutionEvents(requestParams);
        return ResponseResultUtil.build(BasePageResponse.of(jobExecutionEvents));
    }
    
    /**
     * Find job status trace events.
     *
     * @param requestParams query criteria
     * @return job status trace result
     */
    @PostMapping(value = "/status")
    public ResponseResult<BasePageResponse<JobStatusTraceEvent>> findJobStatusTraceEvents(@RequestBody final FindJobStatusTraceEventsRequest requestParams) {
        Page<JobStatusTraceEvent> jobStatusTraceEvents = eventTraceHistoryService.findJobStatusTraceEvents(requestParams);
        return ResponseResultUtil.build(BasePageResponse.of(jobStatusTraceEvents));
    }
    
    @ModelAttribute
    private void initDataSource() {
        eventTraceDataSourceConfigurationService.loadActivated().ifPresent(config -> SessionEventTraceDataSourceConfiguration.setDataSourceConfiguration(config));
    }
}
