package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.item.Event;
import seedu.address.commons.core.item.Item;
import seedu.address.commons.core.item.Item.ItemBuilder;
import seedu.address.commons.core.item.ItemDescription;
import seedu.address.commons.core.item.Priority;
import seedu.address.commons.core.item.Reminder;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Item objects.
 */
public class TestItemBuilder {

    public static final String DEFAULT_DESCRIPTION = "Do 2103T Quiz";
    public static final String DEFAULT_EVENT = "2019-09-26T23:59";
    public static final String DEFAULT_REMINDER = "2019-09-25T23:59";
    public static final String DEFAULT_PRIORITY = "HIGH";
    public static final String DEFAULT_EVENT_DURATION = null;

    private ItemDescription description;
    private Event event;
    private Reminder reminder;
    private Priority priority; // priority of the Event
    private Set<Tag> tags;

    public TestItemBuilder() {
        description = new ItemDescription(DEFAULT_DESCRIPTION);
        event = processEvent(DEFAULT_EVENT, DEFAULT_EVENT_DURATION, DEFAULT_PRIORITY);
        reminder = processReminder(DEFAULT_REMINDER);
        tags = new HashSet<>();
    }

    /**
     * Initializes the TestItemBuilder with the data of {@code itemToCopy}.
     */
    public TestItemBuilder(Item itemToCopy) {
        description = itemToCopy.getItemDescription();
        event = itemToCopy.getEvent().orElse(null);
        reminder = itemToCopy.getReminder().orElse(null);
        tags = new HashSet<>(itemToCopy.getTags());
    }

    /**
     * Sets the {@code desc} of the {@code Item} that we are building.
     */
    public TestItemBuilder withDescription(String desc) {
        this.description = new ItemDescription(desc);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Item} that we are building.
     */
    public TestItemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code itemEvent} of the {@code Item} that we are building.
     */
    public TestItemBuilder withEvent(String itemEvent, String duration, String priority) {
        this.event = processEvent(itemEvent, duration, priority);
        return this;
    }

    /**
     * Sets the {@code itemReminder} of the {@code Item} that we are building.
     */
    public TestItemBuilder withReminder(String itemReminder) {
        this.reminder = processReminder(itemReminder);
        return this;
    }

    /**
     * Builds a new Item object with the given descriptions
     * @return a new Item with the given descriptors
     */
    public Item build() {
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setItemDescription(description);
        itemBuilder.setEvent(event);
        itemBuilder.setReminder(reminder);
        itemBuilder.setTags(tags);

        Item newItem = null;
        try {
            newItem = itemBuilder.build();
        } catch (IllegalArgumentException e) {
            // Should not enter here as all the arguments are valid.
        }

        return newItem;
    }

    /**
     * Processes a {@code datetime}, a {@code duration} and a {@code itemPriority} to generate an appropriate Event.
     * @param datetime start DateTime of this Event
     * @param duration of this Event
     * @param itemPriority of this Event
     * @return a new Event with the given parameters
     */
    private Event processEvent(String datetime, String duration, String itemPriority) {
        Event event = null;
        try {
            event = ParserUtil.parseDateTime(datetime).get();
            priority = ParserUtil.parsePriority(itemPriority).get();
            event = event.changePriority(priority);
        } catch (ParseException e) {
            // should not enter here as DEFAULT_EVENT and DEFAULT_PRIORITY are of the correct format.
        }
        return event;
    }

    /**
     * Processes the {@code itemReminder} given to generate an appropriate Reminder with the dateTime given.
     * @param itemReminder representing the DateTime of the reminder
     * @return a new Reminder with the given date and time
     */
    private Reminder processReminder(String itemReminder) {
        Reminder reminder = null;
        try {
            reminder = ParserUtil.parseReminder(itemReminder).get();
        } catch (ParseException e) {
            //should not enter here as DEFAULT_REMINDER is of the correct format
        }
        return reminder;
    }
}
