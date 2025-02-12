package com.example.TreeViewBot.bot;

import com.example.TreeViewBot.service.commands.Command;
import com.example.TreeViewBot.service.CommandService;
import com.example.TreeViewBot.config.BotConfig;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.Nullable;
import java.util.Arrays;

import static com.example.TreeViewBot.service.CommandName.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class TreeViewBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    public final static String COMMAND_PREF = "/";

    private final CommandService commandService;

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
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String message = update.getMessage().getText().trim();

        if (!message.startsWith(COMMAND_PREF)) processMessage(NO_COMMAND.getCommandName(), update, null);

        // убираю лишние пробелы между частями
        String[] parts = Arrays.stream(message.split(" "))
                .filter(part -> !part.isEmpty())
                .toArray(String[]::new);
        // вычленяем команду (в сообщении также могут быть аргументы)
        String commandName = parts[0];

        String[] args = Arrays.copyOfRange(parts, 1, parts.length); // но без первого (команды)

        processMessage(commandName, update, args);
    }

        @EventListener({ContextRefreshedEvent.class})
        public void init () throws TelegramApiException {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

            try {
                telegramBotsApi.registerBot(this);
            } catch (TelegramApiException e) {
                log.error("Не удалось зарегистрировать бота {}: {}", this.getBotUsername(), e.getMessage());
            }
        }


        /**
         * Обрабатывает команды и отправляет ответ юзеру в чат.
         *
         * @param commandName - имя команды
         * @param update      - вся инфа про чат/пользователя
         * @param args        - аргументы, переданные пользователем
         */
        private void processMessage (String commandName, Update update, @Nullable String[]args){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());

            try {
                if (args == null) {
                    sendMessage.setText(commandService.getCommand(commandName).execute(update));
                    this.execute(sendMessage);
                    return;
                }

                if (args.length == 0 && commandService.getCommand(commandName).isOnlyArgsCommand()) {
                    sendMessage.setText(String.format("Вы передали неправильное количество аргуметов." + Command.HELP_TEXT));
                    this.execute(sendMessage);
                    return;
                }

                switch (args.length) {
                    case 0:
                        sendMessage.setText(commandService.getCommand(commandName).execute(update));
                        break;
                    case 1, 2:
                        sendMessage.setText(commandService.getCommand(commandName).execute(update, args));
                        break;
                    default:
                        log.info("Пользователь передал неверное кол-во аргументов: {}", args.length);
                        sendMessage.setText(String.format("Вы передали неправильное количество аргуметов." + Command.HELP_TEXT));
                        this.execute(sendMessage);
                }

                this.execute(sendMessage);

                log.info("Сообщение отправлено в чат {}", update.getMessage().getChatId());
            } catch (TelegramApiException e) {
                log.error("Не удалось отправить сообщение в чат с id {}: {}", update.getMessage().getChatId(), e.getMessage());
            }
        }
    }
