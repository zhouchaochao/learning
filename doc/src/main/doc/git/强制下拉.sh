#!/bin/bash
cd fu-hive-shell/
git fetch --all
git reset --hard origin/master
git pull

echo "给脚本添加权限:"
echo `pwd`
find . -name "*.sh" -exec chmod 775 {} \;

