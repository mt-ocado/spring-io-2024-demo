package com.ocado.demo.tenant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TenantContext {

    private static final ThreadLocal<String> TENANT_ID = new ThreadLocal<>();

    public void setId(String id){
        TENANT_ID.set(id);
    }

    public String getId(){
        return TENANT_ID.get();
    }

    public void clearId(){
        TENANT_ID.remove();
    };
}
