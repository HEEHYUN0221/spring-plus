## 빌드 스테이지
#FROM ubuntu:22.04 AS build
#
## 필요한 패키지 설치
#RUN apt-get update && apt-get install -y \
#    openjdk-17-jdk \
#    gradle \
#    && rm -rf /var/lib/apt/lists/*
#
#WORKDIR /app
#
## 소스 코드 복사
#COPY . .
#
## Gradle 권한 설정
#RUN chmod +x ./gradlew
#
## 애플리케이션 빌드
#RUN ./gradlew bootJar
#
## 실행 스테이지
#FROM ubuntu:22.04
#
## 필요한 패키지 설치
#RUN apt-get update && apt-get install -y \
#    openjdk-17-jre \
#    && rm -rf /var/lib/apt/lists/*
#
#WORKDIR /app
#
## 빌드 스테이지에서 생성된 JAR 파일 복사
#COPY --from=build /app/build/libs/*.jar app.jar
#
## 애플리케이션 실행
#ENTRYPOINT ["java", "-jar", "app.jar"]

# Gradle 7.6 및 JDK 17 사용
#FROM gradle:7.6-jdk17 AS build

#FROM amazoncorretto:17 AS builder
#
#WORKDIR /app
#COPY gradle /app/gradle
#COPY gradlew /app/
#COPY build.gradle /app/
#COPY settings.gradle /app/
#RUN ./gradlew build --no-daemon
#COPY . .
#
#RUN ./gradlew build --no-daemon
#
#FROM amazoncorretto:17
#COPY --from=builder /app/build/libs/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]


FROM amazoncorretto:17-alpine3.21-jdk AS builder
WORKDIR /app
COPY gradle /app/gradle
COPY gradlew build.gradle settings.gradle /app/
RUN chmod +x ./gradlew
COPY src /app/src
RUN ./gradlew build --no-daemon
FROM amazoncorretto:17-alpine3.21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]