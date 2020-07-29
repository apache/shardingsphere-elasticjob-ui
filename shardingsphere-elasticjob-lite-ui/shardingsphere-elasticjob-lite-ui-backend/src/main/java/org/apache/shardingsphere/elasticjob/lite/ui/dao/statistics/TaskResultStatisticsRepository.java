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

package org.apache.shardingsphere.elasticjob.lite.ui.dao.statistics;

import org.apache.shardingsphere.elasticjob.lite.ui.domain.TaskResultStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Task result statistics repository.
 */
@Repository
public interface TaskResultStatisticsRepository extends JpaRepository<TaskResultStatistics, Long> {
    
    /**
     * Find task result statistics.
     *
     * @param fromTime from date to statistics
     * @param statisticInterval statistic interval
     * @return task result statistics
     */
    @Query("SELECT t FROM TaskResultStatistics t WHERE t.statisticInterval = :statisticInterval AND t.statisticsTime >= :fromTime ORDER BY t.id ASC")
    List<TaskResultStatistics> findTaskResultStatistics(@Param("fromTime") Date fromTime, @Param("statisticInterval") String statisticInterval);
    
    /**
     * Get summed task result statistics.
     *
     * @param fromTime from date to statistics
     * @param statisticInterval statistic interval
     * @return summed task result statistics
     */
    @Query("SELECT new TaskResultStatistics(SUM(t.successCount), SUM(t.failedCount)) FROM TaskResultStatistics t WHERE "
        + "t.statisticInterval = :statisticInterval AND t.statisticsTime >= :fromTime")
    TaskResultStatistics getSummedTaskResultStatistics(@Param("fromTime") Date fromTime, @Param("statisticInterval") String statisticInterval);
}
