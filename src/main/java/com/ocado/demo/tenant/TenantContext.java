package com.ocado.demo.tenant;

import org.springframework.stereotype.Component;

@Component
public class TenantContext {

    private static final ThreadLocal<String> TENANT_ID = new ThreadLocal<>();

    public void set(String id) {
        TENANT_ID.set(id);
    }

    public String get() {
        return TENANT_ID.get();
    }
}
