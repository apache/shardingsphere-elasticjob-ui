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

package org.apache.shardingsphere.elasticjob.cloud.ui.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.cloud.ui.repository.StatisticRdbRepository;
import org.apache.shardingsphere.elasticjob.cloud.ui.web.controller.search.JobEventRdbSearch;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@Slf4j
public class BeanConfiguration {
    
    @Autowired
    private EventTraceConfiguration traceConfiguration;
    
    @Autowired
    private RegistryConfiguration registryConfiguration;
    
    @Bean
    public CoordinatorRegistryCenter regCenter() {
        CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(registryConfiguration.getZookeeperConfiguration());
        registryCenter.init();
        return registryCenter;
    }
    
    @Bean
    public StatisticRdbRepository rdbRepository() {
        Optional<TracingConfiguration> tracingConfiguration = traceConfiguration.getTracingConfiguration();
        return tracingConfiguration.map(each -> new StatisticRdbRepository((DataSource) each.getStorage(), true)).orElse(new StatisticRdbRepository(null, false));
    }
    
    @Bean
    public JobEventRdbSearch jobEventRdbSearch() {
        Optional<TracingConfiguration> tracingConfiguration = traceConfiguration.getTracingConfiguration();
        return tracingConfiguration.map(each -> new JobEventRdbSearch((DataSource) each.getStorage(), true)).orElse(new JobEventRdbSearch(null, false));
    }
}
