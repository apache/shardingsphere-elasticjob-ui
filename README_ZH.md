# ShardingSphere ElasticJob UI

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 概述

ShardingSphere ElasticJob UI是 [ShardingSphere ElasticJob](https://shardingsphere.apache.org/) 的管理后台，包含了动态配置、数据编排等功能。

### ShardingSphere ElasticJob UI 前端

shardingsphere-elasticjob-ui-frontend模块基于 [vue](https://github.com/vuejs/vue) ，并使用了UI工具包 [element](https://github.com/ElemeFE/element) 。

* [shardingsphere-elasticjob-ui-frontend/README.md](shardingsphere-elasticjob-ui-frontend/README.md)

### ShardingSphere ElasticJob UI 后端

shardingsphere-ui-backend 模块是一个标准的 spring boot 项目。

## 如何构建

```bash
git clone https://github.com/apache/shardingsphere-elasticjob-ui.git
cd shardingsphere-elasticjob-ui/
mvn clean package -Prelease
```

从 `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-ui-distribution/shardingsphere-elasticjob-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-ui-bin.tar.gz` 中获取软件包。

