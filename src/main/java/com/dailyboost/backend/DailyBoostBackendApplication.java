package com.dailyboost.backend;

import com.dailyboost.backend.model.User;
import com.dailyboost.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DailyBoostBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyBoostBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository) {
		return args -> {
			User user = new User(
					"dailybooster",
					"booster@example.com",
					"hashedpassword123",
					"https://example.com/avatar.png"
			);

			userRepository.save(user);
			System.out.println("User salvat în MongoDB!");
		};
	}
}
