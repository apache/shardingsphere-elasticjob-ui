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

package org.apache.shardingsphere.elasticjob.lite.ui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.lite.ui.config.DynamicDataSourceConfig;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.DataSourceFactory;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.EventTraceDataSourceConfiguration;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.EventTraceDataSourceConfigurations;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.GlobalConfiguration;
import org.apache.shardingsphere.elasticjob.lite.ui.exception.JdbcDriverNotFoundException;
import org.apache.shardingsphere.elasticjob.lite.ui.repository.ConfigurationsXmlRepository;
import org.apache.shardingsphere.elasticjob.lite.ui.repository.impl.ConfigurationsXmlRepositoryImpl;
import org.apache.shardingsphere.elasticjob.lite.ui.service.EventTraceDataSourceConfigurationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * Event trace data source configuration service implementation.
 */
@Slf4j
@Service
public final class EventTraceDataSourceConfigurationServiceImpl implements EventTraceDataSourceConfigurationService, InitializingBean {
    
    private final ConfigurationsXmlRepository configurationsXmlRepository = new ConfigurationsXmlRepositoryImpl();
    
    @Autowired
    private DynamicDataSourceConfig.DynamicDataSource dynamicDataSource;
    
    @Override
    public EventTraceDataSourceConfigurations loadAll() {
        return loadGlobal().getEventTraceDataSourceConfigurations();
    }
    
    @Override
    public EventTraceDataSourceConfiguration load(final String name) {
        GlobalConfiguration configs = loadGlobal();
        EventTraceDataSourceConfiguration result = find(name, configs.getEventTraceDataSourceConfigurations());
        setActivated(configs, result);
        // Activate the dataSource by data source name for spring boot
        DynamicDataSourceConfig.DynamicDataSourceContextHolder.setDataSourceName(name);
        return result;
    }
    
    @Override
    public EventTraceDataSourceConfiguration find(final String name, final EventTraceDataSourceConfigurations configs) {
        for (EventTraceDataSourceConfiguration each : configs.getEventTraceDataSourceConfiguration()) {
            if (name.equals(each.getName())) {
                return each;
            }
        }
        return null;
    }
    
    private void setActivated(final GlobalConfiguration configs, final EventTraceDataSourceConfiguration toBeConnectedConfig) {
        EventTraceDataSourceConfiguration activatedConfig = findActivatedDataSourceConfiguration(configs);
        if (!toBeConnectedConfig.equals(activatedConfig)) {
            if (null != activatedConfig) {
                activatedConfig.setActivated(false);
            }
            toBeConnectedConfig.setActivated(true);
            configurationsXmlRepository.save(configs);
        }
    }
    
    @Override
    public Optional<EventTraceDataSourceConfiguration> loadActivated() {
        return Optional.ofNullable(findActivatedDataSourceConfiguration(loadGlobal()));
    }
    
    private EventTraceDataSourceConfiguration findActivatedDataSourceConfiguration(final GlobalConfiguration configs) {
        for (EventTraceDataSourceConfiguration each : configs.getEventTraceDataSourceConfigurations().getEventTraceDataSourceConfiguration()) {
            if (each.isActivated()) {
                return each;
            }
        }
        return null;
    }
    
    @Override
    public boolean add(final EventTraceDataSourceConfiguration config) {
        GlobalConfiguration configs = loadGlobal();
        DataSource dataSource = DataSourceFactory.createDataSource(config);
        dynamicDataSource.addDataSource(config.getName(), dataSource);
        boolean result = configs.getEventTraceDataSourceConfigurations().getEventTraceDataSourceConfiguration().add(config);
        if (result) {
            configurationsXmlRepository.save(configs);
        }
        return result;
    }
    
    @Override
    public void delete(final String name) {
        GlobalConfiguration configs = loadGlobal();
        EventTraceDataSourceConfiguration toBeRemovedConfig = find(name, configs.getEventTraceDataSourceConfigurations());
        if (null != toBeRemovedConfig) {
            configs.getEventTraceDataSourceConfigurations().getEventTraceDataSourceConfiguration().remove(toBeRemovedConfig);
            configurationsXmlRepository.save(configs);
        }
    }
    
    private GlobalConfiguration loadGlobal() {
        GlobalConfiguration result = configurationsXmlRepository.load();
        if (null == result.getEventTraceDataSourceConfigurations()) {
            result.setEventTraceDataSourceConfigurations(new EventTraceDataSourceConfigurations());
        }
        return result;
    }
    
    @Override
    public void afterPropertiesSet() {
        for (EventTraceDataSourceConfiguration each : loadGlobal().getEventTraceDataSourceConfigurations().getEventTraceDataSourceConfiguration()) {
            try {
                afterLoad(each);
            } catch (final JdbcDriverNotFoundException ex) {
                log.error("Consider manually adding JDBC Driver JAR to classpath.", ex);
            }
        }
    }
    
    private void afterLoad(final EventTraceDataSourceConfiguration config) {
        DataSource dataSource = DataSourceFactory.createDataSource(config);
        dynamicDataSource.addDataSource(config.getName(), dataSource);
        DynamicDataSourceConfig.DynamicDataSourceContextHolder.setDataSourceName(config.getName());
    }
}
