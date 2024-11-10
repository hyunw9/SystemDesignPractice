FROM openjdk:21 AS build
WORKDIR /app
# Step 2: JAR 파일 복사
COPY build/libs/demo-myapp.jar /app.jar

# Step 3: 컨테이너 시작 시 JAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
