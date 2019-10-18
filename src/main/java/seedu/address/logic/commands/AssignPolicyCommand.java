package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;

/**
 * Command to assign a new policy to a person.
 */
public class AssignPolicyCommand extends Command {

    public static final String COMMAND_WORD = "assignpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns the policy to a person.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_POLICY + "POLICY NAME\n"
            + "Example: "
            + COMMAND_WORD + " 1 "
            + PREFIX_POLICY + "health insurance";

    public static final String MESSAGE_ASSIGN_POLICY_SUCCESS = "Assigned Policy: %1$s to Person: %2$s";
    public static final String MESSAGE_ALREADY_ASSIGNED = "Person: %1$s already has the Policy: %2$s.";
    public static final String MESSAGE_POLICY_NOT_FOUND = "Policy: %1$s not found in the list of policies.";

    private final PolicyName policyName;
    private final Index personIndex;

    /**
     * @param personIndex Index of the person to be assigned
     * @param policyName Name of the policy to assign
     */
    public AssignPolicyCommand(Index personIndex, PolicyName policyName) {
        requireNonNull(policyName);
        requireNonNull(personIndex);

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

        if (person.hasPolicy(policy)) {
            throw new CommandException(String.format(MESSAGE_ALREADY_ASSIGNED, person.getName(), policy.getName()));
        }

        Policy copyPolicy = new PolicyBuilder(policy).build();
        Person assignedPerson = new PersonBuilder(person).addPolicies(copyPolicy).build();

        model.setPerson(person, assignedPerson);
        // to maintain the model's state for undo/redo
        model.saveAddressBookState();
        return new CommandResult(String.format(MESSAGE_ASSIGN_POLICY_SUCCESS,
                policy.getName(), assignedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignPolicyCommand)) {
            return false;
        }

        // state check
        AssignPolicyCommand e = (AssignPolicyCommand) other;
        return personIndex.equals(e.personIndex)
                && policyName.equals(e.policyName);
    }

}
