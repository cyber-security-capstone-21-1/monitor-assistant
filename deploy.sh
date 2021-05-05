#!/bin/bash

REPOSITORY=/home/ec2-user
PROJ_NAME=monitor-assistant

cd $REPOSITORY/$PROJ_NAME/

echo "> PULL"
git pull

echo "> Gradle Build"
./gradlew build

echo "> Copy JAR"
cp $REPOSITORY/$PROJ_NAME/build/libs/*.jar $REPOSITORY/
cd ..

echo "> 현재 실행중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f ${PROJ_NAME}.*.jar)

echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then # (7)
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY | grep *.jar | tail -n 1)

echo "> JAR: $JAR_NAME"

chmod +x $JAR_NAME

sudo nohup java -jar $REPOSITORY/$JAR_NAME \
--spring.config.location="file://$REPOSITORY/application.yml,file://$REPOSITORY/secret/aws.yml" 2>1 &
