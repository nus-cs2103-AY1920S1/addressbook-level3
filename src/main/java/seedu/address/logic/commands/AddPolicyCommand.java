package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DuplicatePolicyException;
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
            + "[" + PREFIX_END_AGE + "END AGE]"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Senior Care "
            + PREFIX_DESCRIPTION + "Insurance for elderly "
            + PREFIX_COVERAGE + "10 months "
            + PREFIX_PRICE + "$50000 "
            + PREFIX_START_AGE + "50 "
            + PREFIX_END_AGE + "75 "
            + PREFIX_TAG + "senior insurance ";

    public static final String MESSAGE_SUCCESS = "New policy added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This policy already exists in the address book";
    public static final String DUPLICATE_PERSON_MERGE_PROMPT = "Do you wish to edit this policy's information?";

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
            StringBuilder exceptionMessage = new StringBuilder();
            exceptionMessage.append(MESSAGE_DUPLICATE_PERSON + "\n");
            exceptionMessage.append(model.getPolicy(toAdd).toString() + "\n");
            exceptionMessage.append(DUPLICATE_PERSON_MERGE_PROMPT);
            throw new DuplicatePolicyException(exceptionMessage.toString());
        }
        model.addPolicy(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPolicyCommand // instanceof handles nulls
                && toAdd.equals(((AddPolicyCommand) other).toAdd));
    }
}
