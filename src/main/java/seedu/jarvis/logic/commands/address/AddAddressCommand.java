package seedu.jarvis.logic.commands.address;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddAddressCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
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

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted Person: %1$s";
    public static final String MESSAGE_INVERSE_PERSON_NOT_FOUND = "Person already deleted: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Person toAdd;

    /**
     * Creates an {@code AddAddressCommand} to add the specified {@code Person}.
     */
    public AddAddressCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Adds {@code Person} to the address book, if person is not already inside address book.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that person was added successfully.
     * @throws CommandException If there already is a {@code Person} matching the person
     * to be added in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Deletes {@code Person} from address book that was added
     * by this command's execution if person is still in address book.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} that person was removed if person was in address book, else
     * {@code CommandResult} that person was already not in address book.
     * @throws CommandException If person to be removed is not found in the address book.
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPerson(toAdd)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_PERSON_NOT_FOUND, toAdd));
        }

        model.deletePerson(toAdd);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_DELETE, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAddressCommand // instanceof handles nulls
                && toAdd.equals(((AddAddressCommand) other).toAdd));
    }
}
