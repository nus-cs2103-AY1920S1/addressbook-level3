package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;

/**
 * Command to assign a new policy to a person.
 */
public class UnassignPolicyCommand extends Command {

    public static final String COMMAND_WORD = "unassignpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an assigned policy from a person.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_POLICY + "POLICY NAME\n"
            + "Example: "
            + COMMAND_WORD + " 1 "
            + PREFIX_POLICY + "health insurance";

    public static final String MESSAGE_UNASSIGN_POLICY_SUCCESS = "Unassigned Policy: %1$s from Person: %2$s";
    public static final String MESSAGE_ALREADY_UNASSIGNED = "Person: %1$s does not have the Policy: %2$s.";
    public static final String MESSAGE_POLICY_NOT_FOUND = "Policy: %1$s not found in list of policies.";


    private final Index personIndex;
    private final PolicyName policyName;

    /**
     * @param personIndex Index of the person to be unassigned
     * @param policyName Name of the policy to be assigned
     */
    public UnassignPolicyCommand(Index personIndex, PolicyName policyName) {
        requireNonNull(personIndex);
        requireNonNull(policyName);

        this.personIndex = personIndex;
        this.policyName = policyName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (!model.hasPolicyWithName(policyName)) {
            throw new CommandException(String.format(MESSAGE_POLICY_NOT_FOUND, policyName));
        }

        Policy policy = model.getPolicyWithName(policyName);
        Person person = lastShownPersonList.get(personIndex.getZeroBased());

        if (!person.hasPolicy(policy)) {
            throw new CommandException(String.format(MESSAGE_ALREADY_UNASSIGNED,
                    person.getName(), policy.getName()));
        }

        Person assignedPerson = new PersonBuilder(person).removePolicies(policy).build();

        model.setPerson(person, assignedPerson);
        // to maintain the model's state for undo/redo
        model.saveAddressBookState();
        return new CommandResult(String.format(MESSAGE_UNASSIGN_POLICY_SUCCESS, policy.getName(),
                assignedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignPolicyCommand)) {
            return false;
        }

        // state check
        UnassignPolicyCommand e = (UnassignPolicyCommand) other;
        return personIndex.equals(e.personIndex)
                && policyName.equals(e.policyName);
    }

}
