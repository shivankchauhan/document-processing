package com.howtoprogram.kafka;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

	public final CountDownLatch countDownLatch1 = new CountDownLatch(1);

	@KafkaListener(id = "foo", topics = "topic2", group = "group1")
	public void listen(ConsumerRecord<?, ?> record) {
		System.out.println(record);
		Object payload = record.value();
		System.out.println(payload);
		countDownLatch1.countDown();
	}

}