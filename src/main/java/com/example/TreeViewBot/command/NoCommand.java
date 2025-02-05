package com.example.TreeViewBot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


/**
 * Любой текст без слэша {@link Command}.
 */
@Slf4j
@Component
public class NoCommand implements Command {

    private final String NO_COMMAND_MESSAGE = String.format("Извините, но я распознаю команды, начинающиеся строго со слэша (/)" +
            Command.HELP_TEXT);

    @Override
    public String execute(Update update) {
        log.warn("Подозрительная команда \"{}\" из чата с id {}", update.getMessage().getText(), update.getMessage().getChatId());
        return NO_COMMAND_MESSAGE;
    }

    @Override
    public CommandName getType() {
        return CommandName.NO_COMMAND;
    }

    @Override
    public boolean isOnlyArgsCommand() {
        return false;
    }

    @Override
    public boolean hasArgs() {
        return false;
    }
}
