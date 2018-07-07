package root;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages="root")
@ComponentScan(basePackages={"root","org.n3r.idworker"})
public class DouyinApplication {

	public static void main(String[] args) {
		SpringApplication.run(DouyinApplication.class, args);
	}
}
