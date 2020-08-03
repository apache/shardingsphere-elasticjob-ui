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

package org.apache.shardingsphere.elasticjob.cloud.ui.service.state.running;

import com.google.common.collect.Sets;
import org.apache.shardingsphere.elasticjob.cloud.ui.service.job.CloudJobConfigurationService;
import org.apache.shardingsphere.elasticjob.infra.context.TaskContext;
import org.apache.shardingsphere.elasticjob.infra.context.TaskContext.MetaInfo;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Running service.
 */
@Service
public final class RunningService {
    
    @Autowired
    private CoordinatorRegistryCenter regCenter;
    
    @Autowired
    private CloudJobConfigurationService configurationService;
    
    /**
     * Get all running tasks.
     *
     * @return collection of all the running tasks
     */
    public Map<String, Set<TaskContext>> getAllRunningTasks() {
        Map<String, Set<TaskContext>> result = new HashMap<>();
        List<String> jobKeys = regCenter.getChildrenKeys(RunningNode.ROOT);
        for (String each : jobKeys) {
            if (!configurationService.load(each).isPresent()) {
                continue;
            }
            result.put(each, Sets.newCopyOnWriteArraySet(regCenter.getChildrenKeys(RunningNode.getRunningJobNodePath(each)).stream().map(
                    input -> TaskContext.from(regCenter.get(RunningNode.getRunningTaskNodePath(MetaInfo.from(input).toString())))).collect(Collectors.toList())));
        }
        return result;
    }
}
