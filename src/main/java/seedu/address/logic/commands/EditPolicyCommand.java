package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_AGE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POLICIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing policy in the address book.
 */
public class EditPolicyCommand extends Command {

    public static final String COMMAND_WORD = "editpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the policy identified "
            + "by the index number used in the displayed policy list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_COVERAGE + "COVERAGE] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_START_AGE + "START_AGE]"
            + "[" + PREFIX_END_AGE + "END_AGE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            // Todo: check if price takes in a "$" sign
            + PREFIX_DESCRIPTION + "Fire Insurance Policy. Covers all ages up to 10 years."
            + PREFIX_PRICE + "$20000 ";

    public static final String MESSAGE_EDIT_POLICY_SUCCESS = "Edited Policy: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_POLICY = "This policy already exists in the address book.";

    private final Index index;
    private final EditPolicyDescriptor editPolicyDescriptor;

    /**
     * @param index of the policy in the filtered policy list to edit
     * @param editPolicyDescriptor details to edit the policy with
     */
    public EditPolicyCommand(Index index, EditPolicyDescriptor editPolicyDescriptor) {
        requireNonNull(index);
        requireNonNull(editPolicyDescriptor);

        this.index = index;
        this.editPolicyDescriptor = new EditPolicyDescriptor(editPolicyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Policy> lastShownList = model.getFilteredPolicyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
        }

        Policy policyToEdit = lastShownList.get(index.getZeroBased());
        Policy editedPolicy = createEditedPolicy(policyToEdit, editPolicyDescriptor);

        if (!policyToEdit.isSamePolicy(editedPolicy) && model.hasPolicy(editedPolicy)) {
            throw new CommandException(MESSAGE_DUPLICATE_POLICY);
        }

        model.setPolicy(policyToEdit, editedPolicy);
        model.updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);
        return new CommandResult(String.format(MESSAGE_EDIT_POLICY_SUCCESS, editedPolicy));
    }

    /**
     * Creates and returns a {@code Policy} with the details of {@code policy}
     * edited with {@code editPolicyDescriptor}.
     */
    private static Policy createEditedPolicy(Policy policyToEdit, EditPolicyDescriptor editPolicyDescriptor) {
        assert policyToEdit != null;

        // TODO: Edit policy details
        PolicyName updatedName = editPolicyDescriptor.getName().orElse(policyToEdit.getName());
        Description updatedDescription = editPolicyDescriptor.getDescription().orElse(policyToEdit.getDescription());
        Coverage updatedCoverage = editPolicyDescriptor.getCoverage().orElse(policyToEdit.getCoverage());
        Price updatedPrice = editPolicyDescriptor.getPrice().orElse(policyToEdit.getPrice());
        StartAge updatedStartAge = editPolicyDescriptor.getStartAge().orElse(policyToEdit.getStartAge());
        EndAge updatedEndAge = editPolicyDescriptor.getEndAge().orElse(policyToEdit.getEndAge());
        Set<Tag> updatedCriteria = policyToEdit.getCriteria();
        Set<Tag> updatedTags = policyToEdit.getTags();

        return new Policy(updatedName, updatedDescription, updatedCoverage, updatedPrice, updatedStartAge,
                updatedEndAge, updatedCriteria, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPolicyCommand)) {
            return false;
        }

        // state check
        EditPolicyCommand e = (EditPolicyCommand) other;
        return index.equals(e.index)
                && editPolicyDescriptor.equals(e.editPolicyDescriptor);
    }

    /**
     * Stores the details to edit the policy with. Each non-empty field value will replace the
     * corresponding field value of the policy.
     */
    public static class EditPolicyDescriptor {
        private PolicyName name;
        private Description description;
        private Coverage coverage;
        private Price price;
        private StartAge startAge;
        private EndAge endAge;
        private Set<Tag> criteria;
        private Set<Tag> tags;

        public EditPolicyDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPolicyDescriptor(EditPolicyDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
            setCoverage(toCopy.coverage);
            setPrice(toCopy.price);
            setStartAge(toCopy.startAge);
            setEndAge(toCopy.endAge);
            setCriteria(toCopy.criteria);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, description, coverage, price, startAge, endAge);
        }

        public void setName(PolicyName name) {
            this.name = name;
        }

        public Optional<PolicyName> getName() {
            return Optional.ofNullable(name);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setCoverage(Coverage coverage) {
            this.coverage = coverage;
        }

        public Optional<Coverage> getCoverage() {
            return Optional.ofNullable(coverage);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setStartAge(StartAge startAge) {
            this.startAge = startAge;
        }

        public Optional<StartAge> getStartAge() {
            return Optional.ofNullable(startAge);
        }

        public void setEndAge(EndAge endAge) {
            this.endAge = endAge;
        }

        public Optional<EndAge> getEndAge() {
            return Optional.ofNullable(endAge);
        }

        /**
         * Sets {@code criteria} to this object's {@code criteria}.
         * A defensive copy of {@code criteria} is used internally.
         */
        public void setCriteria(Set<Tag> criteria) {
            this.criteria = (criteria != null) ? new HashSet<>(criteria) : null;
        }

        /**
         * Returns an unmodifiable criteria set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code criteria} is null.
         */
        public Optional<Set<Tag>> getCriteria() {
            return (criteria != null) ? Optional.of(Collections.unmodifiableSet(criteria)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPolicyDescriptor)) {
                return false;
            }

            // state check
            EditPolicyDescriptor e = (EditPolicyDescriptor) other;

            return getName().equals(e.getName())
                    && getDescription().equals(e.getDescription())
                    && getCoverage().equals(e.getCoverage())
                    && getPrice().equals(e.getPrice())
                    && getStartAge().equals(e.getStartAge())
                    && getEndAge().equals(e.getEndAge());
        }
    }
}
