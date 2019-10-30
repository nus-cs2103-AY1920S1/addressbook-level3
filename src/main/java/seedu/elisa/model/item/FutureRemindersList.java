package seedu.elisa.model.item;

import java.util.ArrayList;

import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.ItemReminderDateTimeComparator;

/**
 * A data structure to hold Items with Reminders that have not yet been prompted to the user.
 */
public class FutureRemindersList extends ArrayList<Item> {
    @Override
    public boolean add(Item item) {
        boolean result = super.add(item);
        super.sort(new ItemReminderDateTimeComparator());
        return result;
    }
}
