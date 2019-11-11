package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EARNINGS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classid.ClassId;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Claim;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.earnings.Type;

/**
 * Edits earnings to the address book.
 */
public class UpdateEarningsCommand extends Command {

    public static final String COMMAND_WORD = "update_earnings";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits/Updates the earnings of the user "
            + "by the index number used in the displayed earnings list. \n"
            + "Existing values will be overwritten by the input values.\n"

            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "NEW_DATE "
            + PREFIX_TYPE + "NEW_TYPE "
            + PREFIX_CLASSID + "NEW_CLASSID "
            + PREFIX_AMOUNT + "NEW_AMOUNT(in dollars) \n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "03/05/2020 "
            + "or " + COMMAND_WORD + " "
            + PREFIX_CLASSID + "CS2103T";

    public static final String MESSAGE_UPDATE_SUCCESS = "Earnings updated: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EARNINGS = "This earnings already exists in the address book.";

    private final Index index;
    private final EditEarningsDescriptor editEarningsDescriptor;

    /**
     * @param index of the person in the filtered earnings list to edit
     * @param editEarningsDescriptor details to edit the earnings with
     */
    public UpdateEarningsCommand(Index index, EditEarningsDescriptor editEarningsDescriptor) {
        requireNonNull(index);
        requireNonNull(editEarningsDescriptor);

        this.index = index;
        this.editEarningsDescriptor = new EditEarningsDescriptor(editEarningsDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Earnings> lastShownList = model.getFilteredEarningsList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
        }

        Earnings earningsToEdit = lastShownList.get(index.getZeroBased());
        Claim claim = earningsToEdit.getClaim();
        Earnings editedEarnings = createEditedEarnings(earningsToEdit, editEarningsDescriptor);
        editedEarnings.setClaim(claim);

        if (!earningsToEdit.isSameEarnings(editedEarnings) && model.hasEarnings(editedEarnings)) {
            throw new CommandException(MESSAGE_DUPLICATE_EARNINGS);
        }

        model.setEarnings(earningsToEdit, editedEarnings);
        model.commitTutorAid();
        model.updateFilteredEarningsList(PREDICATE_SHOW_ALL_EARNINGS);
        return new CommandResult(String.format(MESSAGE_UPDATE_SUCCESS, editedEarnings),
                false, false, true, false, false,
                false, false, false);
    }

    /**
     * Creates and returns a {@code Earnings} with the details of {@code earningsToEdit}
     * edited with {@code editEarningsDescriptor}.
     */
    private static Earnings createEditedEarnings(Earnings earningsToEdit,
                                                 EditEarningsDescriptor editEarningsDescriptor) {
        assert earningsToEdit != null;

        Date updatedDate = editEarningsDescriptor.getDate().orElse(earningsToEdit.getDate());
        ClassId updatedClassId = editEarningsDescriptor.getClassId().orElse(earningsToEdit.getClassId());
        Amount updatedAmount = editEarningsDescriptor.getAmount().orElse(earningsToEdit.getAmount());
        Type updatedType = editEarningsDescriptor.getType().orElse(earningsToEdit.getType());

        return new Earnings(updatedDate, updatedClassId, updatedAmount, updatedType);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateEarningsCommand)) {
            return false;
        }

        // state check
        UpdateEarningsCommand e = (UpdateEarningsCommand) other;
        return index.equals(e.index)
                && editEarningsDescriptor.equals(e.editEarningsDescriptor);
    }

    /**
     * Stores the details to edit the earnings with.
     * Each non-empty field value will replace the
     * corresponding field value of the earnings.
     */
    public static class EditEarningsDescriptor {
        private Date date;
        private ClassId classId;
        private Amount amount;
        private Type type;

        public EditEarningsDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEarningsDescriptor(EditEarningsDescriptor toCopy) {
            setDate(toCopy.date);
            setClassId(toCopy.classId);
            setAmount(toCopy.amount);
            setType(toCopy.type);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, classId, amount, type);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setClassId(ClassId classId) {
            this.classId = classId;
        }

        public Optional<ClassId> getClassId() {
            return Optional.ofNullable(classId);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Optional<Type> getType() {
            return Optional.ofNullable(type);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEarningsDescriptor)) {
                return false;
            }

            // state check
            EditEarningsDescriptor e = (EditEarningsDescriptor) other;

            return getDate().equals(e.getDate())
                    && getClassId().equals(e.getClassId())
                    && getAmount().equals(e.getAmount())
                    && getType().equals(e.getType());
        }
    }
}
