package com.ocado.demo.web;

import com.ocado.demo.sqs.sending.CustomSqsTemplate;
import com.ocado.demo.tenant.TenantContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqsSendingController {
    @Value("${sqs.queue}")
    private String queue;
    private final CustomSqsTemplate customSqsTemplate;
    private final TenantContext tenantContext;

    public SqsSendingController(CustomSqsTemplate customSqsTemplate, TenantContext tenantContext) {
        this.customSqsTemplate = customSqsTemplate;
        this.tenantContext = tenantContext;
    }

    @PostMapping("/{tenantId}/send")
    public void sendMessage(@RequestBody String payload, @PathVariable String tenantId) {
        tenantContext.setId(tenantId);
        customSqsTemplate.send(queue, payload);
    }
}
