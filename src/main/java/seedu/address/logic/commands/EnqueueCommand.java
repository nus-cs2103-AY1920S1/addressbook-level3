package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.ReferenceId;

/**
 * Lists all persons in the address book to the user.
 */
public class EnqueueCommand extends ReversibleCommand {

    public static final String MESSAGE_SUCCESS = "New person added to the queue: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the queue";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person is not registered";
    public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo successful! Person '%1$s' has "
                                                        + " been removed from the queue.";
    public static final String MESSAGE_UNDO_ADD_ERROR = "Could not undo the addition of person: %1$s";

    public static final String COMMAND_WORD = "enqueue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enqueues a patient to the queue. "
            + "Parameters: "
            + "REFERENCE_ID ";

    private final ReferenceId toAdd;


    /**
     * Creates an EnqueueCommand to add the specified {@code Person}
     */
    public EnqueueCommand(ReferenceId id) {
        requireNonNull(id);
        toAdd = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        } else if (model.hasId(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPatient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPerson(toAdd)) {
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
