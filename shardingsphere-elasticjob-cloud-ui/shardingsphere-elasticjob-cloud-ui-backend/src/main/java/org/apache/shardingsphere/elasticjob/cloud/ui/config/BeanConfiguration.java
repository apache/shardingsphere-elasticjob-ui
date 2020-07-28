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

import com.netflix.fenzo.TaskScheduler;
import lombok.extern.slf4j.Slf4j;
import org.apache.mesos.MesosSchedulerDriver;
import org.apache.mesos.Protos;
import org.apache.mesos.SchedulerDriver;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.config.app.CloudAppConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.config.job.CloudJobConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.env.BootstrapEnvironment;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.env.MesosConfiguration;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.ha.FrameworkIDService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.mesos.FacadeService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.mesos.MesosStateService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.mesos.SchedulerEngine;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.producer.ProducerManager;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.state.disable.app.DisableAppService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.statistics.StatisticManager;
import org.apache.shardingsphere.elasticjob.cloud.ui.web.controller.search.JobEventRdbSearch;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.tracing.JobEventBus;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@Slf4j
public class BeanConfiguration {
    
    private static final String WEB_UI_PROTOCOL = "http://";
    
    @Bean
    public CoordinatorRegistryCenter regCenter() {
        CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(BootstrapEnvironment.getINSTANCE().getZookeeperConfiguration());
        registryCenter.init();
        return registryCenter;
    }
    
    @Bean
    public CloudAppConfigurationService appConfigService() {
        return new CloudAppConfigurationService(regCenter());
    }
    
    @Bean
    public CloudJobConfigurationService jobConfigService() {
        return new CloudJobConfigurationService(regCenter());
    }
    
    @Bean
    public MesosStateService mesosStateService() {
        return new MesosStateService(regCenter());
    }
    
    @Bean
    public DisableAppService disableAppService() {
        return new DisableAppService(regCenter());
    }
    
    @Bean
    public ProducerManager producerManager() {
        return new ProducerManager(schedulerDriver(), regCenter());
    }
    
    @Bean
    public FacadeService facadeService() {
        return new FacadeService(regCenter());
    }
    
    @Bean
    public StatisticManager statisticManager() {
        return StatisticManager.getInstance(regCenter(), BootstrapEnvironment.getINSTANCE().getTracingConfiguration().orElse(null));
    }
    
    @Bean
    public FrameworkIDService frameworkIDService() {
        return new FrameworkIDService(regCenter());
    }
    
    @Bean
    public TaskScheduler taskScheduler() {
        return new TaskScheduler.Builder()
                .withLeaseOfferExpirySecs(1000000000L)
                .withLeaseRejectAction(lease -> {
                    log.warn("Declining offer on '{}'", lease.hostname());
                    schedulerDriver().declineOffer(lease.getOffer().getId());
                }).build();
    }
    
    @Bean
    public JobEventBus jobEventBus() {
        Optional<TracingConfiguration> tracingConfiguration = BootstrapEnvironment.getINSTANCE().getTracingConfiguration();
        return tracingConfiguration.map(JobEventBus::new).orElseGet(JobEventBus::new);
    }
    
    @Bean
    public SchedulerDriver schedulerDriver() {
        Protos.FrameworkInfo.Builder builder = Protos.FrameworkInfo.newBuilder();
        frameworkIDService().fetch().ifPresent(frameworkID -> builder.setId(Protos.FrameworkID.newBuilder().setValue(frameworkID).build()));
        Optional<String> role = BootstrapEnvironment.getINSTANCE().getMesosRole();
        String frameworkName = MesosConfiguration.FRAMEWORK_NAME;
        if (role.isPresent()) {
            builder.setRole(role.get());
            frameworkName += "-" + role.get();
        }
        builder.addCapabilitiesBuilder().setType(Protos.FrameworkInfo.Capability.Type.PARTITION_AWARE);
        MesosConfiguration mesosConfig = BootstrapEnvironment.getINSTANCE().getMesosConfiguration();
        Protos.FrameworkInfo frameworkInfo = builder.setUser(mesosConfig.getUser()).setName(frameworkName)
                .setHostname(mesosConfig.getHostname()).setFailoverTimeout(MesosConfiguration.FRAMEWORK_FAILOVER_TIMEOUT_SECONDS)
                .setWebuiUrl(WEB_UI_PROTOCOL + BootstrapEnvironment.getINSTANCE().getFrameworkHostPort()).setCheckpoint(true).build();
        return null; //new MesosSchedulerDriver(new SchedulerEngine(taskScheduler(), facadeService(), jobEventBus(), frameworkIDService(), statisticManager()), frameworkInfo, mesosConfig.getUrl());
    }
    
    @Bean
    public JobEventRdbSearch jobEventRdbSearch() {
        Optional<TracingConfiguration> tracingConfiguration = BootstrapEnvironment.getINSTANCE().getTracingConfiguration();
        return tracingConfiguration.map(each -> new JobEventRdbSearch((DataSource) each.getStorage(), true)).orElse(new JobEventRdbSearch(null, false));
    }
}
