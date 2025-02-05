package com.example.TreeViewBot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Неизвестная команда {@link Command}.
 */
@Component
public class UnkownCommand implements Command {

    private final String UNKNOWN_COMMAND_MESSAGE = String.format("К сожалению, я не поддерживаю данную команду" +
            Command.HELP_TEXT);

    @Override
    public String execute(Update update) {
        return UNKNOWN_COMMAND_MESSAGE;
    }

    @Override
    public CommandName getType() {
        return CommandName.UNKNOWN_COMMAND;
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
