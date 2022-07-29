FROM openjdk:16-alpine

COPY target/demo-*.jar demo.jar

CMD ["java", "-jar", "/demo.jar"]
