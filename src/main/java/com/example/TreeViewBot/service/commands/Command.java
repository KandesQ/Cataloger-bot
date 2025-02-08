package com.example.TreeViewBot.service.commands;

import com.example.TreeViewBot.service.CommandName;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Единый интерфейс для всех команд
 */
public interface Command {
    String HELP_TEXT = String.format("\n\nЧтобы посмотреть список поддерживаемых команд напишите %s",
            CommandName.HELP.getCommandName());

    /**
     * Главный метод, исполняющий командую логику
     *
     * @param update {@link Update} объект со всей нужной инофрмацией для исполнения команды
     */
    default String execute(Update update) {
        return "";
    }

    /**
     * Перегруженный дефолтный метод для команд с аргументами
     *
     * @param update {@link Update} объект со всей нужной инофрмацией для исполнения команды
     * @param args   массив аргументов/названий будущих элементов
     */
    default String execute(Update update, String[] args) {
        return "";
    }

    /**
     * @return тип комманды {@link CommandName}
     */
    CommandName getType();

    default String getCommandInfo() {return "";}

    /**
     * Отвечает на вопрос вызывается ли команда только с аргументами
     *
     * @return имеет ли команда аргументы
     */
    boolean isOnlyArgsCommand();

    /**
     * Я решил что правильнее сделать isOnlyArgsCommand и hasArgs.
     * Может быть случай, что команда можно будет вызываться как с аргументами, так и без них,
     *
     * В моем приложении пока нет (возможно будут) команд, которые могут вызываться с аргументами и без, но
     * в качестве демонстрации, для таких команд можно реализовать данный метод.
     *
     * Если у команду можно вызвать с аргументами и без, то hasArgs() = true, isOnlyArgsCommand() = false
     * Если у команду можно вызвать только с аргументами, то hasArgs() = true, isOnlyArgsCommand() = true
     * Если у команду можно вызвать только без аргументов, то hasArgs() = false, isOnlyArgsCommand() = false
     *
     * Таким образом достигается гибкость кода.
     */
    boolean hasArgs();
}
