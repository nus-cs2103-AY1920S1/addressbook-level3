package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.model.Model;

/**
 * Adds a person to the address book.
 */
public class MergeRejectedCommand extends Command {

    public static final String COMMAND_WORD = "no";

    public static final String MESSAGE_MERGE_FIELD_NOT_EXECUTED = "%1$s not updated.";

    private MergeCommand previousMergeCommand;


    /**
     * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
     */
    public MergeRejectedCommand(MergeCommand previousMergeCommand) {
        requireNonNull(previousMergeCommand);
        this.previousMergeCommand = previousMergeCommand;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        String fieldType = previousMergeCommand.getNextMergeFieldType();
        previousMergeCommand.removeFirstDifferentField();
        if (isLastMerge()) {
            return new CommandResult(String.format(MESSAGE_MERGE_FIELD_NOT_EXECUTED, fieldType)
                    + "\n" + String.format(previousMergeCommand.MESSAGE_SUCCESS,
                    previousMergeCommand.getOriginalPerson()));
        } else {
            String nextMerge = previousMergeCommand.getNextMergePrompt();
            return new CommandResult(String.format(MESSAGE_MERGE_FIELD_NOT_EXECUTED, fieldType)
                    + "\n" + nextMerge);
        }
    }

    public boolean isLastMerge() {
        return previousMergeCommand.onlyOneMergeLeft();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeRejectedCommand // instanceof handles nulls
                && previousMergeCommand.equals(((MergeRejectedCommand) other).previousMergeCommand));
    }
}
