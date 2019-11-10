package seedu.elisa.model.item;

import seedu.elisa.commons.core.item.Item;

/**
 * Object class to store all the items that are part of the calendars within the program.
 */
public class CalendarList extends VisualizeList {
    public CalendarList() {
        super();
    }

    @Override
    public VisualizeList find(String[] searchString) {
        return super.find(searchString, new CalendarList());
    }

    @Override
    public VisualizeList deepCopy() {
        return super.deepCopy(new CalendarList());
    }

    @Override
    public VisualizeList sort() {
        // calendar list is used in the panel and so their sorting will have no value
        return this;
    }

    public boolean belongToList(Item item) {
        return item.hasEvent();
    }

}
