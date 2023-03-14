FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["nohup","java","-jar",\
"-javaagent:$AGENT_PATH/pinpoint-bootstrap-2.5.0.jar",\
"-Dpinpoint.agentId=gjgs01","-Dpinpoint.applicationName=gjgs",\
"-Dpinpoint.config=$AGENT_PATH/pinpoint-root.config"\
,"-Dspring.profiles.active=prod","app.jar","2>&1","&"]
