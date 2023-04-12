package io.github.leonardomvs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.leonardomvs.domain.entity.User;
import io.github.leonardomvs.domain.repository.UserRepository;

@SpringBootApplication
public class SpringBootExampleJwtAuthApplication extends SpringBootServletInitializer {

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	UserRepository userRepository;
			
	@Autowired
	PasswordEncoder passwordEncoder;
			
	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleJwtAuthApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void registerFirstUser() {
		
		String email = "leonardomvs@gmail.com";
		String password = "12345678";
		
		User user = User.builder()
		.email(email)
		.password(passwordEncoder.encode(password))
		.admin(true)
		.build();
		
		userRepository.save(user);
		
		String msg = new StringBuilder()
				.append(messageSource.getMessage("the_default_user_was_registered_in_the_database", null, null))
				.append(" [email: ").append(email).append("] ")
				.append(" [password: ").append(password).append("] ")
				.toString();
		
		System.out.println(msg);
		
	}

}
