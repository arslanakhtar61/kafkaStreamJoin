package com.sample.kafkastreamjoin.serde;

import com.sample.kafkastreamjoin.model.PersonKey;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class PersonKeySerde extends Serdes.WrapperSerde<PersonKey> {
    public PersonKeySerde () {
        super(new JsonSerializer<>(), new JsonDeserializer<>(PersonKey.class));
    }
}
