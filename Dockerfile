FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD spring-boot-queue-0.0.1.jar sbq.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","sbq.jar"]