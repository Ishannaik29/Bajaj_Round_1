package in.ashokit.service;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import in.ashokit.dto.GenerateRequest;
import in.ashokit.model.GenerateResponse;
import in.ashokit.model.QueryResult;
import in.ashokit.repository.QueryResultRepository;

@Service
public class WebhookService {

	private final WebClient webClient;
	  private final QueryResultRepository repo;

	  public WebhookService(WebClient webClient, QueryResultRepository repo) {
	    this.webClient = webClient;
	    this.repo = repo;
	  }

	  public void runFlow() {
	    // 1) Build request (replace fields with your data or source from properties)
	    GenerateRequest req = new GenerateRequest("John Doe", "REG12347", "john@example.com");

	    // 2) Call generateWebhook endpoint
	    GenerateResponse resp = webClient.post()
	        .uri("https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA")
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(req)
	        .retrieve()
	        .bodyToMono(GenerateResponse.class)
	        .block(); // ok to block at startup

	    if (resp == null || resp.getAccessToken() == null) {
	      throw new IllegalStateException("generateWebhook returned no token");
	    }

	    String accessToken = resp.getAccessToken();
	    @SuppressWarnings("unused")
		String webhookUrl = resp.getWebhook();

	    // 3) Decide which question (based on last two digits of regNo)
	    boolean isOdd = determineOdd(req.getRegNo());

	    // 4) Solve SQL problem — implement manually or programmatically.
	    //    For the example, we set a placeholder query:
	    String finalQuery = solveSqlForRegNo(req.getRegNo(), isOdd);

	    // 5) Persist (JPA)
	    QueryResult qr = new QueryResult();
	    qr.setRegNo(req.getRegNo());
	    qr.setFinalQuery(finalQuery);
	    qr.setCreatedAt(OffsetDateTime.now());
	    repo.save(qr);

	    // 6) Submit final query to testWebhook
	    webClient.post()
	      .uri("https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA")
	      .header(HttpHeaders.AUTHORIZATION, accessToken) // follow thein the PDF
	      .contentType(MediaType.APPLICATION_JSON)
	      .bodyValue(Map.of("finalQuery", finalQuery))
	      .retrieve()
	      .bodyToMono(Void.class)
	      .block();
	  }

	  private boolean determineOdd(String regNo) {
	    String digits = regNo.replaceAll("\\D+", "");
	    if (digits.length() < 2) {
	      // fallback
	      return true;
	    }
	    int lastTwo = Integer.parseInt(digits.substring(digits.length() - 2));
	    return (lastTwo % 2) == 1;
	  }

	  private String solveSqlForRegNo(String regNo, boolean isOdd) {
	    // TODO: implement actual SQL to match the question in the PDF.
	    // For now return a placeholder — replace with the final SQL string you compute.
	    if (isOdd) {
	      return "SELECT * FROM sample_table WHERE ... -- replace with your final SQL for Question 1";
	    } else {
	      return "SELECT ... -- replace with your final SQL for Question 2";
	    }
	  }
}
