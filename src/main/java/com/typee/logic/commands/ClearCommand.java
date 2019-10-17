package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;

import com.typee.model.EngagementList;
import com.typee.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Engagement list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setHistoryManager(new EngagementList());
        model.saveEngagementList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
