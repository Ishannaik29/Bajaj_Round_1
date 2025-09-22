package in.ashokit.webhook;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import in.ashokit.service.WebhookService;

@SpringBootApplication
public class WebhookApp {

	public static void main(String[] args) {
	    SpringApplication.run(WebhookApp.class, args);
	  }

	  @Bean
	  public WebClient webClient(WebClient.Builder builder) {
	    return builder.build();
	  }

	  @Bean
	  public ApplicationRunner startupRunner(WebhookService webhookService) {
	    return args -> webhookService.runFlow();
	  }
}
