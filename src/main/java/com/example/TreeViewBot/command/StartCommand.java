package com.example.TreeViewBot.command;

import com.example.TreeViewBot.service.SendBotMessageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Начало работы с ботом {@link Command}
 */
@Component
public class StartCommand implements Command {
    
    private final SendBotMessageService sendBotMessageService;

    private final String START_MESSAGE = String.format("Привет! Этот бот сделан для того," +
            " чтобы показывать древовидное и форматированное представление каталогов. В некотором роде" +
            " аналог файловой системы в какой нибудь ОС\n\n" +
            "Чтобы посмотреть список всех комманд введи /help");

    StartCommand(@Lazy SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), START_MESSAGE);
    }

    @Override
    public CommandName getType() {
        return CommandName.START;
    }
}
