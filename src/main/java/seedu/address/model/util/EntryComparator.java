package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.person.Entry;
import seedu.address.model.person.SortSequence;
import seedu.address.model.person.SortType;

/**
 * Represents a EntryComparator in the finance manager.
 * SortType specifies the type of sorting and SortSequence specifies the direction of sorting.
 */
public class EntryComparator implements Comparator<Entry> {

    private SortType typeOfComparator;
    private SortSequence sequence;

    public EntryComparator(SortType typeOfComparator, SortSequence sequence) {
        this.typeOfComparator = typeOfComparator;
        this.sequence = sequence;
    }

    @Override
    public int compare(Entry e1, Entry e2) {
        switch(typeOfComparator.toString()) {
        case "amount":
            return (Double.valueOf(e1.getAmount().value)).compareTo(e2.getAmount().value) * this.sequence.getSequence();
        case "description":
            return (e1.getDesc().fullDesc).compareTo(e2.getDesc().fullDesc) * this.sequence.getSequence();
        case "time":
            return (e1.getTime().fullTime).compareTo(e2.getTime().fullTime) * this.sequence.getSequence();
        default:
            //TODO
            return 1;
        }
    }
}
