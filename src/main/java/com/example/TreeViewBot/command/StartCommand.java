package com.example.TreeViewBot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Начало работы с ботом {@link Command}
 */
@Component
public class StartCommand implements Command {

    private final String START_MESSAGE = String.format("Привет! Этот бот сделан для того," +
            " чтобы показывать древовидное и форматированное представление каталогов. В некотором роде" +
            " аналог файловой системы в какой нибудь ОС." +
            Command.HELP_TEXT);

    @Override
    public String execute(Update update) {
        return START_MESSAGE;
    }

    @Override
    public String getCommandInfo() {
        return CommandName.START.getCommandName() + " - краткая информация о боте";
    }

    @Override
    public CommandName getType() {
        return CommandName.START;
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
