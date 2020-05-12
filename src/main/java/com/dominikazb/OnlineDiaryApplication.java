package com.dominikazb;

import com.dominikazb.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class OnlineDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineDiaryApplication.class, args);
	}

}
