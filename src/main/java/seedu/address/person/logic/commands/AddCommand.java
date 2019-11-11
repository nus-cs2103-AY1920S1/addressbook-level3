package seedu.address.person.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.util.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.util.CliSyntax.PREFIX_EMAIL;
import static seedu.address.util.CliSyntax.PREFIX_NAME;
import static seedu.address.util.CliSyntax.PREFIX_PHONE;
import static seedu.address.util.CliSyntax.PREFIX_TAG;

import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.util.CommandResult;


/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book.\n"
            + "Parameters:\n"
            + PREFIX_NAME + "NAME"
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.transaction.logic.Logic transactionLogic,
                                 seedu.address.reimbursement.logic.Logic reimbursementLogic) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
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
