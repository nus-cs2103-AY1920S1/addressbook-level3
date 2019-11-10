package seedu.address.logic.commands.merge;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.model.Model;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;

/**
 * Updates a data field of a duplicate {@code Policy} in the Addressbook.
 */
public class MergePolicyConfirmedCommand extends MergeConfirmedCommand {

    public static final String MESSAGE_MERGE_FIELD_SUCCESS = "Successfully updated %1$s";

    private static final Logger logger = LogsCenter.getLogger(MergePolicyConfirmedCommand.class);

    private MergePolicyCommand previousMergeCommand;


    /**
     * Creates an {@code MergePolicyConfirmedCommand} to update the original {@code Policy}.
     */
    public MergePolicyConfirmedCommand(MergePolicyCommand previousMergeCommand) {
        requireNonNull(previousMergeCommand);
        this.previousMergeCommand = previousMergeCommand;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        Policy originalPolicy = previousMergeCommand.getOriginalPolicy();
        Policy inputPolicy = previousMergeCommand.getInputPolicy();
        String fieldType = previousMergeCommand.getNextMergeFieldType();
        EditPolicyCommand.EditPolicyDescriptor editPolicyDescriptor = getEditPolicyDescriptor(fieldType, inputPolicy);
        logger.info("Executing merge: editing " + fieldType);
        EditPolicyCommand edit = new EditPolicyCommand();
        Policy editedPolicy = edit.executeForMerge(originalPolicy, editPolicyDescriptor, model);
        previousMergeCommand.updateOriginalPolicy(editedPolicy);
        if (isLastMerge()) {
            return new CommandResult(String.format(MESSAGE_MERGE_FIELD_SUCCESS, fieldType)
                    + "\n" + String.format(previousMergeCommand.MESSAGE_SUCCESS,
                    previousMergeCommand.getOriginalPolicy()));
        } else {
            previousMergeCommand.removeFirstDifferentField();
            String nextMerge = previousMergeCommand.getNextMergePrompt();
            return new CommandResult(String.format(MESSAGE_MERGE_FIELD_SUCCESS, fieldType)
                    + "\n" + nextMerge);
        }
    }

    /**
     * Creates an EditPolicyDescriptor to be used to update the field.
     *
     */
    public EditPolicyCommand.EditPolicyDescriptor getEditPolicyDescriptor(String fieldType, Policy inputPolicy) {
        EditPolicyCommand.EditPolicyDescriptor editPolicyDescriptor = new EditPolicyCommand.EditPolicyDescriptor();
        switch(fieldType) {
        case Description.DATA_TYPE:
            editPolicyDescriptor.setDescription(inputPolicy.getDescription());
            break;
        case Coverage.DATA_TYPE:
            editPolicyDescriptor.setCoverage(inputPolicy.getCoverage());
            break;
        case Price.DATA_TYPE:
            editPolicyDescriptor.setPrice(inputPolicy.getPrice());
            break;
        case StartAge.DATA_TYPE:
            editPolicyDescriptor.setStartAge(inputPolicy.getStartAge());
            break;
        case EndAge.DATA_TYPE:
            editPolicyDescriptor.setEndAge(inputPolicy.getEndAge());
            break;
        default:
            break;
        }
        return editPolicyDescriptor;
    }

    @Override
    public boolean isLastMerge() {
        return previousMergeCommand.onlyOneMergeLeft();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergePolicyConfirmedCommand // instanceof handles nulls
                && previousMergeCommand.equals(((MergePolicyConfirmedCommand) other).previousMergeCommand));
    }
}
