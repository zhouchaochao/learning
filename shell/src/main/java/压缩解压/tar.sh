tar命令可以用来打包/压缩。它支持三种格式：".tar"、".bz2"和".gz"，后两种是压缩。
    参考：https://blog.csdn.net/weixin_39270987/article/details/122958566

常用支持参数：
-c 建立新的压缩文件
-C 指定解压目录，该目录必须存在
-x 从压缩的文件中提取文件
-j 支持bzip2解压文件
-f 指定压缩文件
-v 显示操作过程
-z 支持gzip解压文件


功能一、打包（不压缩）
    打包:tar -cvf abc.tar [文件或者目录]
    解包:tar -xvf abc.tar
    解包到指定文件夹:tar -xvf abc.tar -C [目录]

示例：
    tar -cvf dir3Name.tar dir3
    将目录dir3打包生成dir3Name.tar

    tar -xvf dir3Name.tar
    解包dir3Name.tar

    mkdir tmpdir
    tar -xvf dir3Name.tar -C tmpdir
    ls tmpdir/dir3/
    解包dir3Name.tar到tmpdir目录下，生成新的子目录dir3


功能二、压缩(.gz格式)
    压缩:tar -zcvf abc.tar.gz [文件或者目录]
    解压:tar -zxvf abc.tar.gz
    解压到指定目录:tar -zxvf abc.tar.gz -C [目录]

示例：
    tar -zcvf 1.txt.tar.gz 1.txt
    压缩文件1.txt，生成压缩文件1.txt.tar.gz

    tar -zcvf dir3NewName.tar.gz dir3
    压缩目录dir3，生成压缩文件dir3NewName.tar.gz

    tar -zxvf 1.txt.tar.gz //等价于tar -zxvf 1.txt.tar.gz -C ./
    解压txt.tar.gz，解压到当前目录

    mkdir /Users/didi/logs/dirtest0620/dir2/dir22
    tar -zxvf 1.txt.tar.gz -C /Users/didi/logs/dirtest0620/dir2/dir22
    解压1.txt.tar.gz，解压到目录dir22

    tar -zxvf dir3NewName.tar.gz
    解压dir3NewName.tar.gz，解压出目录dir3


功能三、压缩（.bz2格式）
    压缩:tar -jcvf abc.tar.bz2 [文件或者目录]
    解压:tar -jxvf abc.tar.bz2 [文件或者目录]
    解压:tar -jxvf abc.tar.bz2 -C [目录]

示例：
    tar -jcvf dir3newName.tar.bz2 dir3
    压缩dir3，生成dir3newName.tar.bz2

    tar -jxvf dir3newName.tar.bz2
    解压dir3newName.tar.bz2到当前目录下

