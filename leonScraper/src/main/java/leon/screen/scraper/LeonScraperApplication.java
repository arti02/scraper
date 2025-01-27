package leon.screen.scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class LeonScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeonScraperApplication.class, args);

    }

}
