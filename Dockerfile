FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mg-clog-be.jar
ENTRYPOINT ["java","-jar","/mg-clog-be.jar"]
