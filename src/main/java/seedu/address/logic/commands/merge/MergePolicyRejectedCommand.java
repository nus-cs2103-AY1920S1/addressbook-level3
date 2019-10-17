package seedu.address.logic.commands.merge;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.model.Model;

/**
 * Rejects an update of a data field of a duplicate {@code Policy} in the Addressbook.
 */
public class MergePolicyRejectedCommand extends Command {

    public static final String COMMAND_WORD = "no";

    public static final String MESSAGE_MERGE_FIELD_NOT_EXECUTED = "%1$s not updated.";

    private MergePolicyCommand previousMergeCommand;


    /**
     * Creates an {@code MergePolicyRejectedCommand}.
     */
    public MergePolicyRejectedCommand(MergePolicyCommand previousMergeCommand) {
        requireNonNull(previousMergeCommand);
        this.previousMergeCommand = previousMergeCommand;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        String fieldType = previousMergeCommand.getNextMergeFieldType();
        if (isLastMerge()) {
            return new CommandResult(String.format(MESSAGE_MERGE_FIELD_NOT_EXECUTED, fieldType)
                    + "\n" + String.format(previousMergeCommand.MESSAGE_SUCCESS,
                    previousMergeCommand.getOriginalPolicy()));
        } else {
            previousMergeCommand.removeFirstDifferentField();
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
                || (other instanceof MergePolicyRejectedCommand // instanceof handles nulls
                && previousMergeCommand.equals(((MergePolicyRejectedCommand) other).previousMergeCommand));
    }
}
