package services.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import services.controllers.GetSpecialConnectionController;


@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
@SpringBootApplication
@ComponentScan(basePackageClasses=GetSpecialConnectionController.class)
public class GetSpecialConnectionsApp 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(GetSpecialConnectionsApp.class, args);
	}
}
