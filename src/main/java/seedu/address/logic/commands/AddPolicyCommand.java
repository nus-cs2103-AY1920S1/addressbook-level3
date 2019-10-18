package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_AGE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DuplicatePolicyWithMergeException;
import seedu.address.logic.commands.exceptions.DuplicatePolicyWithoutMergeException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;

/**
 * Adds a policy to the address book.
 */
public class AddPolicyCommand extends Command {

    public static final String COMMAND_WORD = "addpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a policy to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "POLICY NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_COVERAGE + "TIME_PERIOD "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_START_AGE + "START AGE]"
            + "[" + PREFIX_END_AGE + "END AGE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Senior Care "
            + PREFIX_DESCRIPTION + "Insurance for elderly "
            + PREFIX_COVERAGE + "months/10 "
            + PREFIX_PRICE + "$50000 "
            + PREFIX_START_AGE + "50 "
            + PREFIX_END_AGE + "75 ";

    public static final String MESSAGE_SUCCESS = "New policy added: %1$s";
    public static final String MESSAGE_DUPLICATE_POLICY = "This policy already exists in the address book.\n";
    public static final String MESSAGE_INPUT_INFORMATION_HEADER = "Your input:\n%1$s";
    public static final String DUPLICATE_POLICY_MERGE_PROMPT = "Do you wish to edit this policy's information?";
    public static final String NEW_LINE = "\n";

    private final Policy toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddPolicyCommand(Policy policy) {
        requireNonNull(policy);
        toAdd = policy;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPolicy(toAdd)) {
            String exceptionMessage = "";
            if (toAdd.hasEqualEditableFields(model.getPolicy(toAdd))) {
                exceptionMessage = generateExceptionMessageWithoutMergePrompt(model.getPolicy(toAdd));
                throw new DuplicatePolicyWithoutMergeException(exceptionMessage);
            } else {
                exceptionMessage = generateExceptionMessageWithMergePrompt(model.getPolicy(toAdd));
                throw new DuplicatePolicyWithMergeException(exceptionMessage);
            }
        }
        model.addPolicy(toAdd);
        // to maintain the model's state for undo/redo
        model.saveAddressBookState();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Generates an exception message with a merge prompt.
     * @param original Original policy in the addressbook.
     * @return The exception message to be thrown.
     */
    public String generateExceptionMessageWithMergePrompt(Policy original) {
        StringBuilder exceptionMessage = new StringBuilder();
        exceptionMessage.append(MESSAGE_DUPLICATE_POLICY);
        exceptionMessage.append(original.toString() + NEW_LINE);
        exceptionMessage.append(String.format(MESSAGE_INPUT_INFORMATION_HEADER, toAdd.toString()) + NEW_LINE);
        exceptionMessage.append(DUPLICATE_POLICY_MERGE_PROMPT);
        return exceptionMessage.toString();
    }

    /**
     * Generates an exception message without a merge prompt. This method is used if the input policy has all the same
     * compulsory data fields as the original policy.
     * @param original Original person in the addressbook.
     * @return The exception message to be thrown.
     */
    public String generateExceptionMessageWithoutMergePrompt(Policy original) {
        StringBuilder exceptionMessage = new StringBuilder();
        exceptionMessage.append(MESSAGE_DUPLICATE_POLICY);
        exceptionMessage.append(original.toString());
        return exceptionMessage.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPolicyCommand // instanceof handles nulls
                && toAdd.equals(((AddPolicyCommand) other).toAdd));
    }
}
