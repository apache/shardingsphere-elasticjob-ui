# ElasticJob-UI

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

[中文文档](https://github.com/apache/shardingsphere-elasticjob-ui/blob/master/README_ZH.md)

## Overview

ElasticJob UI is an administrator console of [ElasticJob](http://shardingsphere.apache.org/elasticjob/).
There are two parts in this project:

- shardingsphere-elasticjob-lite-ui: the UI of ElasticJob-Lite
- shardingsphere-elasticjob-cloud-ui: the UI of ElasticJob-Cloud

### ElasticJob-UI Frontend

shardingsphere-elasticjob-lite-ui-frontend & shardingsphere-elasticjob-cloud-ui-frontend based on [vue](https://github.com/vuejs/vue) and use the UI Toolkit [element](https://github.com/ElemeFE/element).

### ElasticJob-UI Backend

shardingsphere-elasticjob-lite-ui-backend & shardingsphere-elasticjob-cloud-ui-backend are standard Spring Boot projects.

## How to Build

```bash
git clone https://github.com/apache/shardingsphere-elasticjob-ui.git
cd shardingsphere-elasticjob-ui/
mvn clean package -Prelease
```

- Get the lite tar in `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-ui-distribution/shardingsphere-elasticjob-lite-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-lite-ui-bin.tar.gz`
- Get the cloud tar in `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-ui-distribution/shardingsphere-elasticjob-cloud-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-cloud-ui-bin.tar.gz`

## How to Connect Event Trace DataSource

Restricted by the agreement, some databases' JDBC driver cannot be added to project directly, and users need to add it by themselves. There are two ways:

### Add JDBC Driver Dependency to pom.xml and Build

Add JDBC driver dependency to [shardingsphere-elasticjob-lite-ui/shardingsphere-elasticjob-lite-ui-backend/pom.xml](https://github.com/apache/shardingsphere-elasticjob-ui/blob/master/shardingsphere-elasticjob-lite-ui/shardingsphere-elasticjob-lite-ui-backend/pom.xml) and build.

### Add JDBC Driver JAR to ext-lib in Binary Distribution Package

1. Get `apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-lite-ui-bin.tar.gz` and extract.
2. Add JDBC Driver (Such as `mysql-connector-java-8.0.13.jar`) to directory `ext-lib`.
3. Run application with `bin/start.sh`
