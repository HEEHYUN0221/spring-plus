FROM openjdk:17
COPY ./build/libs/*.jar /opt/app.jar
WORKDIR /opt
ENTRYPOINT ["java", "-jar", "app.jar"]