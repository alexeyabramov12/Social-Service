FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=impl/target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]