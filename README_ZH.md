# ElasticJob-UI

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

[English document](https://github.com/apache/shardingsphere-elasticjob-ui/blob/master/README.md)

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

## 如何连接事件追踪数据源

受协议限制，本项目无法直接添加部分数据库的 JDBC 驱动，需要用户自行添加，有两种方式：

### 在 pom.xml 添加 JDBC 依赖项并构建项目

通过源码构建本项目，可以直接在 [shardingsphere-elasticjob-lite-ui/shardingsphere-elasticjob-lite-ui-backend/pom.xml](https://github.com/apache/shardingsphere-elasticjob-ui/blob/master/shardingsphere-elasticjob-lite-ui/shardingsphere-elasticjob-lite-ui-backend/pom.xml) 中直接添加所需的 JDBC 驱动依赖。

### 在分发包的 ext-lib 目录中添加 JDBC 驱动 JAR

1. 获取并解压 `apache-shardingsphere-${latest.release.version}-shardingsphere-elasticjob-lite-ui-bin.tar.gz`；
2. 添加 JDBC 驱动 (例如 `mysql-connector-java-8.0.13.jar`) 到文件夹 `ext-lib`；
3. 使用脚本 `bin/start.sh` 运行管理控制台。
