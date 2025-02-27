#Build stage

FROM gradle:8.9.0-jdk21 AS build
WORKDIR /usr/app/
COPY . .
RUN gradle build

# Package stage

FROM openjdk:21
ENV JAR_NAME=exrates-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=build $APP_HOME .
EXPOSE 8080
ENTRYPOINT exec java -jar -Dserver.port=8080 $APP_HOME/build/libs/$JAR_NAME