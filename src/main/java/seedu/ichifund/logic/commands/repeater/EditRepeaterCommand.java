package seedu.ichifund.logic.commands.repeater;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.isAnyNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_END_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_START_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.model.Model.PREDICATE_SHOW_ALL_REPEATERS;

import java.util.List;
import java.util.Optional;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.repeater.MonthOffset;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Edits a repeater in IchiFund.
 */
public class EditRepeaterCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a repeater to IchiFund.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_TRANSACTION_TYPE + "TRANSACTION_TYPE "
            + PREFIX_MONTH_START_OFFSET + "MONTH_START_OFFSET "
            + PREFIX_MONTH_END_OFFSET + "MONTH_END_OFFSET "
            + PREFIX_START_MONTH + "START_MONTH "
            + PREFIX_START_YEAR + "START_YEAR "
            + PREFIX_END_MONTH + "END_MONTH "
            + PREFIX_END_YEAR + "END_YEAR "
            + "\nConstraints:\n"
            + "- Repeater end must not occur before repeater start.\n"
            + "- Repeater start and end can span at most 60 months (5 years).\n"
            + "- At least one of month start offset or month end offset must not be ignored.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Potato money "
            + PREFIX_AMOUNT + "31.34 "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_TRANSACTION_TYPE + "in "
            + PREFIX_MONTH_START_OFFSET + "1 "
            + PREFIX_MONTH_END_OFFSET + "2 "
            + PREFIX_START_MONTH + "1 "
            + PREFIX_START_YEAR + "2019 "
            + PREFIX_END_MONTH + "12 "
            + PREFIX_END_YEAR + "2021";

    public static final String MESSAGE_EDIT_REPEATER_SUCCESS = "Edited repeater: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be modified.";
    public static final String MESSAGE_DUPLICATE_REPEATER = "This repeater already exists in IchiFund";

    private final Index index;
    private final EditRepeaterDescriptor editRepeaterDescriptor;

    /**
     * Creates an EditRepeaterCommand to edit the specified {@code Repeater}
     */
    public EditRepeaterCommand(Index index, EditRepeaterDescriptor editRepeaterDescriptor) {
        requireNonNull(index);
        requireNonNull(editRepeaterDescriptor);

        this.index = index;
        this.editRepeaterDescriptor = editRepeaterDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Repeater> lastShownList = model.getFilteredRepeaterList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REPEATER_DISPLAYED_INDEX);
        }

        Repeater repeaterToEdit = lastShownList.get(index.getZeroBased());

        RepeaterUniqueId updatedUniqueId = repeaterToEdit.getUniqueId();
        Description updatedDescription = editRepeaterDescriptor.getDescription()
                .orElse(repeaterToEdit.getDescription());
        Amount updatedAmount = editRepeaterDescriptor.getAmount()
                .orElse(repeaterToEdit.getAmount());
        Category updatedCategory = editRepeaterDescriptor.getCategory()
                .orElse(repeaterToEdit.getCategory());
        TransactionType updatedTransactionType = editRepeaterDescriptor.getTransactionType()
                .orElse(repeaterToEdit.getTransactionType());
        MonthOffset updatedMonthStartOffset = editRepeaterDescriptor.getMonthStartOffset()
                .orElse(repeaterToEdit.getMonthStartOffset());
        MonthOffset updatedMonthEndOffset = editRepeaterDescriptor.getMonthEndOffset()
                .orElse(repeaterToEdit.getMonthEndOffset());
        Date updatedStartDate = editRepeaterDescriptor.getStartDate()
                .orElse(repeaterToEdit.getStartDate());
        Date updatedEndDate = editRepeaterDescriptor.getEndDate()
                .orElse(repeaterToEdit.getEndDate());

        // Check repeater span.
        if (updatedEndDate.compareTo(updatedStartDate) > 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_REPEATER_SPAN);
        }
        if (countMonths(updatedStartDate, updatedEndDate) > 60) {
            throw new CommandException(Messages.MESSAGE_INVALID_REPEATER_SPAN);
        }

        // Check offsets.
        if (updatedMonthStartOffset.isIgnored() && updatedMonthEndOffset.isIgnored()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REPEATER_MONTH_OFFSETS);
        }

        Repeater editedRepeater = new Repeater(updatedUniqueId, updatedDescription, updatedAmount,
                updatedCategory, updatedTransactionType,
                updatedMonthStartOffset, updatedMonthEndOffset, updatedStartDate, updatedEndDate);

        if (editedRepeater.equals(repeaterToEdit)) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        if (model.hasRepeater(editedRepeater)) {
            throw new CommandException(MESSAGE_DUPLICATE_REPEATER);
        }

        model.setRepeater(repeaterToEdit, editedRepeater);
        model.deleteRepeaterTransactions(editedRepeater.getUniqueId());
        model.createRepeaterTransactions(editedRepeater);

        return new CommandResult(String.format(MESSAGE_EDIT_REPEATER_SUCCESS, editedRepeater));
    }

    /**
     * Counts the number of months spanned by two dates.
     */
    private static int countMonths(Date startDate, Date endDate) {
        if (endDate.compareTo(startDate) > 0) {
            return 0;
        }

        if (endDate.compareTo(startDate) == 0) {
            return 1;
        }

        int startMonth = startDate.getMonth().monthNumber;
        int startYear = startDate.getYear().yearNumber;
        int currentMonth = startDate.getMonth().monthNumber;
        int currentYear = startDate.getYear().yearNumber;
        int endMonth = endDate.getMonth().monthNumber;
        int endYear = endDate.getYear().yearNumber;

        int months = 0;
        while ((currentYear < endYear) || (currentYear == endYear && currentMonth <= endMonth)) {
            months++;
            currentMonth++;
            if (currentMonth == 13) {
                currentMonth = 1;
                currentYear++;
            }
        }

        return months;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditRepeaterCommand // instanceof handles nulls
                && editRepeaterDescriptor.equals(((EditRepeaterCommand) other).editRepeaterDescriptor));
    }

    /**
     * Stores the details to edit the repeater with. Each non-empty field value will replace the
     * corresponding field value of the repeater.
     */
    public static class EditRepeaterDescriptor {
        private Description description;
        private Amount amount;
        private Category category;
        private TransactionType transactionType;
        private MonthOffset monthStartOffset;
        private MonthOffset monthEndOffset;
        private Date startDate;
        private Date endDate;

        public EditRepeaterDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditRepeaterDescriptor(EditRepeaterDescriptor toCopy) {
            setDescription(toCopy.description);
            setAmount(toCopy.amount);
            setCategory(toCopy.category);
            setTransactionType(toCopy.transactionType);
            setMonthStartOffset(toCopy.monthStartOffset);
            setMonthEndOffset(toCopy.monthEndOffset);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return isAnyNonNull(description, amount, category, transactionType,
                    monthStartOffset, monthEndOffset, startDate, endDate);
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

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setTransactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
        }

        public Optional<TransactionType> getTransactionType() {
            return Optional.ofNullable(transactionType);
        }

        public void setMonthStartOffset(MonthOffset monthStartOffset) {
            this.monthStartOffset = monthStartOffset;
        }

        public Optional<MonthOffset> getMonthStartOffset() {
            return Optional.ofNullable(monthStartOffset);
        }

        public void setMonthEndOffset(MonthOffset monthEndOffset) {
            this.monthEndOffset = monthEndOffset;
        }

        public Optional<MonthOffset> getMonthEndOffset() {
            return Optional.ofNullable(monthEndOffset);
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Optional<Date> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Optional<Date> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRepeaterDescriptor)) {
                return false;
            }

            // state check
            EditRepeaterDescriptor e = (EditRepeaterDescriptor) other;

            return getDescription().equals(e.getDescription())
                && getAmount().equals(e.getAmount())
                && getCategory().equals(e.getCategory())
                && getTransactionType().equals(e.getTransactionType())
                && getMonthStartOffset().equals(e.getMonthStartOffset())
                && getMonthEndOffset().equals(e.getMonthEndOffset())
                && getStartDate().equals(e.getStartDate())
                && getEndDate().equals(e.getEndDate());
        }
    }
}
