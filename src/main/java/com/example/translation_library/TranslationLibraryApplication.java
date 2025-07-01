package com.example.translation_library;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class TranslationLibraryApplication implements CommandLineRunner{
	
	private final DataSource dataSource;

	public TranslationLibraryApplication(final DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public static void main(String[] args) {
		SpringApplication.run(TranslationLibraryApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// This method can be used to initialize the database or perform any startup logic
		System.out.println("DataSource: " + dataSource.toString());
		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("SELECT * FROM translations WHERE language_code = 'sv' AND word = 'dog'");
	}

}
