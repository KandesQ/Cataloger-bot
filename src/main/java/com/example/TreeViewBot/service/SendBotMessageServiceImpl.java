package com.example.TreeViewBot.service;

import com.example.TreeViewBot.bot.TreeViewBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Реализация интерфейса {@link SendBotMessageService}
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final TreeViewBot treeViewBot;

    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            treeViewBot.execute(sendMessage);
            log.info("Сообщение отправлено в чат {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить сообщение в чат с id {}: {}", chatId, e.getMessage());
            e.printStackTrace();
        }
    }

}
