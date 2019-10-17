package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;

import com.typee.commons.core.Messages;
import com.typee.model.Model;
import com.typee.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all engagements in engagement list whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all engagements whose descriptions contain any "
            + "of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " interview";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //model.updateFilteredEngagementList(predicate);
        //model.updateFilteredEngagementList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ENGAGEMENT_LISTED_OVERVIEW, model.getFilteredEngagementList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
