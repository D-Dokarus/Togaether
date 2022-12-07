FROM amazoncorretto:17
COPY target/LoginPrototype-1.0-SNAPSHOT-shaded.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]