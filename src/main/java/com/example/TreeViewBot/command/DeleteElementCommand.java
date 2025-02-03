package com.example.TreeViewBot.command;

import com.example.TreeViewBot.entity.Element;
import com.example.TreeViewBot.repository.ElementRepository;
import com.example.TreeViewBot.service.SendBotMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;


/**
 * Команда для удаления элементов из дерева.
 * Реализует:
 *  <ol>
 *      <li>Удаление из дерева</li>
 *  </ol>
 */
@Slf4j
@Component
public class DeleteElementCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final ElementRepository elementRepository;

    public DeleteElementCommand(@Lazy SendBotMessageService sendBotMessageService, ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Transactional
    @Override
    public void execute(Update update, String[] args) {
        String message;
        switch (args.length) {
            case 1:
                message = deleteElement(args[0]);
                break;
            default:
                message = "Кол-во аргументов превысило максимум (1)";
                log.warn(message);
        }

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }

    /**
     * Удаляет элемент из дерева со всеми его потомками, если они присутствуют.
     *
     * @param deletableElementName имя удаляемого эл-та
     * @return сообщение об успешном удалении
     */
    private String deleteElement(String deletableElementName) {
        Optional<Element> deletableElementOpt = elementRepository.findByName(deletableElementName);
        Element deletableElement;

        if (deletableElementOpt.isEmpty()) {
            return "Элемент " + deletableElementName + " не присутствует в дереве";
        } else {
            deletableElement = deletableElementOpt.get();
        }

        Element parent = deletableElement.getParentElement();
        if (parent != null) {
            parent.getChildren().remove(deletableElement);
        }

        elementRepository.deleteById(deletableElement.getId());

        return "Элемент " + deletableElementName + " удален";
    }

    @Override
    public CommandName getType() {
        return CommandName.DELETE_ELEMENT;
    }
}
