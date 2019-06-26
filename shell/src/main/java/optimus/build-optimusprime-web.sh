#!/bin/sh

branch=$1

if [ ! $branch ];
then
   branch="devOffline"
fi

echo start building $branch

cd /home/webroot
#rm -rf ./optimus-prime-web
#mkdir  ./optimus-prime-web
cd     ./optimus-prime-web
#pid=`ps -ef |grep optimus-prime-web-1.0.0-SNAPSHOT.jar |grep -v grep|awk '{print $2}'`
#kill -9 $pid

#git clone -b $branch 地址/optimus-prime.git

cd ./optimus-prime/

mvn clean package -Pdevelopment -Dmaven.test.skip=true

cd /home/webroot/optimus-prime-web/optimus-prime/optimus-prime-web/target

nohup java -jar optimus-prime-web-1.0.0-SNAPSHOT.jar &

echo 'tailf /Users/logs/uemc/optimus-prime/optimus-prime.log.2019010'

echo 'tail -f /Users/logs/uemc/optimus-prime/optimus-prime.log.'`date +%Y%m%d`
tail -f /Users/logs/uemc/optimus-prime/optimus-prime.log.`date +%Y%m%d`

