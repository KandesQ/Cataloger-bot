package com.example.TreeViewBot.service.commands;

import com.example.TreeViewBot.entity.Element;
import com.example.TreeViewBot.repository.ElementRepository;
import com.example.TreeViewBot.service.CommandName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Показывает дерево эл-тов
 */
@RequiredArgsConstructor
@Component
public class ViewTreeCommand implements Command {

    private final ElementRepository elementRepository;

    @Transactional
    @Override
    public String execute(Update update) {
        String message;

        if (elementRepository.findByParentElementIsNull().isEmpty()) {
            message = "В дереве не присутствует эл-тов";
        } else {
            message = getFormatTree();
        }

        return message;
    }

    private String getFormatTree() {
        List<Element> rootElements = elementRepository.findByParentElementIsNull();
        StringBuilder tree = new StringBuilder();

        for (int i = 0; i < rootElements.size(); i++) {
            buildTree(rootElements.get(i), tree, "");
        }

        return tree.toString();
    }


    private void buildTree(Element rootElement, StringBuilder tree, String prefix) {
        tree.append(prefix).append(rootElement.getName()).append("\n");


        List<Element> children = new ArrayList<>(rootElement.getChildren());
        children.sort(Comparator.comparing(Element::getName));

        for (int i = 0; i < children.size(); i++) {
            buildTree(children.get(i), tree, prefix + "\t ");
        }
    }

    @Override
    public String getCommandInfo() {
        return CommandName.VIEW_TREE.getCommandName() + " - вывод форматированной структуры";
    }

    @Override
    public CommandName getType() {
        return CommandName.VIEW_TREE;
    }

    @Override
    public boolean isOnlyArgsCommand() {
        return false;
    }

    @Override
    public boolean hasArgs() {
        return false;
    }
}
