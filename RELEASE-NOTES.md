## 3.0.0

### Enhancements

1. Order event trace history records by descending time 
1. Add confirm for shutdown operations
1. Some improvements for i18n

### Bug Fixes

1. Error occurs when operating the jobs have TracingConfiguration

### Build

1. Supports building frontend modules on macOS with Apple Silicon

### Change Logs

1. [MILESTONE 3.0.0](https://github.com/apache/shardingsphere-elasticjob-ui/milestone/4)

## 3.0.0-RC1

### Enhancement

1. Support querying job history without conditions in Lite Console
1. Discover available JDBC driver classes to reduce manual input in Lite Console

### Bug Fixes

1. Some APIs don't work properly when the job name contains dot character
1. Fix time range criteria doesn't work in history pages
1. Fix cannot switch DataSource in Lite Console

### Build & Dependencies

1. Add PostgreSQL JDBC Driver to Lite UI by default

###  Change Logs

1. [MILESTONE 3.0.0-RC1](https://github.com/apache/shardingsphere-elasticjob-ui/milestone/3)

## 3.0.0-beta

### Enhancement

1. Provide more clear error messages when missing JDBC drivers
1. Support create, read, update and delete properties of the jobs 
1. Support auto complete for job name and server IP in job history page
1. Upgrade parent image in Dockerfile to support resources limiting

###  Change Logs

1. [MILESTONE](https://github.com/apache/shardingsphere-elasticjob-ui/milestone/2)

## 3.0.0-alpha

### Enhancement

1. Refactor ElasticJob-Lite UI from jQuery/AdminTLE to Vue.js/ElementUI
1. Refactor ElasticJob-Cloud UI from jQuery/AdminTLE to Vue.js/ElementUI

###  Change Logs

1. [MILESTONE](https://github.com/apache/shardingsphere-elasticjob-ui/milestone/1)
