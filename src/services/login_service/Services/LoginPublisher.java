package services.login_service.Services;


import javax.xml.ws.Endpoint;
import services.login_service.Services.LogRequestPortType;
public class LoginPublisher {
    public static void main(String[ ] args) {
      // 1st argument is the publication URL
      // 2nd argument is an SIB instance
      Endpoint.publish("http://127.0.0.1:9876/login", new LogRequestPortType());
    //
}
}
    