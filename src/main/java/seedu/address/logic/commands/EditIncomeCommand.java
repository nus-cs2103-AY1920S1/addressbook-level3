package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INCOMES;

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
import seedu.address.model.claim.Description;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.income.Income;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing claim in the claim list.
 */
public class EditIncomeCommand extends Command {

    public static final String COMMAND_WORD = "edit_income";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the income "
            + "by the index number used in the displayed claim list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_CASH + "CASH AMOUNT] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Camp sign up fees "
            + PREFIX_CASH + "1150.50 "
            + PREFIX_NAME + "JOSHUA SEET "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_INCOME_SUCCESS = "Edited Income: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_INCOME = "This income already exists in the income list.";

    private final Index index;
    private final EditIncomeDescriptor editIncomeDescriptor;

    /**
     * @param index of the claim in the filtered claim list to edit
     * @param editIncomeDescriptor details to edit the claim with
     */
    public EditIncomeCommand(Index index, EditIncomeDescriptor editIncomeDescriptor) {
        requireNonNull(index);
        requireNonNull(editIncomeDescriptor);

        this.index = index;
        this.editIncomeDescriptor = new EditIncomeDescriptor(editIncomeDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Income> lastShownList = model.getFilteredIncomeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLAIM_DISPLAYED_INDEX);
        }

        Income incomeToEdit = lastShownList.get(index.getZeroBased());
        Income editedIncome = createEditedIncome(incomeToEdit, editIncomeDescriptor);

        if (!incomeToEdit.isSameIncome(editedIncome) && model.hasIncome(editedIncome)) {
            throw new CommandException(MESSAGE_DUPLICATE_INCOME);
        }

        model.setIncome(incomeToEdit, editedIncome);
        model.updateFilteredIncomeList(PREDICATE_SHOW_ALL_INCOMES);
        return new CommandResult(String.format(MESSAGE_EDIT_INCOME_SUCCESS, editedIncome));
    }

    /**
     * Creates and returns a {@code Claim} with the details of {@code claimToEdit}
     * edited with {@code editClaimDescriptor}.
     */
    private static Income createEditedIncome(Income incomeToEdit, EditIncomeDescriptor editIncomeDescriptor) {
        assert incomeToEdit != null;

        Description updatedDescription = editIncomeDescriptor.getDescription().orElse(incomeToEdit.getDescription());
        Amount updatedAmount = editIncomeDescriptor.getAmount().orElse(incomeToEdit.getAmount());
        Name updatedName = editIncomeDescriptor.getName().orElse(incomeToEdit.getName());
        Phone updatedPhone = editIncomeDescriptor.getPhone().orElse(incomeToEdit.getPhone());
        Set<Tag> updatedTags = editIncomeDescriptor.getTags().orElse(incomeToEdit.getTags());

        return new Income(updatedDescription, updatedAmount, updatedName, updatedPhone, updatedTags);
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
        EditIncomeCommand editedCommand = (EditIncomeCommand) other;
        return index.equals(editedCommand.index)
                && editIncomeDescriptor.equals(editedCommand.editIncomeDescriptor);
    }

    /**
     * Stores the details to edit the income witb. Each non-empty field value will replace the
     * corresponding field value of the income.
     */
    public static class EditIncomeDescriptor {
        private Description description;
        private Amount amount;
        private Name name;
        private Phone phone;
        private Set<Tag> tags;

        public EditIncomeDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditIncomeDescriptor(EditIncomeDescriptor toCopy) {
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
            if (!(other instanceof EditIncomeDescriptor)) {
                return false;
            }

            // state check
            EditIncomeDescriptor e = (EditIncomeDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getAmount().equals(e.getAmount())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getTags().equals(e.getTags());
        }
    }
}
