package com.portfolio.blog.Blog;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@SpringBootApplication
@EnableMongoRepositories
public class BlogApplication {
	@Bean
	public ModelMapper modelMapper(){return modelMapper();}
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
}
