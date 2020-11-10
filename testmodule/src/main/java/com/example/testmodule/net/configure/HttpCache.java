package com.example.testmodule.net.configure;




import com.example.testmodule.util.CacheFileUtils;

import java.io.File;

import okhttp3.Cache;

/**
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        File cacheDirectory = new File(CacheFileUtils.getNetCachePath()); ;
        Cache cache = new Cache(cacheDirectory, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
        return cache;
    }
}
