package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;

import com.typee.model.Model;

/**
 * Lists all engagements in the engagement list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all engagements";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEngagementList(Model.PREDICATE_SHOW_ALL_ENGAGEMENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof ListCommand;
        }
    }

}
