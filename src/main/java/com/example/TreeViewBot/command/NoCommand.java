package com.example.TreeViewBot.command;

import com.example.TreeViewBot.service.SendBotMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


/**
 * Любой текст без слэша {@link Command}.
 */
@Slf4j
@Component
public class NoCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private final String NO_COMMAND_MESSAGE = "Извините, но я распознаю команды, начинающиеся строго со слэша (/)\n\n" +
            "Чтобы посмотреть список поддерживаемых команд напишите /help";

    public NoCommand(@Lazy SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), NO_COMMAND_MESSAGE);
        log.warn("Подозрительная команда \"{}\" из чата с id {}", update.getMessage().getText(), update.getMessage().getChatId());
    }

    @Override
    public CommandName getType() {
        return CommandName.NO_COMMAND;
    }
}
