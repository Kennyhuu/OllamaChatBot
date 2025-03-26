package ollamachat.ollamachat;

import org.springframework.boot.SpringApplication;

public class TestOllamachatApplication {

	public static void main(String[] args) {
		SpringApplication.from(OllamachatApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
