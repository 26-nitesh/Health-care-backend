FROM openjdk:11
ADD target/policy-serviceJAR.jar policy-serviceJAR.jar
ENTRYPOINT ["java","-jar","policy-serviceJAR.jar"]
ENV DB_URL=jdbc:mysql://192.168.224.1:3306/HealthCare?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true