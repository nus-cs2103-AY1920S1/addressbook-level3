package seedu.address.logic.commands.merge;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.DuplicatePolicyWithMergeException;
import seedu.address.model.Model;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;

/**
 * Merges the user input and the duplicate {@code Policy}.
 */
public class MergePolicyCommand extends MergeCommand {

    public static final String COMMAND_WORD = "mergepolicy";

    public static final String MESSAGE_SUCCESS = "Policy was successfully updated:\n%1$s";
    public static final String MERGE_COMMAND_PROMPT = "Do you wish to edit this policy's %1$s?";
    public static final String MERGE_ORIGINAL_HEADER = "Original: ";
    public static final String MERGE_INPUT_HEADER = "Input: ";
    public static final String MERGE_INSTRUCTIONS = "\nPlease press enter or 'yes' to proceed or 'no' to skip.";

    private static final Logger logger = LogsCenter.getLogger(MergePolicyCommand.class);

    private final Policy inputPolicy;
    private Policy originalPolicy;
    private ArrayList<String[]> differentFields = new ArrayList<>();


    /**
     * Creates an {@code MergePolicyCommand} to update the original {@code Policy} to the new {@code Policy}.
     */
    public MergePolicyCommand(Policy inputPolicy) {
        requireNonNull(inputPolicy);
        this.inputPolicy = inputPolicy;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePolicyWithMergeException {
        requireNonNull(model);
        logger.info("Initialising merge process...");
        this.originalPolicy = model.getPolicy(inputPolicy);
        getDifferences();
        assert(differentFields.size() != 0);
        logger.info(differentFields.size() + " differences found.");
        return new CommandResult(getNextMergePrompt());
    }

    public void updateOriginalPolicy(Policy editedPolicy) {
        this.originalPolicy = editedPolicy;
    }

    public String getNextMergePrompt() {
        String[] fieldThatIsDifferent = differentFields.get(0);
        String fieldType = fieldThatIsDifferent[0];
        String original = fieldThatIsDifferent[1];
        String input = fieldThatIsDifferent[2];
        StringBuilder mergePrompt = new StringBuilder();
        mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, fieldType) + "\n")
                .append(MERGE_ORIGINAL_HEADER + original + "\n")
                .append(MERGE_INPUT_HEADER + input)
                .append(MERGE_INSTRUCTIONS);
        return mergePrompt.toString();
    }

    /**
     * Returns an array of the first different field type, original field info and the input field info.
     *
     */
    public String[] getDifferences() {
        boolean hasDifferentDescription = !originalPolicy.getDescription().equals(inputPolicy.getDescription());
        if (hasDifferentDescription) {
            differentFields.add(new String[]{
                Description.DATA_TYPE, originalPolicy.getDescription().description,
                    inputPolicy.getDescription().description});
        }
        boolean hasDifferentCoverage = !originalPolicy.getCoverage().equals(inputPolicy.getCoverage());
        if (hasDifferentCoverage) {
            differentFields.add(new String[]{
                Coverage.DATA_TYPE, originalPolicy.getCoverage().coverage, inputPolicy.getCoverage().coverage});
        }
        boolean hasDifferentPrice = !originalPolicy.getPrice().equals(inputPolicy.getPrice());
        if (hasDifferentPrice) {
            differentFields.add(new String[]{
                Price.DATA_TYPE, originalPolicy.getPrice().price, inputPolicy.getPrice().price});
        }
        boolean hasDifferentStartAge = !originalPolicy.getStartAge().equals(inputPolicy.getStartAge());
        if (hasDifferentStartAge) {
            String originalStartAge = originalPolicy.getStartAge().age;
            if (originalStartAge.equals(StartAge.AGE_ZERO)) {
                originalStartAge = StartAge.MESSAGE_NO_MINIMUM_AGE;
            }

            String inputStartAge = inputPolicy.getStartAge().age;
            if (inputStartAge.equals(StartAge.AGE_ZERO)) {
                inputStartAge = StartAge.MESSAGE_NO_MINIMUM_AGE;
            }

            differentFields.add(new String[]{
                StartAge.DATA_TYPE, originalStartAge, inputStartAge});
        }
        boolean hasDifferentEndAge = !originalPolicy.getEndAge().equals(inputPolicy.getEndAge());
        if (hasDifferentEndAge) {
            String originalEndAge = originalPolicy.getEndAge().age;
            if (originalEndAge.equals(EndAge.AGE_INFINITY)) {
                originalEndAge = EndAge.MESSAGE_NO_MAXIMUM_AGE;
            }

            String inputEndAge = inputPolicy.getEndAge().age;
            if (inputEndAge.equals(EndAge.AGE_INFINITY)) {
                inputEndAge = EndAge.MESSAGE_NO_MAXIMUM_AGE;
            }
            differentFields.add(new String[]{
                EndAge.DATA_TYPE, originalEndAge, inputEndAge});
        }

        return null;
    }

    public void removeFirstDifferentField() {
        differentFields.remove(0);
    }

    public String getNextMergeFieldType() {
        return differentFields.get(0)[0];
    }

    public Policy getInputPolicy() {
        return this.inputPolicy;
    }

    public Policy getOriginalPolicy() {
        return this.originalPolicy;
    }

    /**
     * Checks if this is the last merge in the merge process.
     * @return
     */
    public boolean onlyOneMergeLeft() {
        return differentFields.size() == 1;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergePolicyCommand // instanceof handles nulls
                && inputPolicy.equals(((MergePolicyCommand) other).inputPolicy));
    }
}
