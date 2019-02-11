package com.podcast.patrons.conf;

import com.patreon.PatreonAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatreonAPIBean {

    @Bean
    public PatreonAPI patreonAPI(@Value("${accessToken}") String accessToken) {
        return new PatreonAPI(accessToken);
    }
}
