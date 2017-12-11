package io.retrofrog.frogbot.config;

import com.bugsnag.Bugsnag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BugsnagConfig {
    @Bean
    public Bugsnag bugsnag(@Value("${bugsnag.apiKey}") String apiKey,
                           @Value("${releaseStage:development}") String releaseStage) {
        Bugsnag b = new Bugsnag(apiKey);
        b.setReleaseStage(releaseStage);
        return b;
    }
}
