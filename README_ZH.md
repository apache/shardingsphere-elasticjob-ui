# ElasticJob-UI

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 概述

ElasticJob-UI 是 [ElasticJob](http://shardingsphere.apache.org/elasticjob/) 的管理控制台，包含了动态配置、作业管控等功能。
本项目包括两部分：

- shardingsphere-elasticjob-lite-ui：ElasticJob-Lite 管控端
- shardingsphere-elasticjob-cloud-ui：ElasticJob-Cloud 管控端

### ElasticJob-UI 前端

shardingsphere-elasticjob-lite-ui-frontend & shardingsphere-elasticjob-cloud-ui-frontend 模块基于 [vue](https://github.com/vuejs/vue)，
并使用其相关 UI 工具包 [element](https://github.com/ElemeFE/element) 开发。

### ElasticJob-UI 后端

shardingsphere-elasticjob-lite-ui-backend & shardingsphere-elasticjob-cloud-ui-backend 模块是标准的 Spring Boot 项目。

## 如何构建

```bash
git clone https://github.com/apache/shardingsphere-elasticjob-ui.git
cd shardingsphere-elasticjob-ui/
mvn clean package -Prelease
```

- 从 `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-ui-distribution/shardingsphere-elasticjob-lite-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-lite-ui-bin.tar.gz` 中获取 lite 软件包。
- 从 `shardingsphere-elasticjob-ui/shardingsphere-elasticjob-ui-distribution/shardingsphere-elasticjob-cloud-ui-bin-distribution/target/apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-cloud-ui-bin.tar.gz` 中获取 cloud 软件包。
