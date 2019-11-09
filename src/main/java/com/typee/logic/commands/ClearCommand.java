package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;

import com.typee.model.EngagementList;
import com.typee.model.Model;

/**
 * Clears the engagement list.
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof ClearCommand;
        }
    }

}
