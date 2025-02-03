package com.example.TreeViewBot.command;

import com.example.TreeViewBot.service.SendBotMessageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.sse.Sse;

/**
 * Неизвестная команда {@link Command}.
 */
@Component
public class UnkownCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private final String UNKNOWN_COMMAND_MESSAGE = "К сожалению, я не поддерживаю данную команду\n\n" +
            "Чтобы узнать список поддерживаемых команд, напишите /help";

    public UnkownCommand(@Lazy SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), UNKNOWN_COMMAND_MESSAGE);
    }

    @Override
    public CommandName getType() {
        return CommandName.UNKNOWN_COMMAND;
    }
}
