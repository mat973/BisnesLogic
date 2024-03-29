package com.example.bisneslogic.models;


import com.example.bisneslogic.services.DataSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;



public class StringValueSource implements ValueSource {
    private static final Logger log = LoggerFactory.getLogger(StringValueSource.class);
    private final AtomicLong nextValue = new AtomicLong(1);
    private final DataSender valueConsumer;

    public StringValueSource(DataSender dataSender) {
        this.valueConsumer = dataSender;
    }

    @Override
    public void generate(String mail) {
        var id = nextValue.getAndIncrement();

      valueConsumer.send(new StringValue(id, mail));
        log.info("Send in Kafka");
    }

//    private StringValue makeValue() {
//        var id = nextValue.getAndIncrement();
//        return new StringValue(id, "stVal:" + id);
//    }
}
