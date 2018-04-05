FROM java:8
VOLUME /tmp
ADD build/libs/spring-boot-queue-0.0.1.jar sbq.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","sbq.jar"]