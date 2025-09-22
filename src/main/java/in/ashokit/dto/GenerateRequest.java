package in.ashokit.dto;


public class GenerateRequest {

	private String name;
    private String regNo;
    private String email;

    // No-arg constructor required by Jackson
    public GenerateRequest() {}

    // Convenience constructor
    public GenerateRequest(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }

    // Getters and setters (Jackson uses these)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "GenerateRequest{name='" + name + "', regNo='" + regNo + "', email='" + email + "'}";
    }
}
