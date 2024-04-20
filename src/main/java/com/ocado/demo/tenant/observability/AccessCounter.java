package com.ocado.demo.tenant.observability;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccessCounter {
    private final ConcurrentHashMap<String, Integer> counterMap = new ConcurrentHashMap<>();

    public void increment(String tenantId) {
        counterMap.put(tenantId, counterMap.getOrDefault(tenantId, 0) + 1);
    }

    public Integer get(String tenantId) {
        return counterMap.getOrDefault(tenantId, 0);
    }
}
