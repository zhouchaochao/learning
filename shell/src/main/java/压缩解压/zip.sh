zip/unzip
  解压压缩文件或者目录
  参考：https://blog.csdn.net/weixin_39270987/article/details/122958566

命令格式：
压缩文件：zip abc.zip 1.txt
压缩目录：zip -r dir3newName.zip 目录
解压：unzip abc.zip
解压到指定目录：unzip abc.zip -d dir1


示例：
zip 1newName.zip 1.txt
压缩单个文件

unzip 1newName.zip
解压文件

zip -r dir3newName.zip dir3
压缩目录

unzip dir3newName.zip
压缩

unzip dir3newName.zip -d abc
ls abc/dir3/
解压到指定目录，如果不指定-d filedir则解压到当前目录



