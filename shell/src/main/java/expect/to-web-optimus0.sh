#!/bin/sh
###################
#auto login shell##
###################

echo 'tail -f /home/cc/logs/uemc/optimus-prime/optimus-prime.log.'`date +%Y%m%d`

sh /Users/cc/Tools/scripts/vm/common/connect.sh 1 dssh 192.22.100.100 'sudo -iu cc'

