package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
 * Deletes a tag of an existing policy in the address book.
 */
public class DeleteCriteriaCommand extends Command {

    public static final String COMMAND_WORD = "deletecriteria";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes criteria of the policy identified\n"
            + "by the index number used in the last policy listing. (case insensitive)"
            + "Parameters: INDEX (must be a positive integer) "
            + "cr/CRITERIA [MORE_CRITERIA]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "cr/life cr/accident";

    private static final String MESSAGE_DELETE_CRITERIA_SUCCESS = "Deleted criteria of Policy: %1$s";

    private static final String MESSAGE_CRITERIA_NOT_FOUND = "No matching criteria found of Policy: %1$s";

    private final Index index;
    private final String[] criteria;

    public DeleteCriteriaCommand(Index index, String... criteria) {
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
            throw new CommandException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_USAGE
                    )
            );
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
        List<Tag> removeCriteria = new ArrayList<>();

        for (String criterion : criteria) {
            removeCriteria.add(new Tag(criterion));
        }

        boolean criteriaRemoved = false;

        for (Tag removeCriterion : removeCriteria) {
            for (Tag criterion : policyToEdit.getCriteria()) {
                if (removeCriterion.equals(criterion)) {
                    criteriaRemoved = true;
                    break;
                }
            }
            if (criteriaRemoved) {
                break;
            }
        }

        Policy editedPolicy = new PolicyBuilder(policyToEdit)
                .removeCriteria(removeCriteria)
                .build();

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
        return new CommandResult(generateSuccessMessage(editedPolicy, criteriaRemoved));
    }

    /**
     * Parses {@code personToEdit} and {@code criteriaRemoved} and returns a {@code String}.
     */
    private String generateSuccessMessage(Policy policyToEdit, boolean criteriaRemoved) {
        if (criteriaRemoved) {
            return String.format(MESSAGE_DELETE_CRITERIA_SUCCESS, policyToEdit);
        } else {
            return String.format(MESSAGE_CRITERIA_NOT_FOUND, policyToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCriteriaCommand)) {
            return false;
        }

        DeleteCriteriaCommand e = (DeleteCriteriaCommand) other;
        return index.equals(e.index) && Arrays.equals(criteria, e.criteria);
    }
}
