package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.food.NameContainsCloseExpiryDatePredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "rem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display list with all food expiring within r days "
            + "Parameters: "
            + PREFIX_REMINDER + "REMINDER \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER + "3";

    private final NameContainsCloseExpiryDatePredicate predicate;

    public ReminderCommand(NameContainsCloseExpiryDatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroceryItemList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredGroceryItemList().size()));
    }
}
