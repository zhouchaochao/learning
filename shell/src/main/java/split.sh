切分合并文档


示例：
split -b 450M before.dump.202204121048.dump.tar.gz before.dump.202204121048.dump.tar.gz.split
切分，按照每个文件450M的大小进行切分

cat before.dump.202204121048.dump.tar.gz.splita* > before.dump.202204121048.dump.tar.gz
合并，将多个文件splita*合并为一个


