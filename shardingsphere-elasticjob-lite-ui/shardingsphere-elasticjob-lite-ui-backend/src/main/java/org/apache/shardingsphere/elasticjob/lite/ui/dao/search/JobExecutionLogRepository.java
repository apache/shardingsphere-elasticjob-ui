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

package org.apache.shardingsphere.elasticjob.lite.ui.dao.search;

import org.apache.shardingsphere.elasticjob.lite.ui.domain.JobExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobExecutionLogRepository extends JpaRepository<JobExecutionLog, String>, JpaSpecificationExecutor<JobExecutionLog> {
    
    /**
     * Find all job names with specific prefix.
     *
     * @param prefix job name prefix
     * @return matched job names
     */
    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query("select distinct l.jobName from JobExecutionLog l where l.jobName like :prefix%")
    List<String> findJobNameByJobNameLike(@Param("prefix") String prefix);
    
    /**
     * Find all IP addresses with specific prefix.
     *
     * @param prefix ip prefix
     * @return matched ip
     */
    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query("select distinct l.ip from JobExecutionLog l where l.ip like :prefix%")
    List<String> findIpByIpLike(@Param("prefix") String prefix);
}
