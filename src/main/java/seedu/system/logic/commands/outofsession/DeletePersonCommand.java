package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.system.commons.core.Messages;
import seedu.system.commons.core.index.Index;
import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.person.Person;
//@@author HoWeiChin
/**
 * Deletes a person identified using it's displayed index from the system.
 */
public class DeletePersonCommand extends Command {

    public static final String COMMAND_WORD = "deletePerson";
    public static final CommandType COMMAND_TYPE = CommandType.PERSON;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public DeletePersonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete), COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePersonCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePersonCommand) other).targetIndex)); // state check
    }
}
