package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import com.typee.model.Model;
import com.typee.model.engagement.Engagement;
import com.typee.model.util.EngagementComparator;

/**
 * Lists all engagements in the engagement list to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all appointments";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the engagement list displayed in customised order.\n"
            + "Parameters: [property] + [ ascending] or [ descending]\n"
            + "Example: " + COMMAND_WORD + " start ascending";

    private Comparator<Engagement> comparator;

    public SortCommand(EngagementComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setComparator(comparator);
        model.updateSortedEngagementList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && comparator.equals(((SortCommand) other).comparator)); // state check
    }
}
