package marc.dev.taskmanagement;

import marc.dev.taskmanagement.utils.VerifyToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TaskManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<VerifyToken> filterRegistrationBean(){
		FilterRegistrationBean<VerifyToken> registrationBean = new FilterRegistrationBean<>();
		VerifyToken verifyToken = new VerifyToken();
		registrationBean.setFilter(verifyToken);
		registrationBean.addUrlPatterns("/api/v1/teams/*");
		return  registrationBean;
	}

}
