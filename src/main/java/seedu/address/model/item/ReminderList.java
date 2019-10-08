package seedu.address.model.item;

/**
 * Object class to store all the items that are reminders within the program
 */
public class ReminderList extends ItemList {

    public ReminderList() {
        super();
    }

    /**
     * Sorts the Reminders by the date.
     * @return a new ReminderList with the reminders within sorted.
     */
    public ItemList sort() {
        ReminderList rl = new ReminderList();
        for (Item i : list) {
            rl.add(i);
        }
        rl.list.sort((item1, item2) -> item1.getReminder().getDate().compareTo(item2.getReminder().getDate()));
        return rl;
    }

    /**
     * Finds a substring within the description of an item.
     * @param searchString a string to be search for within the description of an item
     * @return a new ReminderList only containing the items that have the search string in their description
     */
    public ItemList find(String searchString) {
        ReminderList rl = new ReminderList();
        return super.find(searchString, rl);
    }
}
