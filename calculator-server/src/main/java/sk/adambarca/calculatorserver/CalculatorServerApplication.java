package sk.adambarca.calculatorserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CalculatorServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorServerApplication.class, args);
    }

}
