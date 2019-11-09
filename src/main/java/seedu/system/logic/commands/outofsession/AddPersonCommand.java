package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;
import static seedu.system.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.system.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.person.Person;
//@@author HoWeiChin
/**
 * Adds a person to the system.
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "addPerson";
    public static final CommandType COMMAND_TYPE = CommandType.PERSON;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_DOB + "DATE OF BIRTH "
        + PREFIX_GENDER + "GENDER "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_DOB + "12/02/1995 "
        + PREFIX_GENDER + "male ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddPersonCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddPersonCommand // instanceof handles nulls
            && toAdd.equals(((AddPersonCommand) other).toAdd));
    }
}
