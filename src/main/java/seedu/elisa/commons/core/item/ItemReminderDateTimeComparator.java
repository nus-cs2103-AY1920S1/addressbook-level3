package seedu.elisa.commons.core.item;

import java.util.Comparator;

/**
 * A comparator to compare the datetime between the Reminders of two Items.
 */
public class ItemReminderDateTimeComparator implements Comparator<Item> {
    @Override
    public int compare(Item firstItem, Item secondItem) {
        int comparisonResult = 0;
        if (firstItem.getReminder().isEmpty()) {
            comparisonResult = -1;
        } else if (secondItem.getReminder().isEmpty()) {
            comparisonResult = 1;
        } else {
            comparisonResult = firstItem.getReminder().get().getOccurrenceDateTime().compareTo(
                    secondItem.getReminder().get().getOccurrenceDateTime());
        }
        return comparisonResult;
    }
}
