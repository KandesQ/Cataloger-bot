package com.example.TreeViewBot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class WrongArgsCommand implements Command {


    private final String WRONG_ARGUMENTS_GIVEN_MESSAGE = String.format("Вы передали неправильное количество аргуметов." +
            Command.HELP_TEXT);

    @Override
    public String execute(Update update) {
        return WRONG_ARGUMENTS_GIVEN_MESSAGE;
    }

    @Override
    public CommandName getType() {
        return CommandName.WRONG_ARGS;
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
