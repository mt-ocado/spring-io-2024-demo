package com.ocado.demo.tenant.sqs;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TenantIdSqsListener {

    private final TenantContext tenantContext;

    @SqsListener("my-queue")
    public void listenMessage(String message) {
        var tenantId = tenantContext.getId();
        log.info("Hello, {}! Your tenant ID is \"{}\"", message, tenantId);
    }
}
