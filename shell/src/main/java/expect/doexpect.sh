#!/usr/bin/expect -f

set host [ lindex $argv 0 ]
set user [ lindex $argv 1 ]
set password [ lindex $argv 2 ]
set options [ lindex $argv 3 ]
set code [ lindex $argv 4 ]
set zhongkong [ lindex $argv 5 ]
set ssh_command [ lindex $argv 6 ]
set target [ lindex $argv 7 ]
set change_user [ lindex $argv 8 ]
set d_key [ lindex $argv 9 ]


spawn ssh $user@$host $options
expect {
 "*yes/no" { send "yes\r" ; exp_continue }
 "*assword" { send "$password\r" ;exp_continue ; }
 "*Verification*" { send "$code\r" ; exp_continue }
 "*password*" { send "$password\r" ;exp_continue ;  }
# "*input order num" { send "1\r" ; }
 "*控机列表*" { send "$zhongkong\r" ; exp_continue ; }
"*ey*" { send "$d_key\r" ; exp_continue ; }
 "*堡机系统*" { send "$zhongkong\r" ; exp_continue ; }
 "*中机须知*" { send "$ssh_command $target\r" ;exp_continue ; }
 "*\[zhouzhichao*\]$" { send "$change_user\r" ; }
# "*注意清理磁盘*" { send "$change_user\r" ; }
}
#send "dssh $target\r"
#send "sudo -i -u cc\r"
#send "sudo su - cs\r"

send "$change_user\r"

interact

