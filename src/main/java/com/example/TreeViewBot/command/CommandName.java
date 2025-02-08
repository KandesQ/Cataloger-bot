package com.example.TreeViewBot.command;

import lombok.Getter;


/**
 * Все возможные команды, которые обрабатывает бот
 */
@Getter
public enum CommandName {

    START("/start"),
    HELP("/help"),
    VIEW_TREE("/viewtree"),
    ADD_ELEMENT("/addelement"),
    DELETE_ELEMENT("/deleteelement"),
    NO_COMMAND("/no"),
    UNKNOWN_COMMAND("/unknown");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }
}
