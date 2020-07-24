# ShardingSphere ElasticJob UI

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 概述

ShardingSphere ElasticJob UI是 [ShardingSphere ElasticJob](https://shardingsphere.apache.org/) 的管理后台，包含了动态配置、数据编排等功能。
本项目包括两部分：
- shardingsphere-elasticjob-lite-ui：给shardingsphere-elasticjob-lite提供UI界面
- shardingsphere-elasticjob-cloud-ui：给shardingsphere-elasticjob-cloud提供UI界面

### ShardingSphere ElasticJob UI 前端

shardingsphere-elasticjob-lite-ui-frontend/shardingsphere-elasticjob-cloud-ui-frontend 模块基于 [vue](https://github.com/vuejs/vue) ，并使用了UI工具包 [element](https://github.com/ElemeFE/element) 。

- [shardingsphere-elasticjob-lite-ui/shardingsphere-elasticjob-lite-ui-frontend/README.md](shardingsphere-elasticjob-lite-ui/shardingsphere-elasticjob-lite-ui-frontend/README.md)
- [shardingsphere-elasticjob-cloud-ui/shardingsphere-elasticjob-cloud-ui-frontend/README.md](shardingsphere-elasticjob-cloud-ui/shardingsphere-elasticjob-cloud-ui-frontend/README.md)

### ShardingSphere ElasticJob UI 后端

shardingsphere-elasticjob-lite-ui-backend、shardingsphere-elasticjob-cloud-ui-backend 模块是标准的 spring boot 项目。

## 如何构建

```bash
git clone https://github.com/apache/shardingsphere-elasticjob-ui.git
cd shardingsphere-elasticjob-ui/
mvn clean package -Prelease
```

- 从 `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-ui-distribution/shardingsphere-elasticjob-lite-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-lite-ui-bin.tar.gz` 中获取 lite 软件包。
- 从 `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-ui-distribution/shardingsphere-elasticjob-cloud-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-cloud-ui-bin.tar.gz` 中获取 cloud 软件包。
