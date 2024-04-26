package com.ocado.demo.web;

import com.ocado.demo.sqs.sending.SqsTemplateWrapper;
import com.ocado.demo.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqsSendingController {
    //<editor-fold desc="// private fields">
    private static final Logger logger = LoggerFactory.getLogger(SqsSendingController.class);
    @Value("${sqs.queue}")
    private String queue;
    private final SqsTemplateWrapper sqsSendingService;
    private final TenantContext tenantContext;
    //</editor-fold>

    //<editor-fold desc="// default constructor">
    public SqsSendingController(SqsTemplateWrapper sqsTemplateWrapper, TenantContext tenantContext) {
        this.sqsSendingService = sqsTemplateWrapper;
        this.tenantContext = tenantContext;
    }

    //</editor-fold>

    @PostMapping(value = "/{tenantId}/send")
    public String sendMessage(@RequestBody String payload, @PathVariable String tenantId) {
        //<editor-fold desc="// logs">
        logger.info("----Step 1. Sending----");
        logger.info("a. Receive REST call with tenant ID \"{}\"", tenantId);
        //</editor-fold>

        tenantContext.set(tenantId);
        sqsSendingService.send(queue, payload);

        //<editor-fold desc="// return REST response">
        return String.format("Your message with tenant ID \"%s\" has been sent", tenantId);
        //</editor-fold>
    }
}
