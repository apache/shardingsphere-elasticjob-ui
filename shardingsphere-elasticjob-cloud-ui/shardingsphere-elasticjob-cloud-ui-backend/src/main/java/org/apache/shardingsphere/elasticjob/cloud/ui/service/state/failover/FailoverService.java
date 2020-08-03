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

package org.apache.shardingsphere.elasticjob.cloud.ui.service.state.failover;

import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.cloud.ui.config.JobStateConfiguration;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.state.running.RunningService;
import org.apache.shardingsphere.elasticjob.infra.context.TaskContext;
import org.apache.shardingsphere.elasticjob.infra.context.TaskContext.MetaInfo;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Failover service.
 */
@Slf4j
@Service
public final class FailoverService {
    
    @Autowired
    private CoordinatorRegistryCenter regCenter;
    
    @Autowired
    private RunningService runningService;
    
    @Autowired
    private JobStateConfiguration jobStateConfiguration;
    
    /**
     * Add task to failover queue.
     *
     * @param taskContext task running context
     */
    public void add(final TaskContext taskContext) {
        if (regCenter.getNumChildren(FailoverNode.ROOT) > jobStateConfiguration.getQueueSize()) {
            log.warn("Cannot add job, caused by read state queue size is larger than {}.", jobStateConfiguration.getQueueSize());
            return;
        }
        String failoverTaskNodePath = FailoverNode.getFailoverTaskNodePath(taskContext.getMetaInfo().toString());
        if (!regCenter.isExisted(failoverTaskNodePath) && !runningService.isTaskRunning(taskContext.getMetaInfo())) {
            // TODO Whether Daemon-type jobs increase storage and fail immediately?
            regCenter.persist(failoverTaskNodePath, taskContext.getId());
        }
    }
    
    private List<Integer> getAssignedShardingItems(final String jobName, final List<String> taskIdList, final Set<HashCode> assignedTasks) {
        List<Integer> result = new ArrayList<>(taskIdList.size());
        for (String each : taskIdList) {
            MetaInfo metaInfo = MetaInfo.from(each);
            if (assignedTasks.add(Hashing.sha256().newHasher().putString(jobName, StandardCharsets.UTF_8).putInt(metaInfo.getShardingItems().get(0)).hash())
                    && !runningService.isTaskRunning(metaInfo)) {
                result.add(metaInfo.getShardingItems().get(0));
            }
        }
        return result;
    }
    
    /**
     * Remove task from the failover queue.
     *
     * @param metaInfoList collection of task meta infos to be removed
     */
    public void remove(final Collection<MetaInfo> metaInfoList) {
        for (MetaInfo each : metaInfoList) {
            regCenter.remove(FailoverNode.getFailoverTaskNodePath(each.toString()));
        }
    }
    
    /**
     * Get task id from failover queue.
     *
     * @param metaInfo task meta info
     * @return failover task id
     */
    public Optional<String> getTaskId(final MetaInfo metaInfo) {
        String failoverTaskNodePath = FailoverNode.getFailoverTaskNodePath(metaInfo.toString());
        return regCenter.isExisted(failoverTaskNodePath) ? Optional.of(regCenter.get(failoverTaskNodePath)) : Optional.empty();
    }
    
    /**
     * Get all failover tasks.
     *
     * @return all failover tasks
     */
    public Map<String, Collection<FailoverTaskInfo>> getAllFailoverTasks() {
        if (!regCenter.isExisted(FailoverNode.ROOT)) {
            return Collections.emptyMap();
        }
        List<String> jobNames = regCenter.getChildrenKeys(FailoverNode.ROOT);
        Map<String, Collection<FailoverTaskInfo>> result = new HashMap<>(jobNames.size(), 1);
        for (String each : jobNames) {
            Collection<FailoverTaskInfo> failoverTasks = getFailoverTasks(each);
            if (!failoverTasks.isEmpty()) {
                result.put(each, failoverTasks);
            }
        }
        return result;
    }
    
    /**
     * Get failover tasks.
     *
     * @param jobName job name
     * @return collection of failover tasks
     */
    private Collection<FailoverTaskInfo> getFailoverTasks(final String jobName) {
        List<String> failOverTasks = regCenter.getChildrenKeys(FailoverNode.getFailoverJobNodePath(jobName));
        List<FailoverTaskInfo> result = new ArrayList<>(failOverTasks.size());
        for (String each : failOverTasks) {
            String originalTaskId = regCenter.get(FailoverNode.getFailoverTaskNodePath(each));
            if (!Strings.isNullOrEmpty(originalTaskId)) {
                result.add(new FailoverTaskInfo(MetaInfo.from(each), originalTaskId));
            }
        }
        return result;
    }
}
