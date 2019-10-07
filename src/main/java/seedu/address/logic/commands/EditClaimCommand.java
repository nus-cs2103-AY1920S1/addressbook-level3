package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLAIMS;

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
import seedu.address.model.claim.Amount;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.Description;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing claim in the claim list.
 */
public class EditClaimCommand extends Command {

    public static final String COMMAND_WORD = "edit_claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the claim "
            + "by the index number used in the displayed claim list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_CASH + "CASH AMOUNT] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Logistics for Sports Day "
            + PREFIX_CASH + "252.50 "
            + PREFIX_NAME + "WEI GEN "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_CLAIM_SUCCESS = "Edited Claim: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CLAIM = "This claim already exists in the claim list.";

    private final Index index;
    private final EditClaimDescriptor editClaimDescriptor;

    /**
     * @param index of the claim in the filtered claim list to edit
     * @param editClaimDescriptor details to edit the claim with
     */
    public EditClaimCommand(Index index, EditClaimDescriptor editClaimDescriptor) {
        requireNonNull(index);
        requireNonNull(editClaimDescriptor);

        this.index = index;
        this.editClaimDescriptor = new EditClaimDescriptor(editClaimDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Claim> lastShownList = model.getFilteredClaimList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLAIM_DISPLAYED_INDEX);
        }

        Claim claimToEdit = lastShownList.get(index.getZeroBased());
        Claim editedClaim = createEditedClaim(claimToEdit, editClaimDescriptor);

        if (!claimToEdit.isSameClaim(editedClaim) && model.hasClaim(editedClaim)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLAIM);
        }

        model.setClaim(claimToEdit, editedClaim);
        model.updateFilteredClaimList(PREDICATE_SHOW_ALL_CLAIMS);
        return new CommandResult(String.format(MESSAGE_EDIT_CLAIM_SUCCESS, editedClaim));
    }

    /**
     * Creates and returns a {@code Claim} with the details of {@code claimToEdit}
     * edited with {@code editClaimDescriptor}.
     */
    private static Claim createEditedClaim(Claim claimToEdit, EditClaimDescriptor editClaimDescriptor) {
        assert claimToEdit != null;

        Description updatedDescription = editClaimDescriptor.getDescription().orElse(claimToEdit.getDescription());
        Amount updatedAmount = editClaimDescriptor.getAmount().orElse(claimToEdit.getAmount());
        Name updatedName = editClaimDescriptor.getName().orElse(claimToEdit.getName());
        Phone updatedPhone = editClaimDescriptor.getPhone().orElse(claimToEdit.getPhone());
        Set<Tag> updatedTags = editClaimDescriptor.getTags().orElse(claimToEdit.getTags());

        return new Claim(updatedDescription, updatedAmount, updatedName, updatedPhone, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditClaimCommand)) {
            return false;
        }

        // state check
        EditClaimCommand e = (EditClaimCommand) other;
        return index.equals(e.index)
                && editClaimDescriptor.equals(e.editClaimDescriptor);
    }

    /**
     * Stores the details to edit the claim with. Each non-empty field value will replace the
     * corresponding field value of the claim.
     */
    public static class EditClaimDescriptor {
        private Description description;
        private Amount amount;
        private Name name;
        private Phone phone;
        private Set<Tag> tags;

        public EditClaimDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditClaimDescriptor(EditClaimDescriptor toCopy) {
            setDescription(toCopy.description);
            setAmount(toCopy.amount);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, amount, name, phone, tags);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
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
            if (!(other instanceof EditClaimDescriptor)) {
                return false;
            }

            // state check
            EditClaimDescriptor e = (EditClaimDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getAmount().equals(e.getAmount())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getTags().equals(e.getTags());
        }
    }
}
