package com.example.TreeViewBot.service.commands;

import com.example.TreeViewBot.service.CommandName;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.TreeViewBot.service.CommandName.*;

/**
 * Навигация (Help) {@link Command}
 */
@Component
public class HelpCommand implements Command {

    private final ApplicationContext applicationContext;

    public HelpCommand(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public String execute(Update update) {
        StringBuilder message = new StringBuilder().append("Вот список команд:\n\n");

        for (Command command : applicationContext.getBeansOfType(Command.class).values()) {
            if (!command.getCommandInfo().isEmpty()) {
                message.append(command.getCommandInfo()).append("\n");
            }
        }

        return message.toString();
    }

    @Override
    public String getCommandInfo() {
        return HELP.getCommandName() + " - получение всех команд бота";
    }

    @Override
    public CommandName getType() {
        return CommandName.HELP;
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
