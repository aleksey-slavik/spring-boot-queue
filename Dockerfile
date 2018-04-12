FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD /build/libs/spring-boot-queue-0.0.1.jar sbq.jar
RUN sh -c 'touch /sbq.jar'
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java  -Xmx1024m $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar /sbq.jar"]