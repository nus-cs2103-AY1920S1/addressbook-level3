package seedu.elisa.testutil;

import static seedu.elisa.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_DURATION_NULL;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_EVENT_1;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_EVENT_2;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_REMINDER_1;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_REMINDER_2;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_TAG_1;
import static seedu.elisa.logic.commands.CommandTestUtil.VALID_TAG_2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.elisa.commons.core.item.Event;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.ItemDescription;
import seedu.elisa.commons.core.item.Reminder;
import seedu.elisa.commons.core.item.Task;
import seedu.elisa.model.ItemStorage;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {
    public static final Item ITEM_WITH_TASK = new Item.ItemBuilder()
            .setItemDescription(new ItemDescription("test"))
            .setTask(new Task(false)).build();

    public static final Item ITEM_WITH_EVENT = new Item.ItemBuilder()
            .setItemDescription(new ItemDescription("test"))
            .setEvent(new Event(LocalDateTime.now(), Duration.ZERO)).build();

    public static final Item ITEM_WITH_REMINDER = new Item.ItemBuilder()
            .setItemDescription(new ItemDescription("test"))
            .setReminder(new Reminder(LocalDateTime.now())).build();

    public static final Item ITEM_WITH_ALL = new Item.ItemBuilder()
            .setItemDescription(new ItemDescription("test"))
            .setTask(new Task(false))
            .setEvent(new Event(LocalDateTime.now(), Duration.ZERO))
            .setReminder(new Reminder(LocalDateTime.now())).build();

    public static final Item HOMEWORK = new TestItemBuilder().withDescription("Do all homework")
            .withEvent("2018-12-30T19:34:50.63", null)
            .withReminder("2018-12-29T19:34:50.63")
            .build();

    public static final Item EXAM = new TestItemBuilder().withDescription("Final Exam")
            .withEvent("2019-10-30T12:00", null)
            .withReminder("2019-10-29T12:00")
            .build();

    // Manually added - Item's detail found in {@code CommandTestUtil}
    public static final Item ITEM_1 = new TestItemBuilder().withDescription(VALID_DESCRIPTION_1)
                .withPriority(VALID_PRIORITY_HIGH)
                .withEvent(VALID_EVENT_1, VALID_DURATION_NULL)
                .withReminder(VALID_REMINDER_1)
                .withTags(VALID_TAG_1).build();
    public static final Item ITEM_2 = new TestItemBuilder().withDescription(VALID_DESCRIPTION_2)
            .withPriority(VALID_PRIORITY_LOW)
            .withEvent(VALID_EVENT_2, VALID_DURATION_NULL)
            .withReminder(VALID_REMINDER_2)
            .withTags(VALID_TAG_2).build();


    private TypicalItems() {} //prevents instantiation



    /**
     * Returns an {@code ItemStorage} with all the typical persons.
     */
    public static ItemStorage getTypicalItemList() {
        ItemStorage itemStorage = new ItemStorage();
        for (Item item : getTypicalItems()) {
            itemStorage.add(item);
        }
        return itemStorage;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(HOMEWORK, EXAM));
    }
}
