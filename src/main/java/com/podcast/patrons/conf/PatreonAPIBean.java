package com.podcast.patrons.conf;

import com.patreon.PatreonAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.podcast.patrons.conf.CachesConst.PATRONS;

@Configuration
public class PatreonAPIBean {

    @Bean
    public PatreonAPI patreonAPI(@Value("${accessToken}") String accessToken) {
        return new PatreonAPI(accessToken);
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(PATRONS);
    }
}
