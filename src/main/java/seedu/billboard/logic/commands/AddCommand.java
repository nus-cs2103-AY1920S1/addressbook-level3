package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.logic.parser.CliSyntax.*;


import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.person.Expense;

/**
 * Adds a expense to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a expense to the address book. "
//            + "Parameters: "
//            + PREFIX_NAME + "NAME "
//            + PREFIX_PHONE + "PHONE "
//            + PREFIX_EMAIL + "EMAIL "
//            + PREFIX_ADDRESS + "ADDRESS "
//            + "[" + PREFIX_TAG + "TAG]...\n"
//            + "Example: " + COMMAND_WORD + " "
//            + PREFIX_NAME + "John Doe "
//            + PREFIX_PHONE + "98765432 "
//            + PREFIX_EMAIL + "johnd@example.com "
//            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
//            + PREFIX_TAG + "friends "
//            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a expense to the billboard. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Buy a book "
            + PREFIX_AMOUNT + "9.00"
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";;

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This expense already exists in the billboard";

    private final Expense toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AddCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRecord(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
