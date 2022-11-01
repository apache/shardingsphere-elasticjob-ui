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

import org.apache.shardingsphere.elasticjob.lite.ui.domain.EventTraceDataSourceConfiguration;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.EventTraceDataSourceFactory;
import org.apache.shardingsphere.elasticjob.lite.ui.exception.DriverClassNotInWhitelistException;
import org.apache.shardingsphere.elasticjob.lite.ui.service.EventTraceDataSourceConfigurationService;
import org.apache.shardingsphere.elasticjob.lite.ui.util.SessionEventTraceDataSourceConfiguration;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResult;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Driver;
import java.util.Collection;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * Event trace data source RESTful API.
 */
@RestController
@RequestMapping("/api/data-source")
public final class EventTraceDataSourceController {
    
    public static final String DATA_SOURCE_CONFIG_KEY = "data_source_config_key";
    
    private final EventTraceDataSourceConfigurationService eventTraceDataSourceConfigurationService;
    
    @Value("#{${dynamic.datasource.allowed-driver-classes:{}}}")
    private Set<String> dynamicDataSourceAllowedDriverClasses;
    
    @Autowired
    public EventTraceDataSourceController(final EventTraceDataSourceConfigurationService eventTraceDataSourceConfigurationService) {
        this.eventTraceDataSourceConfigurationService = eventTraceDataSourceConfigurationService;
    }
    
    /**
     * Get all available driver classes.
     *
     * @return Driver classes
     */
    @GetMapping("/drivers")
    public ResponseResult<Collection<String>> availableDrivers() {
        Collection<String> result = new HashSet<>();
        ServiceLoader.load(Driver.class).forEach(each -> result.add(each.getClass().getName()));
        return ResponseResultUtil.build(result);
    }
    
    /**
     * Judge whether event trace data source is activated.
     *
     * @param request HTTP request
     * @return event trace data source is activated or not
     */
    @GetMapping("/activated")
    public boolean activated(final HttpServletRequest request) {
        return eventTraceDataSourceConfigurationService.loadActivated().isPresent();
    }
    
    /**
     * Load event trace data source configuration.
     *
     * @param request HTTP request
     * @return event trace data source configurations
     */
    @GetMapping("/load")
    public ResponseResult<Collection<EventTraceDataSourceConfiguration>> load(final HttpServletRequest request) {
        eventTraceDataSourceConfigurationService.loadActivated().ifPresent(eventTraceDataSourceConfig -> setDataSourceNameToSession(eventTraceDataSourceConfig, request.getSession()));
        return ResponseResultUtil.build(eventTraceDataSourceConfigurationService.loadAll().getEventTraceDataSourceConfiguration());
    }
    
    /**
     * Add event trace data source configuration.
     *
     * @param config event trace data source configuration
     * @return success to added or not
     */
    @PostMapping("/add")
    public ResponseResult<Boolean> add(@RequestBody final EventTraceDataSourceConfiguration config) {
        failedIfDriverClassNotAllowed(config.getDriver());
        return ResponseResultUtil.build(eventTraceDataSourceConfigurationService.add(config));
    }
    
    /**
     * Delete event trace data source configuration.
     *
     * @param config event trace data source configuration
     */
    @DeleteMapping
    public ResponseResult delete(@RequestBody final EventTraceDataSourceConfiguration config) {
        eventTraceDataSourceConfigurationService.delete(config.getName());
        return ResponseResultUtil.success();
    }
    
    /**
     * Test event trace data source connection.
     *
     * @param config  event trace data source configuration
     * @param request HTTP request
     * @return success or not
     */
    @PostMapping(value = "/connectTest")
    public ResponseResult<Boolean> connectTest(@RequestBody final EventTraceDataSourceConfiguration config, final HttpServletRequest request) {
        failedIfDriverClassNotAllowed(config.getDriver());
        setDataSourceNameToSession(config, request.getSession());
        return ResponseResultUtil.build(true);
    }
    
    private void failedIfDriverClassNotAllowed(final String driverClass) {
        if (!dynamicDataSourceAllowedDriverClasses.contains(driverClass)) {
            throw new DriverClassNotInWhitelistException(driverClass);
        }
    }
    
    /**
     * Connect event trace data source.
     *
     * @param config  event trace data source
     * @param request HTTP request
     * @return success or not
     */
    @PostMapping(value = "/connect")
    public ResponseResult<Boolean> connect(@RequestBody final EventTraceDataSourceConfiguration config, final HttpServletRequest request) {
        setDataSourceNameToSession(eventTraceDataSourceConfigurationService.find(config.getName(), eventTraceDataSourceConfigurationService.loadAll()), request.getSession());
        eventTraceDataSourceConfigurationService.load(config.getName());
        return ResponseResultUtil.build(true);
    }
    
    private void setDataSourceNameToSession(final EventTraceDataSourceConfiguration dataSourceConfig, final HttpSession session) {
        session.setAttribute(DATA_SOURCE_CONFIG_KEY, dataSourceConfig);
        EventTraceDataSourceFactory.createEventTraceDataSource(dataSourceConfig.getDriver(), dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
        SessionEventTraceDataSourceConfiguration.setDataSourceConfiguration((EventTraceDataSourceConfiguration) session.getAttribute(DATA_SOURCE_CONFIG_KEY));
    }
}
