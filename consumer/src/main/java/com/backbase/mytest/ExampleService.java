package com.backbase.mytest;

import com.backbase.buildingblocks.backend.communication.event.EnvelopedEvent;
import com.backbase.buildingblocks.backend.communication.event.handler.EventHandler;
import com.backbase.service.example.event.spec.v1.TestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExampleService implements EventHandler<TestEvent> {

    private static final Logger log = LoggerFactory.getLogger(ExampleService.class);

    /**
     * The <code>handle</code> method is invoked for processing of an EnvelopedEvent.
     */
    @Override
    public void handle(EnvelopedEvent<TestEvent> eventWrapper) {

        log.info("Received event with data: {}", eventWrapper.getEvent().getData());

        throw new IllegalStateException();
    }
}
