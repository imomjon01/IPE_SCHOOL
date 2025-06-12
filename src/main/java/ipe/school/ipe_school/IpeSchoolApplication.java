package ipe.school.ipe_school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@SpringBootApplication
public class IpeSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpeSchoolApplication.class, args);
    }

}
