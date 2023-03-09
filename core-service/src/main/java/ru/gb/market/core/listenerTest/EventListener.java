package ru.gb.market.core.listenerTest;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener implements ApplicationListener<Event> {
    @Override
    public void onApplicationEvent(Event event) {
        Long seconds = 5000L;
        try {
            Thread.sleep(seconds);
            System.out.println("Received spring custom event - " + event.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}