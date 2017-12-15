package io.retrofrog.frogbot;

import com.github.mongobee.Mongobee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }

    @Bean
    public Mongobee mongobee(){
        Mongobee runner = new Mongobee("mongodb://localhost:27017/frogbot");
        runner.setDbName("frogbot");
        runner.setChangeLogsScanPackage("io.retrofrog.frogbot.migrations.changelogs");

        return runner;
    }
}