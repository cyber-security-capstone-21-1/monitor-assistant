package kr.ac.ajou.cybersecurity.capstone5.monitorassistant;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MonitorAssistantApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MonitorAssistantApplication.class)
				.run(args);
	}
}
