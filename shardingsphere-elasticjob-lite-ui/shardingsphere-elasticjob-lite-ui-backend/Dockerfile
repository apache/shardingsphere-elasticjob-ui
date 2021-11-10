FROM java:8-jre-alpine

ENV TZ Asia/Shanghai
ENV LANG C.UTF-8
ENV DOCKER true

WORKDIR /app/elasticjob

COPY target/shardingsphere-elasticjob-lite-ui.jar /app/elasticjob

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "/app/elasticjob/shardingsphere-elasticjob-lite-ui.jar"]
