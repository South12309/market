package ru.gb.market.core.listenerTest;

import org.springframework.context.ApplicationEvent;

public class Event extends ApplicationEvent {
    private String message;

    public Event(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}