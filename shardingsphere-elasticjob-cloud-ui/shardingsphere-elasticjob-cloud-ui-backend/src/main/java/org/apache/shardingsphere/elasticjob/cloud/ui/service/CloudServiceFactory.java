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

package org.apache.shardingsphere.elasticjob.cloud.ui.service;

import org.apache.shardingsphere.elasticjob.cloud.scheduler.config.app.CloudAppConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.config.job.CloudJobConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.mesos.MesosStateService;
import org.apache.shardingsphere.elasticjob.cloud.scheduler.state.disable.app.DisableAppService;

/**
 * Cloud service factory.
 */
public final class CloudServiceFactory {
    
    /**
     * Get app config service.
     *
     * @return app config service
     */
    public static CloudAppConfigurationService getAppConfigService() {
        return new CloudAppConfigurationService(RegistryCenterFactory.getRegistryCenter());
    }
    
    /**
     * Get job config service.
     *
     * @return job config service
     */
    public static CloudJobConfigurationService getJobConfigService() {
        return new CloudJobConfigurationService(RegistryCenterFactory.getRegistryCenter());
    }
    
    /**
     * Get mesos state service.
     *
     * @return mesos state service
     */
    public static MesosStateService getMesosStateService() {
        return new MesosStateService(RegistryCenterFactory.getRegistryCenter());
    }
    
    /**
     * Get disable app service.
     *
     * @return disable app service
     */
    public static DisableAppService getDisableAppService() {
        return new DisableAppService(RegistryCenterFactory.getRegistryCenter());
    }
}
