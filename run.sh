#!/bin/sh
echo "********************************************************"
echo "Starting invitation-service "
echo "********************************************************"
java -jar -Dspring.profiles.active=dev invitation.jar
#java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=dev -jar /usr/local/simple-service/apis.jar apis.jar &
