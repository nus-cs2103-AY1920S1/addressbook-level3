package seedu.guilttrip.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_LOWER_BOUND;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_UPPER_BOUND;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.util.CollectionUtil;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.IEWReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.DateCondition;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;
import seedu.guilttrip.model.reminders.conditions.TagsCondition;
import seedu.guilttrip.model.reminders.conditions.TypeCondition;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.Frequency;

/**
 * Edits the details of an existing entry in the guilttrip book.
 */
public class EditReminderCommand extends Command {

    public static final String COMMAND_WORD = "editReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of selected Reminder "
            + "Existing values will be overwritten by the input values.\n"
            + "GeneralReminder Parameters: "
            + PREFIX_LOWER_BOUND + "LOWERBOUND FOR AMT "
            + PREFIX_UPPER_BOUND + "UPPERBOUND FOR AMT "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_END_DATE + "END DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "Singapore Sale!. \n";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the guilttrip book.";
    public static final String CONDITION_NOT_REMOVABLE = "Reminder must have at least one condition \n";
    public static final String MISMATCHING_REMINDER_TYPES = "Currently selected reminder does not support" +
            "the modified parameters.\n";
    public static final String REMINDER_TYPE_NOT_SUPPORTED = "This reminder cannot be edited. \n";


    private EditReminderDescriptor editReminderDescriptor;
    private final Logger logger = LogsCenter.getLogger(getClass());


    /**
     * @param editReminderDescriptor details to edit the entry with
     */
    public EditReminderCommand(EditReminderDescriptor editReminderDescriptor) {
        if (editReminderDescriptor instanceof EditGeneralReminderDescriptor) {
            EditGeneralReminderDescriptor editGeneralReminderDescriptor =
                    (EditGeneralReminderDescriptor) editReminderDescriptor;
            this.editReminderDescriptor = new EditGeneralReminderDescriptor(editGeneralReminderDescriptor);
        } else if (editReminderDescriptor instanceof EditIEWReminderDescriptor) {
            EditIEWReminderDescriptor editIEWReminderDescriptor = (EditIEWReminderDescriptor) editReminderDescriptor;
            this.editReminderDescriptor = new EditIEWReminderDescriptor(editIEWReminderDescriptor);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Reminder reminderToEdit = model.getReminderSelected();
        Reminder editedReminder;
        if (editReminderDescriptor instanceof EditGeneralReminderDescriptor) {
            logger.info("Editing GeneralReminder");
            editedReminder = createGeneralEditedReminder(model,
                    (GeneralReminder) reminderToEdit, (EditGeneralReminderDescriptor) editReminderDescriptor);
        } else if (editReminderDescriptor instanceof EditIEWReminderDescriptor) {
            logger.info("Editing IEWReminder");
            editedReminder = createIEWEditedReminder(model,
                    (IEWReminder) reminderToEdit, (EditIEWReminderDescriptor) editReminderDescriptor);
            if (!reminderToEdit.equals(editedReminder) && model.hasReminder(editedReminder)) {
                throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
            }
        } else {
            throw new CommandException(REMINDER_TYPE_NOT_SUPPORTED);
        }
        if (!reminderToEdit.equals(editedReminder) && model.hasReminder(editedReminder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }
        model.setReminder(reminderToEdit, editedReminder);
        model.updateFilteredReminders(model.PREDICATE_SHOW_ALL_REMINDERS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Reinder} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Reminder createGeneralEditedReminder(Model model,
        GeneralReminder reminderToEdit, EditGeneralReminderDescriptor editGeneralReminderDescriptor) throws CommandException {
    assert reminderToEdit != null;
        if (! (model.getReminderSelected() instanceof GeneralReminder)) {
            throw new CommandException(MISMATCHING_REMINDER_TYPES);
        }
        HashMap<String, Condition> oldConditions = new HashMap<>();
        for (Condition condition : reminderToEdit.getConditions()) {
            if (condition instanceof TypeCondition) {
                oldConditions.put("TypeCondition", condition);
            } else if (condition instanceof QuotaCondition) {
                QuotaCondition quotaCondition = (QuotaCondition) condition;
                if (quotaCondition.isLowerBound()) {
                    oldConditions.put("LowerBound", condition);
                } else {
                    oldConditions.put("UpperBound", condition);
                }
            } else if (condition instanceof DateCondition) {
                DateCondition dateCondition = (DateCondition) condition;
                if (dateCondition.isStart()) {
                    oldConditions.put("Start", condition);
                } else {
                    oldConditions.put("End", condition);
                }
            } else {
                assert(condition instanceof TagsCondition);
                oldConditions.put("TagsCondition", condition);
            }
        }
        List<Condition> conditionsToEdit = new ArrayList<>();
        Condition typeCondition = oldConditions.get("TypeCondition");
        conditionsToEdit.add(typeCondition);
        if ( editGeneralReminderDescriptor.getLowerBound().isPresent()) {
            double lowerBound = editGeneralReminderDescriptor.getLowerBound().get();
            QuotaCondition lowerBoundCondition = new QuotaCondition(lowerBound, true);
            conditionsToEdit.add(lowerBoundCondition);
        } else if (oldConditions.containsKey("LowerBound")){
            conditionsToEdit.add(oldConditions.get("LowerBound"));
        }
        if ( editGeneralReminderDescriptor.getUpperBound().isPresent()) {
            double upperBound = editGeneralReminderDescriptor.getUpperBound().get();
            QuotaCondition upperBoundCondition = new QuotaCondition(upperBound, false);
            conditionsToEdit.add(upperBoundCondition);
        } else if (oldConditions.containsKey("UpperBound")){
            conditionsToEdit.add(oldConditions.get("UpperBound"));
        }
        if ( editGeneralReminderDescriptor.getStart().isPresent()) {
            Date start = editGeneralReminderDescriptor.getStart().get();
            DateCondition newStartCondition = new DateCondition(start, true);
            conditionsToEdit.add(newStartCondition);
        } else if (oldConditions.containsKey("Start")){
            conditionsToEdit.add(oldConditions.get("Start"));
        }
        if ( editGeneralReminderDescriptor.getEnd().isPresent()) {
            Date end = editGeneralReminderDescriptor.getEnd().get();
            DateCondition newEndCondition = new DateCondition(end, false);
            conditionsToEdit.add(newEndCondition);
        } else if (oldConditions.containsKey("End")){
            conditionsToEdit.add(oldConditions.get("End"));
        }
        if ( editGeneralReminderDescriptor.getTags().isPresent()) {
            Set<Tag> tags = editGeneralReminderDescriptor.getTags().get();
            TagsCondition newTagsCondition = new TagsCondition(new ArrayList<>(tags));
            conditionsToEdit.add(newTagsCondition);
        } else if (oldConditions.containsKey("TagsCondition")){
            conditionsToEdit.add(oldConditions.get("TagsCondition"));
        }
        Description header = reminderToEdit.getHeader();
        Reminder editedReminder = new GeneralReminder(header, conditionsToEdit);
        editedReminder.setMessage(reminderToEdit.getMessage());
        editedReminder.togglePopUpDisplay(reminderToEdit.willDisplayPopUp());
        for (Condition condition: oldConditions.values()) {
            model.deleteCondition(condition);
        }
        for (Condition condition: conditionsToEdit) {
            model.addCondition(condition);
        }
        return editedReminder;
    }

    /**
     * Creates and returns a {@code Reinder} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Reminder createIEWEditedReminder(
            Model model, IEWReminder reminderToEdit, EditIEWReminderDescriptor editIEWReminderDescriptor)
        throws CommandException {
        assert reminderToEdit != null;
        if (! (model.getReminderSelected() instanceof IEWReminder)) {
            throw new CommandException(MISMATCHING_REMINDER_TYPES);
        }
        final Period period;
        final Frequency freq;
        if (editIEWReminderDescriptor.getPeriod().isPresent()) {
            period = editIEWReminderDescriptor.getPeriod().get();
        } else {
            period = reminderToEdit.getPeriod();
        }
        if (editIEWReminderDescriptor.getFrequency().isPresent()) {
            freq = editIEWReminderDescriptor.getFrequency().get();
        } else {
            freq = reminderToEdit.getFrequency();
        }
        Description header = reminderToEdit.getHeader();
        Entry targetEntry = reminderToEdit.getEntry();
        IEWReminder newReminder = new IEWReminder(header, targetEntry, period, freq);
        return newReminder;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditReminderCommand)) {
            return false;
        }

        // state check
        EditReminderCommand e = (EditReminderCommand) other;
        return this.editReminderDescriptor.equals(e.editReminderDescriptor);
    }


    public static interface EditReminderDescriptor {
    }
    /**
     * Stores the details to edit the entry with. Each non-empty field value will replace the
     * corresponding field value of the entry.
     */
    public static class EditGeneralReminderDescriptor implements EditReminderDescriptor{
        private Double lowerBound;
        private Double upperBound;
        private Date start;
        private Date end;
        private Set<Tag> tags;

        public EditGeneralReminderDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGeneralReminderDescriptor(EditGeneralReminderDescriptor toCopy) {
            setLowerBound(toCopy.lowerBound);
            setUpperBound(toCopy.upperBound);
            setStart(toCopy.start);
            setEnd(toCopy.end);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(lowerBound, upperBound,
            start, end, tags);
        }

        public void setLowerBound(Double amt) {
            this.lowerBound = amt;
        }

        public Optional<Double> getLowerBound() {
            return Optional.ofNullable(lowerBound);
        }

        public void setUpperBound(Double amt) {
            this.upperBound = amt;
        }

        public Optional<Double> getUpperBound() {
            return Optional.ofNullable(upperBound);
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Optional<Date> getStart() {
            return Optional.ofNullable(start);
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public Optional<Date> getEnd() {
            return Optional.ofNullable(end);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditGeneralReminderDescriptor e = (EditGeneralReminderDescriptor) other;

            return getLowerBound().equals(e.getLowerBound())
                    && getUpperBound().equals(e.getUpperBound())
                    && getStart().equals(e.getStart())
                    && getEnd().equals(e.getEnd())
                    && getTags().equals(e.getTags());
        }
    }

    public static class EditIEWReminderDescriptor implements EditReminderDescriptor{
        Period period;
        Frequency frequency;

        public EditIEWReminderDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditIEWReminderDescriptor(EditIEWReminderDescriptor toCopy) {
            setPeriod(toCopy.period);
            setFrequency(toCopy.frequency);
        }

        public Optional<Period> getPeriod() {
            return Optional.ofNullable(period);
        }

        public void setPeriod(final Period period) {
            this.period = period;
        }

        public Optional<Frequency> getFrequency() {
            return Optional.ofNullable(frequency);
        }

        public void setFrequency(final Frequency frequency) {
            this.frequency = frequency;
        }
    }
}
