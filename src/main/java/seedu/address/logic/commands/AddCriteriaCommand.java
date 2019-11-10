package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CRITERIA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.tag.Tag;

/**
 * Adds a criteria to an existing policy in the address book.
 */
public class AddCriteriaCommand extends Command {

    public static final String COMMAND_WORD = "addcriteria";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds criteria to the policy identified "
            + "by the index number used in the last policy listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CRITERIA
            + "CRITERIA [MORE_CRITERIA] (will be converted to lowercase)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "cr/life cr/accident";

    private static final String MESSAGE_ADD_CRITERIA_SUCCESS = "Added criteria to Policy: %1$s";

    private final Index index;
    private final String[] criteria;

    public AddCriteriaCommand(Index index, String... criteria) {
        requireAllNonNull(index, criteria);
        this.index = index;
        this.criteria = criteria;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Policy> lastShownList = model.getFilteredPolicyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
        }

        if (criteria.length == 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        for (String criterion : criteria) {
            if ((criterion.length() == 0) || (criterion.matches("^.*[^a-z0-9 ].*$"))) {
                throw new CommandException(
                        String.format(
                                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                                MESSAGE_USAGE
                        )
                );
            }
        }

        Policy policyToEdit = lastShownList.get(index.getZeroBased());
        List<Tag> newCriteria = new ArrayList<>();

        for (String criterion : criteria) {
            newCriteria.add(new Tag(criterion));
        }

        Policy editedPolicy = new PolicyBuilder(policyToEdit).addCriteria(newCriteria).build();

        model.setPolicy(policyToEdit, editedPolicy);

        // Update persons with the edited policy
        for (Person p : model.getAddressBook().getPersonList()) {
            if (p.hasPolicy(policyToEdit)) {
                Person policyRemoved = new PersonBuilder(p).removePolicies(policyToEdit).build();
                Person editedPerson = new PersonBuilder(policyRemoved).addPolicies(editedPolicy).build();
                model.setPerson(p, editedPerson);
            }
        }

        // to maintain the model's state for undo/redo
        model.saveAddressBookState();
        return new CommandResult(generateSuccessMessage(editedPolicy));
    }

    private String generateSuccessMessage(Policy policyToEdit) {
        return String.format(MESSAGE_ADD_CRITERIA_SUCCESS, policyToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCriteriaCommand)) {
            return false;
        }

        AddCriteriaCommand e = (AddCriteriaCommand) other;
        return index.equals(e.index) && Arrays.equals(criteria, e.criteria);
    }
}
