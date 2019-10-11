package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;
import seedu.address.model.tag.Tag;

/**
 * Command to assign a new policy to a person.
 */
public class AssignPolicyCommand extends Command {

    public static final String COMMAND_WORD = "assignpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns the policy to a person.\n"
            + "Parameters: "
            + PREFIX_POLICY_INDEX + "POLICY INDEX "
            + PREFIX_PERSON_INDEX + "PERSON INDEX\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_POLICY_INDEX + "1 "
            + PREFIX_PERSON_INDEX + "1";

    public static final String MESSAGE_ASSIGN_POLICY_SUCCESS = "Assigned Policy: %1$s to Person: %2$s";
    public static final String MESSAGE_ALREADY_ASSIGNED = "Person: %1$s already has the Policy: %2$s.";


    private final Index policyIndex;
    private final Index personIndex;

    /**
     * @param policyIndex Index of the policy to assign
     * @param personIndex Index of the person to be assigned
     */
    public AssignPolicyCommand(Index policyIndex, Index personIndex) {
        requireNonNull(policyIndex);
        requireNonNull(personIndex);

        this.policyIndex = policyIndex;
        this.personIndex = personIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Policy> lastShownPolicyList = model.getFilteredPolicyList();

        if (policyIndex.getZeroBased() >= lastShownPolicyList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
        }
        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Policy policy = lastShownPolicyList.get(policyIndex.getZeroBased());
        Person person = lastShownPersonList.get(personIndex.getZeroBased());

        if (person.hasPolicy(policy)) {
            throw new CommandException(String.format(MESSAGE_ALREADY_ASSIGNED,
                    person.getName(), policy.getName()));
        }

        Person assignedPerson = createAssignedPerson(person, policy);

        model.setPerson(person, assignedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ASSIGN_POLICY_SUCCESS,
                policy.getName(), assignedPerson.getName()));
    }

    /**
     * Creates and returns a {@code Policy} with the details of {@code policyToCopy}.
     */
    private static Policy copyPolicy(Policy policyToCopy) {
        requireNonNull(policyToCopy);

        PolicyName name = policyToCopy.getName();
        Description description = policyToCopy.getDescription();
        Coverage coverage = policyToCopy.getCoverage();
        Price price = policyToCopy.getPrice();
        StartAge startAge = policyToCopy.getStartAge();
        EndAge endAge = policyToCopy.getEndAge();
        Set<Tag> criteria = policyToCopy.getCriteria();
        Set<Tag> tags = policyToCopy.getTags();

        return new Policy(name, description, coverage, price, startAge, endAge, criteria, tags);
    }

    /**
     * Creates a returns a {@code Person} with the details of {@code person}
     * with an additional policy {@code policy}.
     */
    private static Person createAssignedPerson(Person person, Policy policy) {
        Name name = person.getName();
        Nric nric = person.getNric();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        DateOfBirth dateOfBirth = person.getDateOfBirth();
        Set<Policy> updatedPolicies = new HashSet<Policy>(person.getPolicies());
        updatedPolicies.add(copyPolicy(policy));
        Set<Tag> tags = person.getTags();

        return new Person(name, nric, phone, email, address, dateOfBirth, updatedPolicies, tags);
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
                && policyIndex.equals(e.policyIndex);
    }

}
