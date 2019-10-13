package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TagMatchesPredicate;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all persons in address book whose tag matches the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags match the specified "
            + "keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends";

    private final TagMatchesPredicate predicate;

    public FilterCommand(TagMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Creates the message in the event that no tag has been found.
     * @return Suggestion of tags that exist which the user can search instead.
     */
    private String getNoMatchMessage() {
        StringBuilder message = new StringBuilder("There are no tags found matching your query.\n");
        message.append("Try these tags instead: ");
        Set<String> uniqueTags = new HashSet<String>(Tag.getAllTags());
        for (String tagName : uniqueTags) {
            message.append(tagName + " ");
        }
        return message.toString();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().size() == 0) {
            return new CommandResult(getNoMatchMessage());
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
