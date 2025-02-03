package com.example.TreeViewBot.service;


/**
 * Сервис для отправки сообщений через бота
 */
public interface SendBotMessageService {
    /**
     * Отправка сообщений через бота
     *
     * @param chatId это идентификатор чата, куда будут отправляться сообщения
     * @param message - сообщение, которое будет отправлено
     */
    void sendMessage(Long chatId, String message);
}
