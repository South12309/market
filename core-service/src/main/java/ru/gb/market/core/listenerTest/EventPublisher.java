package ru.gb.market.core.listenerTest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCustomEvent(final String message) {
        Event customSpringEvent = new Event(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
