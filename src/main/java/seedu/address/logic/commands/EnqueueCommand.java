package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.core.CommandResult;
import seedu.address.logic.commands.core.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class EnqueueCommand extends UndoableCommand {

    public static final String MESSAGE_SUCCESS = "New person added to the queue: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the queue";
    public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo successful! Person '%1$s' has been removed from the queue.";
    public static final String MESSAGE_UNDO_ADD_ERROR = "Could not undo the addition of person: %1$s";

    public static final String COMMAND_WORD = "enqueue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enqueues a patient to the queue. "
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

    private final Person toAdd;


    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public EnqueueCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPatient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPatient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPatient(toAdd)) {
            throw new CommandException(String.format(MESSAGE_UNDO_ADD_ERROR, toAdd));
        }

        model.removePatient(toAdd);
        return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EnqueueCommand // instanceof handles nulls
                && toAdd.equals(((EnqueueCommand) other).toAdd));
    }
}
