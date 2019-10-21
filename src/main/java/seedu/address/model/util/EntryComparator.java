package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Entry;
import seedu.address.model.person.SortSequence;
import seedu.address.model.person.SortType;
import seedu.address.model.tag.Tag;

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
            return (e1.getDesc().fullDesc.toLowerCase()).compareTo(e2.getDesc().fullDesc.toLowerCase())
                    * this.sequence.getSequence();
        case "time":
            return (e1.getDate().getDate()).compareTo(e2.getDate().getDate()) * this.sequence.getSequence();
        case "tags":
            Set<Tag> firstEntryTags = e1.getTags();
            Set<Tag> secondEntryTags = e2.getTags();
            List<Tag> firstEntryListTags = new ArrayList<Tag>(firstEntryTags);
            List<Tag> secondEntryListTags = new ArrayList<Tag>(secondEntryTags);
            Collections.sort(firstEntryListTags);
            Collections.sort(secondEntryListTags);
            String firstEntryStringofTags = firstEntryListTags.stream().map(n -> n.tagName)
                    .collect(Collectors.joining());
            String secondEntryStringofTags = secondEntryListTags.stream().map(n -> n.tagName)
                    .collect(Collectors.joining());
            return firstEntryStringofTags.toLowerCase()
                    .compareToIgnoreCase(secondEntryStringofTags.toLowerCase()) * this.sequence.getSequence();
        default:
            //TODO
            return 1;
        }
    }
}
