package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContextType;
import seedu.address.model.Model;
import seedu.address.model.activity.TitleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all contacts in SplitWiser whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";
    public static final String WARNING_INVALID_CONTEXT = "This command can only be used"
            + "while viewing the list of activities/contacts.";
    private final String searchTerm;
    private final String[] keywords;

    public FindCommand(String[] keywords, String searchTerm) {
        this.keywords = keywords;
        this.searchTerm = searchTerm;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ContextType type = model.getContext().getType();
        switch (type) {
        case LIST_CONTACT:
            model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
            return new CommandResult(String.format(Messages.MESSAGE_FOUND_BY_KEYWORD,
                    model.getFilteredPersonList().size(),
                    pluralize("contact", model.getFilteredPersonList().size()),
                    searchTerm));

        case LIST_ACTIVITY:
            model.updateFilteredActivityList(new TitleContainsKeywordsPredicate(Arrays.asList(keywords)));
            return new CommandResult(String.format(Messages.MESSAGE_FOUND_BY_KEYWORD,
                    model.getFilteredActivityList().size(),
                    pluralize("activity", model.getFilteredActivityList().size()),
                    searchTerm));
        default:
            throw new CommandException(WARNING_INVALID_CONTEXT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && Arrays.equals(this.keywords, ((FindCommand) other).keywords)
                && searchTerm.equals(((FindCommand) other).searchTerm));
    }

    /**
     * Helper method that helps to pluralize given string, depending on the number given.
     */
    private String pluralize(String input, int count) {
        if (count <= 1) {
            return input;
        }
        switch (input) {
        case "contact":
            return "contacts";
        case "activity":
            return "activities";
        default:
            return "";
        }
    }
}
