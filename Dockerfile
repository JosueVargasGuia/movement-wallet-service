FROM openjdk:11
EXPOSE  9081
WORKDIR /app
ADD   ./target/*.jar /app/movement-wallet-service.jar
ENTRYPOINT ["java","-jar","/app/movement-wallet-service.jar"] 