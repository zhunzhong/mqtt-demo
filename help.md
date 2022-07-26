以下是kafka常用命令行总结：

运行命令查看所有的topic： 

```
./kafka-topics --list --bootstrap-server 127.0.0.1:9092
```

1.查看topic的详细信息

```
./kafka-topics --describe --topic topic-test1   --bootstrap-server 127.0.0.1:9092
```



9、删除topic

```
./kafka-topics --delete --topic topic-test1   --bootstrap-server 127.0.0.1:9092
```



3、创建topic

```
./kafka-topics --create --replication-factor 1 --partitions 1 --topic topic-test1 --bootstrap-server 127.0.0.1:9092
```

查看消费情况

```
kafka-consumer-groups  --describe --group test-consumer-group  --bootstrap-server 127.0.0.1:9092
```



增加分区数据

```
kafka-topics   --alter --partitions 2  --topic topic-test1 --bootstrap-server 127.0.0.1:9092
```


