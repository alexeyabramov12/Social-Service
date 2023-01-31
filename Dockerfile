FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=impl/target/social-service-impl-1.0.0-SNAPSHOT-exec.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]