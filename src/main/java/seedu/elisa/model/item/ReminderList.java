package seedu.elisa.model.item;

import java.util.List;

import seedu.elisa.commons.core.item.Item;

/**
 * Object class to store all the items that are reminders within the program
 */
public class ReminderList extends VisualizeList {

    public ReminderList() {
        super();
    }

    public ReminderList(List<Item> list) {
        super(list);
    }

    /**
     * Sorts the Reminders by the date.
     * @return a new ReminderList with the reminders within sorted.
     */
    public VisualizeList sort() {
        ReminderList rl = new ReminderList(list);
        rl.sort((item1, item2) -> item1.getReminder().get().getDefaultDateTime()
                .compareTo(item2.getReminder().get().getDefaultDateTime()));
        return rl;
    }

    /**
     * Finds a substring within the description of an item.
     * @param searchString a string to be search for within the description of an item
     * @return a new ReminderList only containing the items that have the search string in their description
     */
    public VisualizeList find(String[] searchString) {
        return super.find(searchString, new ReminderList());
    }

    @Override
    public VisualizeList deepCopy () {
        return super.deepCopy(new ReminderList());
    }

    public boolean belongToList(Item item) {
        return item.hasReminder();
    }
}
