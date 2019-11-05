package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.AutoExpense;

/**
 * Adds a entry to the guilttrip book.
 */
public class AddAutoExpenseCommand extends Command {

    public static final String COMMAND_WORD = "addAutoExp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a auto expense to the finance tracker.\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_DESC + "Mala "
            + PREFIX_AMOUNT + "5.50 "
            + PREFIX_FREQ + "daily "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_TAG + "indulgence\n"
            + "Required parameters:"
            + PREFIX_DESC + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_CATEGORY + "CATEGORY\n"
            + "Optional parameters:"
            + "["
            + PREFIX_FREQ + "weekly "
            + PREFIX_DATE + "DATE_TODAY "
            + PREFIX_TAG + "TAG...]\n";

    public static final String MESSAGE_SUCCESS = "New auto expense added: %1$s";

    private final AutoExpense toAdd;

    /**
     * Creates an AddAutoExpenseCommand to add the specified {@code Person}
     */
    public AddAutoExpenseCommand(AutoExpense autoExpense) {
        requireNonNull(autoExpense);
        toAdd = autoExpense;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.addAutoExpense(toAdd);

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAutoExpenseCommand // instanceof handles nulls
                        && toAdd.equals(((AddAutoExpenseCommand) other).toAdd));
    }
}
