package seedu.address.logic.commands.merge;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.DuplicatePolicyWithMergeException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;

/**
 * Rejects merging a duplicate policy.
 */
public class DoNotMergePolicyCommand extends Command {

    public static final String COMMAND_WORD = "npmerge";

    public static final String MESSAGE_SUCCESS = "This profile was not updated : %1$s.";

    private final Policy inputPolicy;
    private Policy originalPolicy;
    private ArrayList<String[]> differentFields = new ArrayList<>();


    /**
     * Creates an DoNoMergePolicy to skip the merging of a field in a merging process.
     */
    public DoNotMergePolicyCommand(Policy inputPolicy) {
        requireNonNull(inputPolicy);
        this.inputPolicy = inputPolicy;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePolicyWithMergeException {
        requireNonNull(model);
        this.originalPolicy = model.getPolicy(inputPolicy);
        return new CommandResult(String.format(MESSAGE_SUCCESS, originalPolicy));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoNotMergePolicyCommand // instanceof handles nulls
                && inputPolicy.equals(((DoNotMergePolicyCommand) other).inputPolicy));
    }
}
