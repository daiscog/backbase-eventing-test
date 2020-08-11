package com.backbase.mytest;

import com.backbase.buildingblocks.backend.communication.context.OriginatorContextUtil;
import com.backbase.buildingblocks.backend.communication.event.EnvelopedEvent;
import com.backbase.buildingblocks.backend.communication.event.proxy.EventBus;
import com.backbase.buildingblocks.backend.internalrequest.InternalRequestContext;
import com.backbase.service.example.event.spec.v1.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class ExampleController  {

    @Autowired
    private EventBus eventBus;
    @Autowired
    private OriginatorContextUtil originatorContextUtil;
    @Autowired
    private InternalRequestContext internalRequestContext;

    @RequestMapping(method = RequestMethod.POST, value = "/message", produces = {
                    "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getMessage(@RequestBody Message message) {

        TestEvent event = new TestEvent();
        event.setData(message.getMessage());

        EnvelopedEvent<TestEvent> envelopedEvent = new EnvelopedEvent<>();
        envelopedEvent.setEvent(event);
        envelopedEvent.setOriginatorContext(originatorContextUtil.create(internalRequestContext));
        eventBus.emitEvent(envelopedEvent);

        return ResponseEntity
            .accepted()
            .body(message);
    }
}
