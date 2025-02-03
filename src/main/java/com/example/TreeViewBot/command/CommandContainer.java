package com.example.TreeViewBot.command;


import com.example.TreeViewBot.service.SendBotMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.TreeViewBot.command.CommandName.*;

/**
 * Контейнер, содержащий {@link Command}'ы, которые используются для обработки сообщений пользователя.
 */
@Slf4j
@Component
public class CommandContainer {

    private final Map<String, Command> commands = new HashMap<>();

    @Autowired
    public CommandContainer(List<Command> commands) {
        /**
         * Чтобы при создании новой команды вручную не прописывать их экземпляры,
         * использую автоматическую "регистрацию" всех бинов, помеченных интерфейсом {@link Command}
         */
        for (Command command: commands) {
            this.commands.put(command.getType().getCommandName(), command);
        }
    }

    /**
     * Геттер команды по ее имени
     *
     * @param commandName имя команды
     * @return команда по переданному имени
     */
    public Command getCommand(String commandName) {
        return commands.getOrDefault(commandName, commands.get(CommandName.UNKNOWN_COMMAND.getCommandName()));
    }
}
