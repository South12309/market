package ru.gb.market.core.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.market.core.listenerTest.EventPublisher;

@RestController
@RequestMapping("/api/v1/listener_test")
public class EventController {
    private final EventPublisher customEventPublisher;

    public EventController(EventPublisher customEventPublisher) {
        this.customEventPublisher = customEventPublisher;
    }

    @GetMapping
    public String root(){
        System.out.println("Entered into controller.");
        customEventPublisher.publishCustomEvent("publishCustomEvent event is OK");
        System.out.println("Finished.");
        return "listener done";
    }


}