package com.podcast.patrons.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    public void clearAllCaches(String cacheName) {
        cacheManager.getCache(cacheName).clear();
    }
}
