FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["nohup","java","-jar",\
"-javaagent:/root/pinpoint-agent-2.5.0/pinpoint-bootstrap-2.5.0.jar",\
"-Dpinpoint.agentId=gjgs01","-Dpinpoint.applicationName=gjgs",\
"-Dpinpoint.config=/root/pinpoint-agent-2.5.0/pinpoint-root.config"\
,"-Dspring.profiles.active=prod","app.jar","2>&1","&"]
