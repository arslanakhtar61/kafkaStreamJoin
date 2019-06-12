package com.sample.kafkastreamjoin.serde;

import com.sample.kafkastreamjoin.model.School;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class SchoolSerde extends Serdes.WrapperSerde<School> {
    public SchoolSerde () {
        super(new JsonSerializer<>(), new JsonDeserializer<>(School.class));
    }
}
