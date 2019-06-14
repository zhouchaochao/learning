#!/bin/bash

#提示“请输入用户名称”。并等待30秒，把用户的输入保存入变量name中
read -t 30 -p "请输入用户名称:" name
echo "用户名为:$name"

read -p "请输入选项:" check_item
echo "选择:$check_item"

