package tobyspring.helloboot;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

    public static void main(String[] args) {
      TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
      WebServer webServer = factory.getWebServer(servletContainer -> {
          HelloController helloController = new HelloController();

          servletContainer.addServlet("frontcontroller", new HttpServlet() {
              @Override
              protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    
                  //인증, 보안, 다국어, 공통 기능

                  // URL 및 METHOD를 통한 분기처리
                  if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())){
                      String name = req.getParameter("name");

                      String ret = helloController.hello(name);
                      resp.setStatus(HttpStatus.OK.value());
                      resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                      resp.getWriter().println(ret);

                  }
                  else if(req.getRequestURI().equals("users")){

                  }else{
                      resp.setStatus(HttpStatus.NOT_FOUND.value());

                  }

              }
          }).addMapping("/*");
      });
      webServer.start();
    }

}
