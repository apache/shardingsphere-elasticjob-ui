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

import com.google.common.base.Strings;
import lombok.Setter;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "event.trace")
@Setter
public final class EventTraceConfiguration {
    
    private String rdbDriver;

    private String rdbUrl;
    
    private String rdbUsername;

    private String rdbPassword;
    
    public Optional<TracingConfiguration> getTracingConfiguration() {
        
        if (!Strings.isNullOrEmpty(rdbDriver) && !Strings.isNullOrEmpty(rdbUrl) && !Strings.isNullOrEmpty(rdbUsername)) {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(rdbDriver);
            dataSource.setUrl(rdbUrl);
            dataSource.setUsername(rdbUsername);
            dataSource.setPassword(rdbPassword);
            return Optional.of(new TracingConfiguration<DataSource>("RDB", dataSource));
        }
        return Optional.empty();
    }
}
