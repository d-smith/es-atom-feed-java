FROM frolvlad/alpine-oraclejdk8:slim
ADD atom-rest-service-0.1.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
