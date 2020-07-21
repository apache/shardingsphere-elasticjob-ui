# ShardingSphere ElasticJob UI

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## Overview

ShardingSphere ElasticJob UI is a management background for [ShardingSphere-ElasticJob ](https://shardingsphere.apache.org/).
There are two parts in this project:
- shardingsphere-elasticjob-lite-ui: the UI of shardingsphere-elasticjob-lite
- shardingsphere-elasticjob-cloud-ui: the UI of shardingsphere-elasticjob-cloud

### ShardingSphere ElasticJob UI Frontend

shardingsphere-elasticjob-lite-ui-frontend/shardingsphere-elasticjob-cloud-ui-frontend based on [vue](https://github.com/vuejs/vue) and use the UI Toolkit [element](https://github.com/ElemeFE/element).

* [shardingsphere-elasticjob-lite-ui/shardingsphere-elasticjob-lite-ui-frontend/README.md](shardingsphere-elasticjob-lite-ui/shardingsphere-elasticjob-lite-ui-frontend/README.md)
* [shardingsphere-elasticjob-cloud-ui/shardingsphere-elasticjob-cloud-ui-frontend/README.md](shardingsphere-elasticjob-cloud-ui/shardingsphere-elasticjob-cloud-ui-frontend/README.md)

### ShardingSphere ElasticJob UI Backend

shardingsphere-elasticjob-lite-ui-backend、shardingsphere-elasticjob-cloud-ui-backend is a standard spring boot project.

## How to Build

```bash
git clone https://github.com/apache/shardingsphere-elasticjob-ui.git
cd shardingsphere-elasticjob-ui/
mvn clean package -Prelease
```

- Get the lite package in `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-lite-ui/shardingsphere-elasticjob-lite-ui-distribution/shardingsphere-elasticjob-lite-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-lite-ui-bin.tar.gz`
- Get the cloud package in `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-cloud-ui/shardingsphere-elasticjob-cloud-ui-distribution/shardingsphere-elasticjob-cloud-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-cloud-ui-bin.tar.gz`
