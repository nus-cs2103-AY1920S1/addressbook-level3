package seedu.address.logic.commands.merge;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

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
    public static final String ORIGINAL_HEADER = "Original: ";
    public static final String INPUT_HEADER = "Input: ";

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
        this.originalPolicy = model.getPolicy(inputPolicy);
        getDifferences();
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
                .append(ORIGINAL_HEADER + original + "\n")
                .append(INPUT_HEADER + input);
        return mergePrompt.toString();
    }

    /**
     * Returns an array of the first different field type, original field info and the input field info.
     *
     */
    private String[] getDifferences() {
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
            differentFields.add(new String[]{
                StartAge.DATA_TYPE, originalPolicy.getStartAge().age, inputPolicy.getStartAge().age});
        }
        boolean hasDifferentEndAge = !originalPolicy.getEndAge().equals(inputPolicy.getEndAge());
        if (hasDifferentEndAge) {
            differentFields.add(new String[]{
                EndAge.DATA_TYPE, originalPolicy.getEndAge().age, inputPolicy.getEndAge().age});
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
