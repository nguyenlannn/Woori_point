package com.example.woori_base;

import com.example.woori_base.test.Outfit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WooriBaseApplication {

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(WooriBaseApplication.class, args);

		Outfit outfit = context.getBean(Outfit.class);
		System.out.println("bean tìm thấy: " + outfit);
		outfit.wear();
	}

}
