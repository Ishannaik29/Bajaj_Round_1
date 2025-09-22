package in.ashokit.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerateResponse {

	 @JsonProperty("webhook")
	    private String webhook;

	    @JsonProperty("accessToken")
	    private String accessToken;

	    // Add other fields here if the API returns more data

	    public GenerateResponse() {}

	    public String getWebhook() { return webhook; }
	    public void setWebhook(String webhook) { this.webhook = webhook; }

	    public String getAccessToken() { return accessToken; }
	    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

	    @Override
	    public String toString() {
	        return "GenerateResponse{webhook='" + webhook + "', accessToken='" + accessToken + "'}";
	    }
}
