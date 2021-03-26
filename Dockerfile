FROM openjdk:8-jdk-alpine
EXPOSE 8080/tcp
RUN  apk update && apk upgrade && apk add netcat-openbsd
WORKDIR invitation-service
COPY target/invitation.jar invitation.jar
RUN chmod -R 777 invitation.jar
ADD run.sh .
RUN chmod +x run.sh
CMD ./run.sh