package com.noctest.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 8/05/2016.
 */
@Component
public class WSSender {

    private SimpMessagingTemplate template;

    @Autowired
    public WSSender(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void deliver(String message) {
        this.template.convertAndSend("/topic/ferry/location", message);
    }



}
