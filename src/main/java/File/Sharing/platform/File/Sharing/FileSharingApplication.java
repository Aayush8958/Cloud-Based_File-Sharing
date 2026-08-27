package File.Sharing.platform.File.Sharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileSharingApplication {

	public static void main(String[] args) {
		System.out.println("STARTING FILE SHARING APPLICATION");
		SpringApplication.run(FileSharingApplication.class, args);
	}

}
