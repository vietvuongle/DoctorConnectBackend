package com.vuong.DoctorConnext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DoctorConnextApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorConnextApplication.class, args);
	}

}
