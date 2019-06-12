package com.sample.kafkastreamjoin;

import com.sample.kafkastreamjoin.model.*;
import com.sample.kafkastreamjoin.serde.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.time.Duration;

@SpringBootApplication
public class KafkaStreamJoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamJoinApplication.class, args);
	}

	@EnableBinding(KStreamProcessorX.class)
	public static class KafkaKStreamJoinApplication {

		@StreamListener
		public void process(@Input("school") KStream<SchoolKey, School> schools) {

			schools.map((schoolKey, school) -> {
				return KeyValue.pair(new PersonKey("Adam", "Smith", schoolKey.getId()), new Person(12));
			})
					.through("person", Produced.with(new PersonKeySerde(), new PersonSerde()));
		}

		@StreamListener
		public void process1(@Input("school_1") KStream<SchoolKey, School> schools, @Input("person") KStream<PersonKey, Person> persons) {

			schools.selectKey((schoolKey, school) -> schoolKey.getId())
					.join(persons.selectKey((personKey, person) -> personKey.getId()),
							(school, person) -> {
								System.out.println("school_app2= " + school + ", person_app2= " + person);
								return null;
							},
							JoinWindows.of(Duration.ofSeconds(1)),
							Joined.with(Serdes.Integer(), new SchoolSerde(), new PersonSerde())
					);
		}

	}

	interface KStreamProcessorX {

		@Input("person")
		KStream<?, ?> inputPersonKStream();

		@Input("school")
		KStream<?, ?> inputSchoolKStream();

		@Input("school_1")
		KStream<?, ?> inputSchool1KStream();

	}
}
