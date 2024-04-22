package com.ocado.demo.observability;

import com.ocado.demo.tenant.TenantContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantMetrics {
    private final ConcurrentHashMap<String, Integer> tenantCounter = new ConcurrentHashMap<>();
    private final TenantContext tenantContext;

    public TenantMetrics(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    public void increment() {
        var tenantId = tenantContext.get();
        tenantCounter.put(tenantId, tenantCounter.getOrDefault(tenantId, 0) + 1);
    }

    public Integer get() {
        var tenantId = tenantContext.get();
        return tenantCounter.getOrDefault(tenantId, 0);
    }
}
