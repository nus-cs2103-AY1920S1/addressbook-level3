package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_NULL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_2;


import seedu.address.commons.core.item.Item;
import seedu.address.model.item.ItemList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {

    public static final Item HOMEWORK = new TestItemBuilder().withDescription("Do all homework")
            .withEvent("2018-12-30T19:34:50.63", null, null)
            .withReminder("2018-12-29T19:34:50.63")
            .build();

    public static final Item EXAM = new TestItemBuilder().withDescription("Final Exam")
            .withEvent("2019-10-30T12:00", null, null)
            .withReminder("2019-10-29T12:00")
            .build();

    // Manually added - Item's detail found in {@code CommandTestUtil}
    public static final Item ITEM_1 = new TestItemBuilder().withDescription(VALID_DESCRIPTION_1)
                .withEvent(VALID_EVENT_1, VALID_DURATION_NULL, VALID_PRIORITY_HIGH)
                .withReminder(VALID_REMINDER_1)
                .withTags(VALID_TAG_1).build();
    public static final Item ITEM_2 = new TestItemBuilder().withDescription(VALID_DESCRIPTION_2)
            .withEvent(VALID_EVENT_2, VALID_DURATION_NULL, VALID_PRIORITY_LOW)
            .withReminder(VALID_REMINDER_2)
            .withTags(VALID_TAG_2).build();


    private TypicalItems() {} //prevents instantiation



    /**
     * Returns an {@code ItemList} with all the typical persons.
     */
    public static ItemList getTypicalItemList() {
        ItemList il = new ItemList();
        for (Item item : getTypicalItems()) {
            il.add(item);
        }
        return il;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(HOMEWORK, EXAM));
    }
}