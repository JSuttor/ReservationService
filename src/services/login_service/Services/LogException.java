package services.login_service.Services;

public class LogException extends Exception {
    private String details;

    public LogException(String reason, String details) {
      super(reason);
      this.details = details;
    }

    public String getFaultInfo() {
        return details;
    }
}
