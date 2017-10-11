package br.com.muxi.equipments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class EquipmentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipmentsApplication.class, args);
	}
}
