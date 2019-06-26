#!/bin/sh

branch=$1

if [ ! $branch ];
then
   branch="devOffline"
fi

echo start building $branch

cd /home/webroot
rm -rf ./optimus-prime-manager
mkdir  ./optimus-prime-manager
cd     ./optimus-prime-manager
pid=`ps -ef |grep optimus-prime-manager-1.0.0-SNAPSHOT.jar |grep -v grep|awk '{print $2}'`
kill -9 $pid

git clone -b $branch 地址.git
cd ./optimus-prime/

mvn clean package -Pdevelopment -Dmaven.test.skip=true

cd /home/webroot/optimus-prime-manager/optimus-prime/optimus-prime-manager/target

nohup java -jar optimus-prime-manager-1.0.0-SNAPSHOT.jar &

echo 'tail -f /Users/logs/uemc/optimus-prime-manager/optimus-prime.log.'`date +%Y%m%d`
tail -f /Users/logs/uemc/optimus-prime-manager/optimus-prime.log.`date +%Y%m%d`

