package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.day.ActivityWithTime;

import seedu.address.model.field.Name;

import java.util.List;
import java.util.Optional;

/**
 * Unschedules an activity from the day by time.
 */
public class UnscheduleTimeCommand extends UnscheduleCommand {

    public static final String SECOND_COMMAND_WORD = "time";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
            + ": Unschedules an activity by a time occupied by the activity on a certain day. "
            + "Parameters: "
            + PREFIX_DAY + "DAY "
            + PREFIX_START_TIME + "TIME";

    public static final String MESSAGE_UNSCHEDULE_TIME_SUCCESS = "Activity unscheduled: %1$s";

    private final Index dayIndex;
    private final EditDayDescriptor editDayDescriptor;

    /**
     * @param dayIndex of the contacts in the filtered contacts list to edit
     * @param editDayDescriptor details to edit the contacts with
     */
    public UnscheduleTimeCommand(Index dayIndex, EditDayDescriptor editDayDescriptor) {
        requireNonNull(dayIndex);
        requireNonNull(editDayDescriptor);
        this.dayIndex = dayIndex;
        this.editDayDescriptor = editDayDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (dayIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(dayIndex.getZeroBased());
        Contact editedContact = createEditedContact(contactToEdit, editContactDescriptor);

        if (!contactToEdit.isSameContact(editedContact) && model.hasContact(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(String.format(MESSAGE_UNSCHEDULE_TIME_SUCCESS, editedContact));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddContactCommand // instanceof handles nulls
                && toAdd.equals(((AddContactCommand) other).toAdd));
    }

    /**
     * Stores the details to edit the contacts with. Each non-empty field value will replace the
     * corresponding field value of the contacts.
     */
    public static class EditDayDescriptor {
        private List<ActivityWithTime> activitiesInDay;

        public EditDayDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDayDescriptor(UnscheduleTimeCommand.EditDayDescriptor toCopy) {
            setActivitiesInDay(toCopy.activitiesInDay);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setActivitiesInDay(List<ActivityWithTime> activitiesInDay) {
            this.activitiesInDay = activitiesInDay;
        }

        public Optional<Name> getActivitiesInDay() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditContactDescriptor)) {
                return false;
            }

            // state check
            EditCommand.EditContactDescriptor e = (EditCommand.EditContactDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}