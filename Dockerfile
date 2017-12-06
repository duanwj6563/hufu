FROM java:8
ADD target/app-hufu.jar /opt/app-hufu.jar
ADD pinpoint-agent /opt/pinpoint-agent
ENTRYPOINT ["java","-javaagent:/opt/pinpoint-agent/pinpoint-bootstrap-1.6.1.jar","-Dpinpoint.agentId=feo-hufu-test","-Dpinpoint.applicationName=feo-hufu","-jar","/opt/app-hufu.jar","-Duser.timezone=GMT+08"]
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
EXPOSE 8080