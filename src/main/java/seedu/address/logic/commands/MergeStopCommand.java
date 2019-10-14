package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class MergeStopCommand extends Command {

    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_MERGE_STOPPED = "Merging has been stopped.\n%1$s";

    private MergeCommand previousMergeCommand;

    /**
     * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
     */
    public MergeStopCommand(MergeCommand previousMergeCommand) {
        requireNonNull(previousMergeCommand);
        this.previousMergeCommand = previousMergeCommand;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        Person currentPerson = previousMergeCommand.getOriginalPerson();
        return new CommandResult(String.format(MESSAGE_MERGE_STOPPED, currentPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeStopCommand // instanceof handles nulls
                && previousMergeCommand.equals(((MergeStopCommand) other).previousMergeCommand));
    }
}
