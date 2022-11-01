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

package org.apache.shardingsphere.elasticjob.lite.ui.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.lite.ui.exception.DriverClassNotInWhitelistException;
import org.apache.shardingsphere.elasticjob.lite.ui.exception.JdbcDriverNotFoundException;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResult;
import org.apache.shardingsphere.elasticjob.lite.ui.web.response.ResponseResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

/**
 * Define exception handlers.
 */
@Slf4j
@RestControllerAdvice
public final class RestExceptionHandler {
    
    @ExceptionHandler(JdbcDriverNotFoundException.class)
    public ResponseResult<?> handleJdbcDriverNotFoundException(final JdbcDriverNotFoundException ex) {
        log.error(ex.getLocalizedMessage(), ex);
        return ResponseResultUtil.handleUncaughtException(ex.getLocalizedMessage());
    }
    
    @ExceptionHandler(DriverClassNotInWhitelistException.class)
    public ResponseResult<?> handleDriverClassNotInWhitelistException(final DriverClassNotInWhitelistException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseResultUtil.handleIllegalArgumentException(ex.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseResult<?> handleException(final Exception ex) {
        Throwable cause = Optional.ofNullable(ex.getCause()).orElse(ex);
        log.error(cause.getLocalizedMessage(), cause);
        return ResponseResultUtil.handleUncaughtException(cause.getLocalizedMessage());
    }
}
