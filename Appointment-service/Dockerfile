FROM openjdk:11
ADD target/appointment-serviceJAR.jar appointment-serviceJAR.jar
ENTRYPOINT ["java","-jar","appointment-serviceJAR.jar"]
ENV MONGO_CONN=mongodb://root3:8210@192.168.224.1:27017/health-care-system-collections