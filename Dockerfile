FROM openjdk:17 AS kstate
LABEL authors="dbontch"

#RUN mkdir data

ADD target/kstate-1.0.0-SNAPSHOT.jar kstate.jar

ENTRYPOINT ["java", "-jar", "kstate.jar"]
EXPOSE 8080
