#!/bin/bash

REPOSITORY=/home/ubuntu/deployment

cd $REPOSITORY/

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

JAR_NAME=$(ls -tr $REPOSITORY/ | grep *.jar | tail -n 1)
chmod +x $JAR_NAME

echo "Starting $JAR_NAME..."

sudo nohup java -jar $REPOSITORY/$JAR_NAME \
--spring.config.location="classpath:application.yml,file://$REPOSITORY/aws.yml" \
-Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2 \
> /dev/null 2> /dev/null < /dev/null &
