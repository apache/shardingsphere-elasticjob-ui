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

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.cloud.config.CloudJobExecutionType;
import org.apache.shardingsphere.elasticjob.cloud.config.pojo.CloudJobConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.app.CloudAppConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.app.pojo.CloudAppConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.job.CloudJobConfigurationService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.state.disable.app.DisableAppService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.state.disable.job.DisableJobService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.state.failover.FailoverService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.state.failover.FailoverTaskInfo;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.state.ready.ReadyService;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.state.running.RunningService;
import org.apache.shardingsphere.elasticjob.infra.context.ExecutionType;
import org.apache.shardingsphere.elasticjob.infra.context.TaskContext;
import org.apache.shardingsphere.elasticjob.infra.context.TaskContext.MetaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mesos facade service.
 */
@Slf4j
@Service
public final class FacadeService {
    
    @Autowired
    private CloudAppConfigurationService appConfigService;
    
    @Autowired
    private CloudJobConfigurationService jobConfigService;
    
    @Autowired
    private ReadyService readyService;
    
    @Autowired
    private RunningService runningService;
    
    @Autowired
    private FailoverService failoverService;
    
    @Autowired
    private DisableAppService disableAppService;
    
    @Autowired
    private DisableJobService disableJobService;
    
    /**
     * Start facade service.
     */
    public void start() {
        log.info("Elastic Job: Start facade service");
        runningService.start();
    }
    
    /**
     * Remove launched task from queue.
     *
     * @param taskContexts task running contexts
     */
    public void removeLaunchTasksFromQueue(final List<TaskContext> taskContexts) {
        List<TaskContext> failoverTaskContexts = new ArrayList<>(taskContexts.size());
        Collection<String> readyJobNames = new HashSet<>(taskContexts.size(), 1);
        for (TaskContext each : taskContexts) {
            switch (each.getType()) {
                case FAILOVER:
                    failoverTaskContexts.add(each);
                    break;
                case READY:
                    readyJobNames.add(each.getMetaInfo().getJobName());
                    break;
                default:
                    break;
            }
        }
        failoverService.remove(failoverTaskContexts.stream().map(TaskContext::getMetaInfo).collect(Collectors.toList()));
        readyService.remove(readyJobNames);
    }
    
    /**
     * Add task to running queue.
     *
     * @param taskContext task running context
     */
    public void addRunning(final TaskContext taskContext) {
        runningService.add(taskContext);
    }
    
    /**
     * Update daemon task status.
     *
     * @param taskContext task running context
     * @param isIdle set to idle or not
     */
    public void updateDaemonStatus(final TaskContext taskContext, final boolean isIdle) {
        runningService.updateIdle(taskContext, isIdle);
    }
    
    /**
     * Remove task from running queue.
     *
     * @param taskContext task running context
     */
    public void removeRunning(final TaskContext taskContext) {
        runningService.remove(taskContext);
    }
    
    /**
     * Record task to failover queue.
     *
     * @param taskContext task running context
     */
    public void recordFailoverTask(final TaskContext taskContext) {
        Optional<CloudJobConfigurationPOJO> cloudJobConfigOptional = jobConfigService.load(taskContext.getMetaInfo().getJobName());
        if (!cloudJobConfigOptional.isPresent()) {
            return;
        }
        if (isDisable(cloudJobConfigOptional.get())) {
            return;
        }
        CloudJobConfigurationPOJO cloudJobConfig = cloudJobConfigOptional.get();
        if (cloudJobConfig.isFailover() || CloudJobExecutionType.DAEMON == cloudJobConfig.getJobExecutionType()) {
            failoverService.add(taskContext);
        }
    }
    
    private boolean isDisable(final CloudJobConfigurationPOJO cloudJobConfig) {
        return disableAppService.isDisabled(cloudJobConfig.getAppName()) || disableJobService.isDisabled(cloudJobConfig.getJobName());
    }
    
    /**
     * Add transient job to ready queue.
     *
     * @param jobName job name
     */
    public void addTransient(final String jobName) {
        readyService.addTransient(jobName);
    }
    
    /**
     * Load cloud job config.
     *
     * @param jobName job name
     * @return cloud job config
     */
    public Optional<CloudJobConfigurationPOJO> load(final String jobName) {
        return jobConfigService.load(jobName);
    }
    
    /**
     * Load app config by app name.
     *
     * @param appName app name
     * @return cloud app config
     */
    public Optional<CloudAppConfigurationPOJO> loadAppConfig(final String appName) {
        return appConfigService.load(appName);
    }
    
    /**
     * Get failover task id by task meta info.
     *
     * @param metaInfo task meta info
     * @return failover task id
     */
    public Optional<String> getFailoverTaskId(final MetaInfo metaInfo) {
        return failoverService.getTaskId(metaInfo);
    }
    
    /**
     * Add daemon job to ready queue.
     *
     * @param jobName job name
     */
    public void addDaemonJobToReadyQueue(final String jobName) {
        Optional<CloudJobConfigurationPOJO> cloudJobConfig = jobConfigService.load(jobName);
        if (!cloudJobConfig.isPresent()) {
            return;
        }
        if (isDisable(cloudJobConfig.get())) {
            return;
        }
        readyService.addDaemon(jobName);
    }
    
    /**
     * Determine whether the task is running or not.
     *
     * @param taskContext task running context
     * @return true is running, otherwise not
     */
    public boolean isRunning(final TaskContext taskContext) {
        return ExecutionType.FAILOVER != taskContext.getType() && !runningService.getRunningTasks(taskContext.getMetaInfo().getJobName()).isEmpty()
                || ExecutionType.FAILOVER == taskContext.getType() && runningService.isTaskRunning(taskContext.getMetaInfo());
    }
    
    /**
     * Add mapping of the task primary key and host name.
     *
     * @param taskId task primary key
     * @param hostname host name
     */
    public void addMapping(final String taskId, final String hostname) {
        runningService.addMapping(taskId, hostname);
    }
    
    /**
     * Retrieve hostname and then remove task.
     *
     * @param taskId task primary key
     * @return hostname of the removed task
     */
    public String popMapping(final String taskId) {
        return runningService.popMapping(taskId);
    }
    
    /**
     * Get all ready tasks.
     *
     * @return ready tasks
     */
    public Map<String, Integer> getAllReadyTasks() {
        return readyService.getAllReadyTasks();
    }
    
    /**
     * Get all running tasks.
     *
     * @return running tasks
     */
    public Map<String, Set<TaskContext>> getAllRunningTasks() {
        return runningService.getAllRunningTasks();
    }
    
    /**
     * Get all failover tasks.
     *
     * @return failover tasks
     */
    public Map<String, Collection<FailoverTaskInfo>> getAllFailoverTasks() {
        return failoverService.getAllFailoverTasks();
    }
    
    /**
     * Determine whether the job is disable or not.
     *
     * @param jobName job name
     * @return true is disabled, otherwise not
     */
    public boolean isJobDisabled(final String jobName) {
        Optional<CloudJobConfigurationPOJO> jobConfiguration = jobConfigService.load(jobName);
        return !jobConfiguration.isPresent() || disableAppService.isDisabled(jobConfiguration.get().getAppName()) || disableJobService.isDisabled(jobName);
    }
    
    /**
     * Enable job.
     *
     * @param jobName job name
     */
    public void enableJob(final String jobName) {
        disableJobService.remove(jobName);
    }
    
    /**
     * Disable job.
     *
     * @param jobName job name
     */
    public void disableJob(final String jobName) {
        disableJobService.add(jobName);
    }
    
    
    /**
     * Stop facade service.
     */
    public void stop() {
        log.info("Elastic Job: Stop facade service");
        // TODO stop scheduler
        runningService.clear();
    }
}
