package seedu.elisa.model.item;

import java.util.List;

import seedu.elisa.commons.core.item.Item;

/**
 * Object class to store all the items that are events within the program
 */
public class EventList extends VisualizeList {
    public EventList() {
        super();
    }

    public EventList(List<Item> list) {
        super(list);
    }

    /**
     * Sorts the event list based on the date of the event.
     * @return a sorted EventList of the current list
     */
    public VisualizeList sort() {
        EventList el = new EventList(list);
        el.sort((item1, item2) -> item1.getEvent().get().getStartDateTime()
                .compareTo(item2.getEvent().get().getStartDateTime()));
        return el;
    }

    /**
     * Finds a substring within the description of an item.
     * @param searchString a string to be search for within the description of an item
     * @return a new EventList only containing the items that have the search string in their description
     */
    public VisualizeList find(String[] searchString) {
        return find(searchString, new EventList());
    }

    @Override
    public VisualizeList deepCopy() {
        return super.deepCopy(new EventList());
    }

    public boolean belongToList(Item item) {
        return item.hasEvent();
    }
}
