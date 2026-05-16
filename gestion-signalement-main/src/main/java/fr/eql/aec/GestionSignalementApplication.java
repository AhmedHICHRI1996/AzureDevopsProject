package fr.eql.aec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GestionSignalementApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GestionSignalementApplication.class);
		app.setAdditionalProfiles("initData");
		ConfigurableApplicationContext context = app.run(args);
	}
}