package com.ocado.demo.observability;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantMetrics {
    private final ConcurrentHashMap<String, Integer> tenantCounter = new ConcurrentHashMap<>();

    public void increment(String tenantId) {
        tenantCounter.put(tenantId, tenantCounter.getOrDefault(tenantId, 0) + 1);
    }

    public Integer get(String tenantId) {
        return tenantCounter.getOrDefault(tenantId, 0);
    }
}
