package com.example.TreeViewBot.bot;

import com.example.TreeViewBot.command.CommandContainer;
import com.example.TreeViewBot.command.CommandName;
import com.example.TreeViewBot.config.BotConfig;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.lang.reflect.Array;
import java.util.Arrays;

import static com.example.TreeViewBot.command.CommandName.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class TreeViewBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    public final static String COMMAND_PREF = "/";

    private final CommandContainer commandContainer;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();

            if (message.startsWith(COMMAND_PREF)) {
                // убираю лишние пробелы между частями
                String[] parts = Arrays.stream(message.split(" "))
                        .filter(part -> !part.isEmpty())
                        .toArray(String[]::new);
                // вычленяем команду (в сообщении также могут быть аргументы)
                String commandName = parts[0];

                if (parts.length > 1 && parts.length <= 3) {
                    // вычленяем аргументы
                    String[] args = Arrays.copyOfRange(parts, 1, parts.length); // но без первого (команды)

                    // реализация для команд с аргументами
                    commandContainer.getCommand(commandName).execute(update, args);
                } else {
                    // реализация для обычных команд
                    commandContainer.getCommand(commandName).execute(update);
                }
            } else {
                commandContainer.getCommand(NO_COMMAND.getCommandName()).execute(update);
            }
        }
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            log.error("Не удалось зарегистрировать бота {}: {}", this.getBotUsername(), e.getMessage());
        }
    }
}
