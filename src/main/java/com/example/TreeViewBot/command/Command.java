package com.example.TreeViewBot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.TreeViewBot.service.SendBotMessageService;

/**
 * Единый интерфейс для всех команд
 */
public interface Command {

    /**
     * Главный метод, исполняющий командую логику
     * Выполняет отправку сообщения пользователю через {@link SendBotMessageService}
     *
     * @param update {@link Update} объект со всей нужной инофрмацией для исполнения команды
     */
    default void execute(Update update) {};

    /**
     * Перегруженный дефолтный метод для команд с аргументами
     * Выполняет отправку сообщения пользователю через {@link SendBotMessageService}
     *
     * @param update {@link Update} объект со всей нужной инофрмацией для исполнения команды
     * @param args массив аргументов/названий будущих элементов
     */
    default void execute(Update update, String[] args) {}

    /**
     *
     * @return тип комманды {@link CommandName}
     */
    CommandName getType();
}
