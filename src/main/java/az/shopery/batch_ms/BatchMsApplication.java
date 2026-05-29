package az.shopery.batch_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class BatchMsApplication {

	static void main(String[] args) {
		SpringApplication.run(BatchMsApplication.class, args);
	}
}
