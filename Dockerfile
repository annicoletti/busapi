FROM openjdk:8
VOLUME /tmp
EXPOSE 8088
ADD ./target/BusApi-0.0.1-SNAPSHOT.jar busapi.jar
ENTRYPOINT ["java", "-jar", "/busapi.jar"]