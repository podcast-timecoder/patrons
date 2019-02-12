package com.podcast.patrons.scheduler;


import com.podcast.patrons.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.podcast.patrons.conf.CachesConst.PATRONS;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerTasks {

    private final CacheService cacheService;

    @Scheduled(cron = "0 0 6 * * *")
    public void clearPatronsCaches() {
        log.info("Start clear patrons caches");

        cacheService.clearAllCaches(PATRONS);

        log.info("Finish clear patrons caches");
    }


}
