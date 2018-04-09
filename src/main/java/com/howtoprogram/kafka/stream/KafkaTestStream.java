package com.howtoprogram.kafka.stream;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

public class KafkaTestStream {
	
	public static void main(String[] args) {
		
		
		 Properties props = new Properties();
	        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "foo");
	        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
		  
	        final StreamsBuilder builder = new StreamsBuilder();
	        builder.stream("topic1").to("topic2");
		    final Topology topology = builder.build();
			 
	        final KafkaStreams streams = new KafkaStreams(topology, props);

	        final CountDownLatch latch = new CountDownLatch(1);
	 
	        // attach shutdown handler to catch control-c
	        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
	            @Override
	            public void run() {
	                streams.close();
	                latch.countDown();
	            }
	        });
	 
	        try {
	            streams.start();
	            latch.await();
	        } catch (Throwable e) {
	            System.exit(1);
	        }
	        System.exit(0);
	}

}
