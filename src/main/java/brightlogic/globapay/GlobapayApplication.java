package brightlogic.globapay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "brightlogic.globapay.domain.model")
public class GlobapayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobapayApplication.class, args);
    }

}
