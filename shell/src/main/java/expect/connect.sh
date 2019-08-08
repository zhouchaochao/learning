#!/bin/sh
###################
#auto login shell##
###################

declare -a dic
#dic=([00]=".gz01" [01]="b.gz01" [02]="c.gz01" [03]="d.gz01" [04]="a.gz01" [05]="a.gz01")


#target=$1
target=online
#machine=${dic[$1]}
#machine=$1
zhongkong=$1
ssh_command=$2
machine=$3
change_user=$4


if [ ! $target ];
then
   target="myself"
fi

if [ "x"$target == "xonline"  ];
then
host="relay.sys.cckeji.com"
user="cc"
options="-p35000"
password="helloWorld"
#echo "please input online code"
#read code
code=`/Users/cc/Tools/scripts/vm/genCode abc`
fi

if [ "x"$target == "xmyself"  ];
then
host="1.2.3.100"
user="root"
password="root"
fi

read -p "请输入d_key:" d_key

echo $zhongkong
echo $ssh_command
echo $machine
echo $change_user
echo $d_key

/usr/bin/expect /Users/cc/tools/scripts/vm/common/doexpect.sh $host $user $password $options $code $zhongkong $ssh_command $machine "$change_user" $d_key

#cckeji
#cc
#