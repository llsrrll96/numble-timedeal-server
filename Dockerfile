FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar",\
"-javaagent:./pinpoint/pinpoint-bootstrap-2.5.0.jar",\
"-Dpinpoint.agentId=gjgs01","-Dpinpoint.applicationName=gjgs",\
"-Dpinpoint.config=./pinpoint/pinpoint-root.config"\
,"-Dspring.profiles.active=prod","app.jar","2>&1","&"]
