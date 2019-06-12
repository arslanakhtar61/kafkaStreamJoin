package com.sample.kafkastreamjoin;

import com.sample.kafkastreamjoin.model.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Producers {

	public static void main(String... args) {

		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		List<KeyValue<SchoolKey, School>> schools = Arrays.asList(
				new KeyValue<>(new SchoolKey("BMIA", "PK", "Islamabad", 1), new School("Sector F/8")),
				new KeyValue<>(new SchoolKey("CMII", "Hk", "Rawalpindi", 2), new School("Sector G/8")),
				new KeyValue<>(new SchoolKey("SCSV", "USA", "Lahore", 3), new School("Sector H/8")),
				new KeyValue<>(new SchoolKey("NVS", "SW", "Faisalbad", 4), new School("Sector J/8")),
				new KeyValue<>(new SchoolKey("SNVJ", "CH", "Shikarpur", 5), new School("Sector C/8")),
				new KeyValue<>(new SchoolKey("DBJ", "CN", "Talaqand", 6), new School("Sector Z/8")),
				new KeyValue<>(new SchoolKey("SCNJ", "SE", "Karachi", 7), new School("Sector S/8"))
		);

		DefaultKafkaProducerFactory<SchoolKey, School> schoolF = new DefaultKafkaProducerFactory<>(props);
		KafkaTemplate<SchoolKey, School> templateSchool = new KafkaTemplate<>(schoolF, true);
		templateSchool.setDefaultTopic("school");

		for (KeyValue<SchoolKey, School> keyValue : schools) {
			templateSchool.sendDefault(keyValue.key, keyValue.value);
		}

	}

}
