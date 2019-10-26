package seedu.mark.logic.commands;

import seedu.mark.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.mark.logic.parser.ParserUtil;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.reminder.Note;
import seedu.mark.model.reminder.Reminder;

public class EditReminderDescriptorBuilder {

    private EditReminderDescriptor descriptor;

    public EditReminderDescriptorBuilder() {
        descriptor = new EditReminderDescriptor();
    }

    public EditReminderDescriptorBuilder(EditReminderDescriptor descriptor) {
        this.descriptor = new EditReminderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditReminderDescriptor} with fields containing {@code reminder}'s details
     */
    public EditReminderDescriptorBuilder(Reminder reminder) {
        descriptor = new EditReminderDescriptor();
        descriptor.setNote(reminder.getNote());
        descriptor.setTime(reminder.getRemindTime());
    }

    /**
     * Sets the {@code Note} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withTime(String time) throws ParseException {
        descriptor.setTime(ParserUtil.parseTime(time));
        return this;
    }



    public EditReminderCommand.EditReminderDescriptor build() {
        return descriptor;
    }
}
