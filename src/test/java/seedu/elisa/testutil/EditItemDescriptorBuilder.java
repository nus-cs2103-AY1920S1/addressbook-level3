package seedu.elisa.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.elisa.commons.core.item.Event;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.ItemDescription;
import seedu.elisa.commons.core.item.Priority;
import seedu.elisa.commons.core.item.Reminder;
import seedu.elisa.commons.core.item.Task;
import seedu.elisa.commons.core.item.tag.Tag;
import seedu.elisa.logic.commands.EditCommand.EditItemDescriptor;
import seedu.elisa.logic.parser.ParserUtil;
import seedu.elisa.logic.parser.exceptions.ParseException;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditItemDescriptorBuilder {

    private EditItemDescriptor descriptor;

    public EditItemDescriptorBuilder() {
        descriptor = new EditItemDescriptor();
    }

    public EditItemDescriptorBuilder(EditItemDescriptor descriptor) {
        this.descriptor = new EditItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditItemDescriptorBuilder(Item item) {
        descriptor = new EditItemDescriptor();
        descriptor.setDescription(item.getItemDescription());
        descriptor.setTask(item.getTask().orElse(null));
        descriptor.setEvent(item.getEvent().orElse(null));
        descriptor.setReminder(item.getReminder().orElse(null));
        descriptor.setTags(item.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new ItemDescription(description));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withTask(boolean complete) {
        descriptor.setTask(new Task(complete));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withEvent(String start, String duration) {
        Event processedEvent = getEvent(start, duration);
        descriptor.setEvent(processedEvent);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withReminder(String dateTime) {
        Reminder processedReminder = getReminder(dateTime);
        descriptor.setReminder(processedReminder);
        return this;
    }

    /**
     * Parses the {@code priority} into a {@code priority} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditItemDescriptorBuilder withPriority(String priority) {
        Priority newPriority = Priority.fromJson(priority);
        descriptor.setPriority(newPriority);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditItemDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditItemDescriptor build() {
        return descriptor;
    }


    private Priority getPriority(String p) {
        Priority priority = null;
        try {
            priority = ParserUtil.parsePriority(p).get();
        } catch (ParseException e) {
            //should not enter here as priority given is valid.
        }
        return priority;
    }

    private Event getEvent(String start, String duration) {
        Event event = null;
        try {
            event = ParserUtil.parseDateTime(start).get();
        } catch (ParseException e) {
            //should not enter here as start format should be valid.
        }

        if (duration != null) {
            // Leave empty as of now, currently we don't deal with parsing duration
            //event = event.changeDuration(Duration.of(...) );
        }
        return event;
    }

    private Reminder getReminder(String itemReminder) {
        Reminder reminder = null;
        try {
            reminder = ParserUtil.parseReminder(itemReminder).get();
        } catch (ParseException e) {
            // Should not enter here as itemReminder format should be valid.
        }

        return reminder;
    }
}
