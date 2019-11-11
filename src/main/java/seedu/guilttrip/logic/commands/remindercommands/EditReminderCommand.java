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
import seedu.guilttrip.commons.util.TimeUtil;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.reminders.EntryReminder;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.DateCondition;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;
import seedu.guilttrip.model.reminders.conditions.TagsCondition;
import seedu.guilttrip.model.reminders.conditions.TypeCondition;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.Frequency;

/**
 * Edits the details of an existing reminder in the GuiltTrip.
 */
public class EditReminderCommand extends Command {

    public static final String COMMAND_WORD = "editReminder";


    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Edits the display conditions "
            + "of the reminder selected ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "\nExisting values will be overwritten by the input values.\n"
            + "GeneralReminder Parameters: "
            + PREFIX_LOWER_BOUND + "Lower-bound for entry amount "
            + PREFIX_UPPER_BOUND + "Upper-bound for entry amount "
            + PREFIX_START_DATE + "Start date "
            + PREFIX_END_DATE + "End date \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "Singapore Sale!. \n";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the guilttrip book.";
    public static final String REMINDER_NOT_SELECTED = "Please select a reminder to edit";
    public static final String MISMATCHING_REMINDER_TYPES = "Currently selected reminder does not support"
            + " the modified parameters.\n";
    public static final String REMINDER_TYPE_NOT_SUPPORTED = "This reminder cannot be edited. \n";
    public static final String INVALID_PERIOD = "Invalid period. (Cannot set reminder to notify before today!)";
    public static final String INVALID_FREQ = "Frequency must be smaller than period.";
    private static final Logger logger = LogsCenter.getLogger(EditReminderCommand.class);

    private EditReminderDescriptor editReminderDescriptor;

    /**
     * @param editReminderDescriptor details to edit the entry with
     */
    public EditReminderCommand(EditReminderDescriptor editReminderDescriptor) {
        if (editReminderDescriptor instanceof EditGeneralReminderDescriptor) {
            EditGeneralReminderDescriptor editGeneralReminderDescriptor =
                    (EditGeneralReminderDescriptor) editReminderDescriptor;
            this.editReminderDescriptor = new EditGeneralReminderDescriptor(editGeneralReminderDescriptor);
        } else if (editReminderDescriptor instanceof EditEntryReminderDescriptor) {
            EditEntryReminderDescriptor editEntryReminderDescriptor =
                    (EditEntryReminderDescriptor) editReminderDescriptor;
            this.editReminderDescriptor = new EditEntryReminderDescriptor(editEntryReminderDescriptor);
        } else {
            this.editReminderDescriptor = editReminderDescriptor;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Reminder reminderToEdit = model.getReminderSelected();
        if (reminderToEdit == null) {
            throw new CommandException(REMINDER_NOT_SELECTED);
        }
        Reminder editedReminder;
        if (editReminderDescriptor instanceof EditGeneralReminderDescriptor) {
            logger.info("Editing GeneralReminder");
            editedReminder = createGeneralEditedReminder(model,
                    reminderToEdit, (EditGeneralReminderDescriptor) editReminderDescriptor);
        } else if (editReminderDescriptor instanceof EditEntryReminderDescriptor) {
            logger.info("Editing EntryReminder");
            editedReminder = createEntryEditedReminder(model,
                    reminderToEdit, (EditEntryReminderDescriptor) editReminderDescriptor);
            EntryReminder entryReminder = (EntryReminder) editedReminder;
            Entry targetEntry = entryReminder.getEntry();
            Period period = entryReminder.getPeriod();
            Frequency freq = entryReminder.getFrequency();
            Date currDate = new Date(TimeUtil.getLastRecordedDate());
            if (targetEntry.getDate().minus(period).isBefore(currDate)) {
                throw new CommandException(INVALID_PERIOD);
            }
            if (currDate.plus(period).isBefore(currDate.plus(freq))) {
                throw new CommandException(INVALID_FREQ);
            }
        } else {
            editedReminder = createEditedReminder(model, reminderToEdit, editReminderDescriptor);
        }
        if (!reminderToEdit.equals(editedReminder) && model.hasReminder(editedReminder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }
        model.setReminder(reminderToEdit, editedReminder);
        model.selectReminder(editedReminder);
        model.updateFilteredReminders(model.PREDICATE_SHOW_ALL_REMINDERS);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Reinder} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Reminder createGeneralEditedReminder(Model model,
        Reminder reminderToEdit, EditGeneralReminderDescriptor editGeneralReminderDescriptor)
            throws CommandException {
    assert reminderToEdit != null;
        GeneralReminder generalReminderToEdit = (GeneralReminder) reminderToEdit;
        if (!(model.getReminderSelected() instanceof GeneralReminder)) {
            throw new CommandException(MISMATCHING_REMINDER_TYPES);
        }
        HashMap<String, Condition> oldConditions = new HashMap<>();
        for (Condition condition : generalReminderToEdit.getConditions()) {
            if (condition instanceof TypeCondition) {
                logger.info("Old Type Condition Found");
                oldConditions.put("TypeCondition", condition);
            } else if (condition instanceof QuotaCondition) {
                QuotaCondition quotaCondition = (QuotaCondition) condition;
                if (quotaCondition.isLowerBound()) {
                    logger.info("Old Lower Bound Found" + quotaCondition);
                    oldConditions.put("LowerBound", condition);
                } else {
                    logger.info("Old upper Bound Found" + quotaCondition);
                    oldConditions.put("UpperBound", condition);
                }
            } else if (condition instanceof DateCondition) {
                DateCondition dateCondition = (DateCondition) condition;
                if (dateCondition.isStart()) {
                    logger.info("Old Start Date Found" + dateCondition);
                    oldConditions.put("Start", condition);
                } else {
                    logger.info("Old End Date Found" + dateCondition);
                    oldConditions.put("End", condition);
                }
            } else {
                assert(condition instanceof TagsCondition);
                logger.info("Old Tag Condition Found" + condition);
                oldConditions.put("TagsCondition", condition);
            }
        }
        Description header;
        if (editGeneralReminderDescriptor.getHeader().isPresent()) {
            header = editGeneralReminderDescriptor.getHeader().get();
        } else {
            header = generalReminderToEdit.getHeader();
        }
        List<Condition> newConditions = new ArrayList<>();
        Condition typeCondition = oldConditions.get("TypeCondition");
        newConditions.add(typeCondition);
        if (editGeneralReminderDescriptor.getLowerBound().isPresent()) {
            double lowerBound = editGeneralReminderDescriptor.getLowerBound().get();
            QuotaCondition lowerBoundCondition = new QuotaCondition(lowerBound, true);
            logger.info("newLowerBound set");
            newConditions.add(lowerBoundCondition);
        } else if (oldConditions.containsKey("LowerBound")) {
            newConditions.add(oldConditions.get("LowerBound"));
            logger.info("oldLowerBound set");
        }
        if (editGeneralReminderDescriptor.getUpperBound().isPresent()) {
            double upperBound = editGeneralReminderDescriptor.getUpperBound().get();
            QuotaCondition upperBoundCondition = new QuotaCondition(upperBound, false);
            newConditions.add(upperBoundCondition);
            logger.info("newUpperBound set");
        } else if (oldConditions.containsKey("UpperBound")) {
            newConditions.add(oldConditions.get("UpperBound"));
            logger.info("oldUpperBound set");
        }
        if (editGeneralReminderDescriptor.getStart().isPresent()) {
            Date start = editGeneralReminderDescriptor.getStart().get();
            DateCondition newStartCondition = new DateCondition(start, true);
            logger.info("newStart set");
            newConditions.add(newStartCondition);
        } else if (oldConditions.containsKey("Start")) {
            logger.info("old start set");
            newConditions.add(oldConditions.get("Start"));
        }
        if (editGeneralReminderDescriptor.getEnd().isPresent()) {
            Date end = editGeneralReminderDescriptor.getEnd().get();
            DateCondition newEndCondition = new DateCondition(end, false);
            logger.info("newEnd set");
            newConditions.add(newEndCondition);
        } else if (oldConditions.containsKey("End")) {
            logger.info("old end set");
            newConditions.add(oldConditions.get("End"));
        }
        if (editGeneralReminderDescriptor.getTags().isPresent()) {
            logger.info("newtags set");
            Set<Tag> tags = editGeneralReminderDescriptor.getTags().get();
            TagsCondition newTagsCondition = new TagsCondition(new ArrayList<>(tags));
            newConditions.add(newTagsCondition);
        } else if (oldConditions.containsKey("TagsCondition")) {
            logger.info("old tags set");
            newConditions.add(oldConditions.get("TagsCondition"));
        }
        Reminder editedReminder = new GeneralReminder(header, newConditions);
        editedReminder.setMessage(generalReminderToEdit.getMessage());
        editedReminder.togglePopUpDisplay(generalReminderToEdit.willDisplayPopUp());
        for (Condition condition: newConditions) {
            model.addCondition(condition);
        }
        return editedReminder;
    }

    /**
     * Creates and returns a {@code Reinder} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Reminder createEntryEditedReminder(
            Model model, Reminder reminderToEdit, EditEntryReminderDescriptor editEntryReminderDescriptor)
        throws CommandException {
        assert reminderToEdit != null;
        if (!(model.getReminderSelected() instanceof EntryReminder)) {
            throw new CommandException(MISMATCHING_REMINDER_TYPES);
        }
        EntryReminder entryReminderToEdit = (EntryReminder) reminderToEdit;
        final Period period;
        final Frequency freq;
        if (editEntryReminderDescriptor.getPeriod().isPresent()) {
            period = editEntryReminderDescriptor.getPeriod().get();
        } else {
            period = entryReminderToEdit.getPeriod();
        }
        if (editEntryReminderDescriptor.getFrequency().isPresent()) {
            freq = editEntryReminderDescriptor.getFrequency().get();
        } else {
            freq = entryReminderToEdit.getFrequency();
        }
        Description header;
        if (editEntryReminderDescriptor.getHeader().isPresent()) {
            header = editEntryReminderDescriptor.getHeader().get();
        } else {
            header = entryReminderToEdit.getHeader();
        }
        Entry targetEntry = entryReminderToEdit.getEntry();
        EntryReminder newReminder = new EntryReminder(header, targetEntry, period, freq);
        return newReminder;
    }

    /**
     * Called when only the header is modified, and knowing the type of reminder is not needed.
     */
    private static Reminder createEditedReminder(
            Model model, Reminder reminderToEdit, EditReminderDescriptor editReminderDescriptor)
            throws CommandException {
        Reminder editedReminder;
        if (reminderToEdit instanceof GeneralReminder) {
            editedReminder = createGeneralEditedReminder(model,
                    (GeneralReminder) reminderToEdit, new EditGeneralReminderDescriptor(editReminderDescriptor));
        } else if (reminderToEdit instanceof EntryReminder) {
            editedReminder = createEntryEditedReminder(model,
                    (EntryReminder) reminderToEdit, new EditEntryReminderDescriptor(editReminderDescriptor));
        } else {
            throw new CommandException(REMINDER_TYPE_NOT_SUPPORTED);
        }
        return editedReminder;
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

    /**
     * all reminder descriptors for all reminder types extend this class.
     */
    public static class EditReminderDescriptor {
        protected Description header;

        public Optional<Description> getHeader() {
            return Optional.ofNullable(header);
        }
        public void setHeader(Description header) {
            this.header = header;
        }
    }
    /**
     * Stores the details to edit the entry with. Each non-empty field value will replace the
     * corresponding field value of the entry.
     */
    public static class EditGeneralReminderDescriptor extends EditReminderDescriptor {
        private Double lowerBound;
        private Double upperBound;
        private Date start;
        private Date end;
        private Set<Tag> tags;
        private final Logger logger = LogsCenter.getLogger(getClass());

        public EditGeneralReminderDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGeneralReminderDescriptor(EditGeneralReminderDescriptor toCopy) {
            setHeader(toCopy.header);
            setLowerBound(toCopy.lowerBound);
            setUpperBound(toCopy.upperBound);
            setStart(toCopy.start);
            setEnd(toCopy.end);
            setTags(toCopy.tags);
        }

        /**
         * Only header is modified.
         * @param toCopy
         */
        public EditGeneralReminderDescriptor(EditReminderDescriptor toCopy) {
            logger.info(toCopy.header.fullDesc);
            setHeader(toCopy.header);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(header, lowerBound, upperBound,
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
            if (!(other instanceof EditGeneralReminderDescriptor)) {
                return false;
            }

            // state check
            EditGeneralReminderDescriptor e = (EditGeneralReminderDescriptor) other;

            return getHeader().equals(e.getHeader())
                    && getLowerBound().equals(e.getLowerBound())
                    && getUpperBound().equals(e.getUpperBound())
                    && getStart().equals(e.getStart())
                    && getEnd().equals(e.getEnd())
                    && getTags().equals(e.getTags());
        }
    }

    /**
     * Contains edited information for EntryReminder
     */
    public static class EditEntryReminderDescriptor extends EditReminderDescriptor {
        private Period period;
        private Frequency frequency;

        public EditEntryReminderDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEntryReminderDescriptor(EditEntryReminderDescriptor toCopy) {
            setHeader(toCopy.header);
            setPeriod(toCopy.period);
            setFrequency(toCopy.frequency);
        }

        /**
         * Only header is modified.
         * @param toCopy
         */
        public EditEntryReminderDescriptor(EditReminderDescriptor toCopy) {
            setHeader(toCopy.header);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(header, period, frequency);
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

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            } else if (!(other instanceof EditEntryReminderDescriptor)) {
                return false;
            } else {
                EditEntryReminderDescriptor e = (EditEntryReminderDescriptor) other;
                return getHeader().equals(e.getHeader())
                        && getPeriod().equals(e.getPeriod())
                        && getFrequency().equals(e.getFrequency());
            }
        }
    }
}
