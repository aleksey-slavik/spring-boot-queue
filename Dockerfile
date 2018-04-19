FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD /build/libs/spring-boot-queue-0.0.1.jar sbqueue.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar /sbqueue.jar"]