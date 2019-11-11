package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classid.ClassId;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.earnings.Type;

/**
 * Adds the list of auto earnings to the current list of earnings.
 */
public class AutoCommand extends Command {

    public static final String COMMAND_WORD = "auto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Commences the auto addition of earnings and must be done "
            + "every time you log in for the auto addition of earnings to be done. \n";

    public static final String MESSAGE_SUCCESS = "New earnings automatically added!";
    public static final String MESSAGE_FAILURE = "No earnings to add!";
    public static final String MESSAGE_DUPLICATE_EARNINGS =
            "This earnings with the same module, date and amount already exists in the address book";

    public AutoCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        addAutoEarnings(model);

        return new CommandResult(MESSAGE_SUCCESS,
                false, false, true, false, false,
                false, false, false);
    }

    /**
     * Adds earnings into the list automatically.
     */
    public static void addAutoEarnings(Model model) throws CommandException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE");
        LocalDateTime now = LocalDateTime.now();
        String currentDay = dtf.format(now);
        List<Earnings> lastShownList = model.getFilteredEarningsList();
        Earnings earnings = lastShownList.get(0);

        if (earnings.getListOfAutoEarnings().containsKey(currentDay)) {
            ArrayList<Earnings> list = earnings.getArrayListOfAutoEarnings(currentDay);

            for (Earnings e : list) {

                if (Integer.parseInt(e.getCount().count) > 0) {
                    CopyEarningsDescriptor copy = new CopyEarningsDescriptor();
                    Earnings newEarnings = createNewCopyEarnings(e, copy);
                    newEarnings.setClaim(e.getClaim());
                    newEarnings.setCount(e.getCount());
                    newEarnings.reduceCount();
                    if (model.hasEarnings(newEarnings)) {
                        throw new CommandException("Unable to auto add weekly earnings. " + MESSAGE_DUPLICATE_EARNINGS);
                    }
                    model.addEarnings(newEarnings);
                    //Earnings.getEarningsList().add(newEarnings);
                } else if (Integer.parseInt(e.getCount().count) == 0) {
                    earnings.removeEarnings(currentDay, e);
                }
            }
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    private static Date getTodayDate() {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = dtf2.format(now);
        Date date = new Date(currentDate);
        return date;
    }

    /**
     * Creates and returns a {@code Earnings} with the details of {@code earningsToEdit}
     * edited with {@code editEarningsDescriptor}.
     */
    private static Earnings createNewCopyEarnings(Earnings earningsToEdit,
                                                 CopyEarningsDescriptor editEarningsDescriptor) {
        assert earningsToEdit != null;

        Date updatedDate = getTodayDate();
        ClassId updatedClassId = editEarningsDescriptor.getClassId().orElse(earningsToEdit.getClassId());
        Amount updatedAmount = editEarningsDescriptor.getAmount().orElse(earningsToEdit.getAmount());
        Type updatedType = editEarningsDescriptor.getType().orElse(earningsToEdit.getType());

        return new Earnings(updatedDate, updatedClassId, updatedAmount, updatedType);
    }

    /**
     * Stores the details to edit the earnings with.
     * Each non-empty field value will replace the
     * corresponding field value of the earnings.
     */
    public static class CopyEarningsDescriptor {
        private Date date;
        private ClassId classId;
        private Amount amount;
        private Type type;

        public CopyEarningsDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public CopyEarningsDescriptor(CopyEarningsDescriptor toCopy) {
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
            if (!(other instanceof CopyEarningsDescriptor)) {
                return false;
            }

            // state check
            CopyEarningsDescriptor e = (CopyEarningsDescriptor) other;

            return getDate().equals(e.getDate())
                    && getClassId().equals(e.getClassId())
                    && getAmount().equals(e.getAmount())
                    && getType().equals(e.getType());
        }
    }
}
