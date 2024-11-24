package com.selflearn.tree.cacheConfig;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class CacheConfig {

    public static HttpHeaders createNoCacheHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }

}
