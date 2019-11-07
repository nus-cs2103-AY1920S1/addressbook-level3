package seedu.guilttrip.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_LOWER_BOUND;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_UPPER_BOUND;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_GENERAL_REMINDERS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.commons.util.CollectionUtil;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.DateCondition;
import seedu.guilttrip.model.reminders.conditions.KeyWordsCondition;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;
import seedu.guilttrip.model.reminders.conditions.TagsCondition;
import seedu.guilttrip.model.reminders.conditions.TypeCondition;
import seedu.guilttrip.model.tag.Tag;

/**
 * Edits the details of an existing entry in the guilttrip book.
 */
public class EditReminderCommand extends Command {

    public static final String COMMAND_WORD = "editReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of selected Reminder "
            + "Existing values will be overwritten by the input values.\n"
            + "GeneralReminder Parameters: "
            + PREFIX_TYPE + "INCOME OR EXPENSE "
            + PREFIX_DESC + "REMINDER HEADER "
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


    private EditReminderDescriptor editReminderDescriptor;

    /**
     * @param editReminderDescriptor details to edit the entry with
     */
    public EditReminderCommand(EditReminderDescriptor editReminderDescriptor) {
        if (editReminderDescriptor instanceof EditGeneralReminderDescriptor) {
            EditGeneralReminderDescriptor editGeneralReminderDescriptor =
                    (EditGeneralReminderDescriptor) editReminderDescriptor;
            this.editReminderDescriptor = new EditGeneralReminderDescriptor(editGeneralReminderDescriptor);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Reminder reminderToEdit = model.getReminderSelected();
        if (editReminderDescriptor instanceof EditGeneralReminderDescriptor) {
            Reminder editedReminder = createGeneralEditedReminder(model, reminderToEdit, editReminderDescriptor);
            if (!reminderToEdit.equals(editedReminder) && model.hasReminder(editedReminder)) {
                throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
            }
            model.setReminder(reminderToEdit, editedReminder);
            model.updateFilteredReminders(model.PREDICATE_SHOW_ALL_REMINDERS);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedReminder));
        }
        else {
            throw new CommandException("");
        }
    }

    /**
     * Creates and returns a {@code Reinder} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Reminder createGeneralEditedReminder(Model model,
        Reminder reminderToEdit, EditReminderDescriptor editReminderDescriptor) throws CommandException {
    assert reminderToEdit != null;
        if (! (model.getReminderSelected() instanceof GeneralReminder)) {
            throw new CommandException(MISMATCHING_REMINDER_TYPES);
        }
        EditGeneralReminderDescriptor editGeneralReminderDescriptor
                = (EditGeneralReminderDescriptor) editReminderDescriptor;
        GeneralReminder generalReminderToEdit = (GeneralReminder) reminderToEdit;
        HashMap<String, Condition> oldConditions = new HashMap<>();
        for (Condition condition : generalReminderToEdit.getConditions()) {
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
        Description header = ((GeneralReminder)reminderToEdit).getHeader();
        Reminder editedReminder = new GeneralReminder(header, conditionsToEdit);
        editedReminder.setMessage(reminderToEdit.getMessage());
        editedReminder.togglePopUpDisplay(reminderToEdit.willDisplayPopUp());
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


    public static interface EditReminderDescriptor {
    }
    /**
     * Stores the details to edit the entry with. Each non-empty field value will replace the
     * corresponding field value of the entry.
     */
    public static class EditGeneralReminderDescriptor implements EditReminderDescriptor{
        private double lowerBound;
        private double upperBound;
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
}
