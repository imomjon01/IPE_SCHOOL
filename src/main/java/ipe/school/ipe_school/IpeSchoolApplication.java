package ipe.school.ipe_school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@SpringBootApplication
@EnableScheduling
public class IpeSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpeSchoolApplication.class, args);
    }

}
