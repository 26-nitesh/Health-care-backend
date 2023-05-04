FROM openjdk:11
ADD target/employee-serviceJAR.jar employee-serviceJAR.jar
ENTRYPOINT ["java","-jar","employee-serviceJAR.jar"]
ENV DB_URL=jdbc:mysql://192.168.224.1:3306/HealthCare?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true