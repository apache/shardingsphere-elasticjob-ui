# ShardingSphere ElasticJob UI

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## Overview

ShardingSphere ElasticJob UI is a management background for [ShardingSphere-ElasticJob ](https://shardingsphere.apache.org/).

### ShardingSphere ElasticJob UI Frontend

shardingsphere-elasticjob-ui-frontend based on [vue](https://github.com/vuejs/vue) and use the UI Toolkit [element](https://github.com/ElemeFE/element).

* [shardingsphere-elasticjob-ui-frontend/README.md](shardingsphere-elasticjob-ui-frontend/README.md)

### ShardingSphere ElasticJob UI Backend

shardingsphere-elasticjob-ui-backend is a standard spring boot project.

## How to Build

```bash
git clone https://github.com/apache/shardingsphere-elasticjob-ui.git
cd shardingsphere-elasticjob-ui/
mvn clean package -Prelease
```

Get the package in `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-ui-distribution/shardingsphere-elasticjob-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-ui-bin.tar.gz`

