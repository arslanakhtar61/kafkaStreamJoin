# kafkaStreamJoin

https://stackoverflow.com/questions/56548736/kstream-kstream-inner-join-throws-java-lang-classcastexception

Reproducible Steps:

1) Start zookeeper and kafka broker

2)Create person topic by running the following command in a terminal:

`.\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic person`

`.\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic school`

3) Run the stand-alone Producer.java to generate messages for 'school' topic

4) Optional - To observe the messages within topics

`.\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic person --key-deserializer org.apache.kafka.common.serialization.StringDeserializer --value-deserializer org.apache.kafka.common.serialization.StringDeserializer --property print.key=true --property key.separator="-" --property print.timestamp=true`

`.\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic school --key-deserializer org.apache.kafka.common.serialization.StringDeserializer --value-deserializer org.apache.kafka.common.serialization.StringDeserializer --property print.key=true --property key.separator="-" --property print.timestamp=true`

5) Run KafkaStreamJoinApplication.java as the main application from an IDE

6) Observe the java.lang.ClassCastException:
