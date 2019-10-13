package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a entry to the finance tracker. "
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_DESC + "NAME "
            + PREFIX_TIME + "TIME "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "expense "
            + PREFIX_DESC + "deck mala "
            + PREFIX_TIME + "13:00 "
            + PREFIX_AMOUNT + "5.50 "
            + PREFIX_TAG + "food "
            + PREFIX_TAG + "mala";

    public static final String MESSAGE_SUCCESS = "New entry added: %1$s";

    private final Entry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Entry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String type = toAdd.getType().toLowerCase();
        switch (type) {
        case "expense":
            model.addExpense((Expense) toAdd);
            break;
        case "income":
            model.addIncome((Income) toAdd);
            break;
        default:
            throw new CommandException("command not found");
        }

        /*if (toAdd.getType().equalsIgnoreCase("Expense")) {

        } else {
            model.addEntry(toAdd);
        }*/
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
