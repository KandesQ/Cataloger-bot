package com.example.TreeViewBot.command;

import com.example.TreeViewBot.service.SendBotMessageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.TreeViewBot.command.CommandName.*;

/**
 * Навигация (Help) {@link Command}
 */
@Component
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private final String HELP_MESSAGE = String.format("Вот список команд:\n\n" +
            "%s - информация о боте\n" +
            "%s - получение всех команд бота\n" +
            "%s - вывод форматированной структуры\n" +
            "%s <название элемента> - добавление корневого элемента в главный каталог\n" +
            "%s <родительский элемент> <дочерний элемент> - добавление дочернего элемента к родительскому\n" +
            "%s <название элемента> - удаление элемента и его потомков",
            START.getCommandName(), HELP.getCommandName(), VIEW_TREE.getCommandName(),
            ADD_ELEMENT.getCommandName(), ADD_ELEMENT.getCommandName(), DELETE_ELEMENT.getCommandName());

    public HelpCommand(@Lazy SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }

    @Override
    public CommandName getType() {
        return CommandName.HELP;
    }
}
