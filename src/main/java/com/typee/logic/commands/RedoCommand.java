package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;

import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;

/**
 * Redos the last undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Successfully redone!";
    public static final String MESSAGE_FAILURE = "No commands to redo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (model.hasNoRedoableCommand()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoEngagementList();
        model.updateFilteredEngagementList(Model.PREDICATE_SHOW_ALL_ENGAGEMENTS);
        model.updateSortedEngagementList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RedoCommand)) {
            return false;
        } else {
            return true;
        }
    }
}
