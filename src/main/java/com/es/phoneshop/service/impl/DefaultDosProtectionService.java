package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.service.DosProtectionService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static volatile DefaultDosProtectionService instance;

    private static final long THRESHOLD = 20;
    private static long lastTime;
    private static final long PERIOD = 60_000L;
    private Map<String, Long> countMap;

    private DefaultDosProtectionService() {
        countMap = new ConcurrentHashMap<>();
        lastTime = System.currentTimeMillis();
    }

    public static DefaultDosProtectionService getInstance() {
        DefaultDosProtectionService result = instance;
        if(result != null) {
            return result;
        }

        synchronized(DefaultDosProtectionService.class) {
            if(instance == null) {
                instance = new DefaultDosProtectionService();
            }
            return instance;
        }
    }

    @Override
    public boolean isAllowed(String ip) {
        updateTime();
        Long count = countMap.get(ip);
        if(count == null){
            count = 1L;
        } else {
            if(count > THRESHOLD){
                return false;
            }
            count++;
        }
        countMap.put(ip, count);
        return true;
    }

    private void updateTime(){
        if((System.currentTimeMillis() - lastTime) > PERIOD){
            countMap.clear();
            lastTime = System.currentTimeMillis();
        }
    }
}
