package com.example.TreeViewBot.command;

import com.example.TreeViewBot.entity.Element;
import com.example.TreeViewBot.repository.ElementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.Optional;

/**
 * Команда для добавления новых элементов в дерево.
 * Реализует:
 *  <ol>
 *      <li>добавление родителя в дерево</li>
 *      <li>добавление элемента к существующему родителю</li>
 *  </ol>
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class AddElementCommand implements Command {

    private final ElementRepository elementRepository;

    @Transactional // чтобы избежать LazyInitializationException, все будет в рамках одной транзакции
    @Override
    public String execute(Update update, String[] args) {
        String message;
        switch (args.length) {
            case 1:
                message = addElement(args[0]);
                break;
            case 2:
                message = addElement(args[0], args[1]);
                break;
            default:
                message = "Кол-во аргументов превысило максимум (2)";
                log.warn(message);
        }

        return message;
    }

    /**
     * Реализует добавление родителя в дерево и
     * добавление элемента к существующему родителю в зависимости от кол-ва аргуметов
     *
     * @param elements элементы (родительский & дочерний или только родительский)
     * @return сообщение об успехе сохранения в структуре
     */
    private String addElement(String... elements) {
        String message;

        switch (elements.length) {
            case 1:
                Element newElement = new Element(elements[0]);
                elementRepository.save(newElement);
                message = "Родитель " + elements[0] + " добавлен";
                break;
            case 2:
                Optional<Element> parentElementOpt = elementRepository.findByName(elements[0]);
                Element parentElement;

                if (parentElementOpt.isEmpty()) {
                    return "Родитель " + elements[0] + " не найден";
                } else {
                    parentElement = parentElementOpt.get();
                }

                Element childElement = new Element(elements[1]);

                childElement.setParentElement(parentElement);

                parentElement.getChildren().add(childElement);

                elementRepository.save(childElement);
                elementRepository.save(parentElement);

                message = "Потомок " + elements[1] + " добавлен родителю " + elements[0];
                break;
            default:
                message = "Массив {} содержит неверное кол-во аргументов";
                log.error(message, Arrays.toString(elements));
        }

        return message;
    }

    @Override
    public CommandName getType() {
        return CommandName.ADD_ELEMENT;
    }

    @Override
    public boolean isOnlyArgsCommand() {
        return true;
    }

    @Override
    public boolean hasArgs() {
        return true;
    }
}
