package tritronik.test.SmartHomeStay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SmartHomeStayApplication {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(SmartHomeStayApplication.class, args);
		MessageListener listener = context.getBean(MessageListener.class);

		listener.latch.await(10, TimeUnit.SECONDS);

	}

	@Bean
	public MessageListener messageListener() {
		return new MessageListener();
	}

	public static class MessageListener {

		private CountDownLatch latch = new CountDownLatch(3);


		@KafkaListener(topics = "${reservations.topic.name}", groupId = "employee-rsv", containerFactory = "fooKafkaListenerContainerFactory")
		public void listenGroupRsv(String message) {
			System.out.println("Received Message in group 'employee-rsv': " + message);
			latch.countDown();
		}


	}

}
