# 自定义实现的catalog
用于保存 flink sql创建的元数据

实现了 database table view 的 create ，alter， drop，list，get 操作。
partition相关操作: 根据文档，只有再文件系统的connector才需要这个功能。例如hdfs
其作用是在文件系统上根据分区字段创建目录，
列出已有的分区目录，修改分区目录等。根据评估，如果是应对流数据，暂时不需要实现该功能。
如果是针对hdfs，则使用hive catalog来作为 catalog 更好
如果是其他fs类的支持，需要适配各种环境，不利于做到通用性。
故这个部分未进行实现。

对各类 Statistics 也未进行实现，因为流数据无法获取相关的条数，数据大小等。
