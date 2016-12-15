package dev.gva.bookmarks.web;

import dev.gva.bookmarks.model.chat.Message;
import dev.gva.bookmarks.model.chat.OutputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class ChatController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/chatroom",method = RequestMethod.GET)
    public String viewApplication() {
        return "chat";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public OutputMessage sendMessage(Message message) {
        logger.info("Message sent");
        return new OutputMessage(message, new Date());
    }
}