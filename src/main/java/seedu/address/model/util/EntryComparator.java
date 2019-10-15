package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.person.Entry;
import seedu.address.model.person.SortType;

public class EntryComparator implements Comparator<Entry> {

    SortType typeOfComparator;

    public EntryComparator(SortType typeOfComparator) {
        this.typeOfComparator = typeOfComparator;
    }

    @Override
    public int compare(Entry e1, Entry e2) {
        switch(typeOfComparator.toString()) {
        case "amount":
            return (Double.valueOf(e1.getAmount().value)).compareTo(e2.getAmount().value);
        case "description":
            return (e1.getDesc().fullDesc).compareTo(e2.getDesc().fullDesc);
        case "time":
            return (e1.getTime().fullTime).compareTo(e2.getTime().fullTime);
        default:
            //TODO
            return 1;
        }
    }
}
