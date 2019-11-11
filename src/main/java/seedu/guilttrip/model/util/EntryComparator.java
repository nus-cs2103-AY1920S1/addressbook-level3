package seedu.guilttrip.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.SortSequence;
import seedu.guilttrip.model.entry.SortType;
import seedu.guilttrip.model.tag.Tag;

/**
 * Represents a EntryComparator in the finance manager.
 * SortType specifies the type of sorting and SortSequence specifies the sequence of sorting.
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
        int sortByAmount = 0;
        int sortByDate = 0;
        int sortByDesc = 0;
        int sortByCategory = 0;
        int sortByTag = 0;
        switch(typeOfComparator.toString()) {
        case "amount":
            sortByAmount = helperSortByAmount(e1, e2);
            if (sortByAmount != 0) {
                return sortByAmount * this.sequence.getSequence();
            }

            sortByDate = helperSortByDate(e1, e2);
            if (sortByDate != 0) {
                return sortByDate;
            }

            sortByDesc = helperSortByDesc(e1, e2);
            if (sortByDesc != 0) {
                return sortByDesc;
            }

            sortByCategory = helperSortByCategory(e1, e2);
            if (sortByCategory != 0) {
                return sortByCategory;
            }

            return helperSortByTag(e1, e2);
        case "description":
            sortByDesc = helperSortByDesc(e1, e2);
            if (sortByDesc != 0) {
                return sortByDesc * this.sequence.getSequence();
            }

            sortByDate = helperSortByDate(e1, e2);
            if (sortByDate != 0) {
                return sortByDate;
            }

            sortByAmount = helperSortByAmount(e1, e2);
            if (sortByAmount != 0) {
                return sortByAmount;
            }

            sortByCategory = helperSortByCategory(e1, e2);
            if (sortByCategory != 0) {
                return sortByCategory;
            }

            return helperSortByTag(e1, e2);
        case "time":
            sortByDate = helperSortByDate(e1, e2);
            if (sortByDate != 0) {
                return sortByDate * this.sequence.getSequence();
            }

            sortByAmount = helperSortByAmount(e1, e2);
            if (sortByAmount != 0) {
                return sortByAmount;
            }

            sortByDesc = helperSortByDesc(e1, e2);
            if (sortByDesc != 0) {
                return sortByDesc;
            }

            sortByCategory = helperSortByCategory(e1, e2);
            if (sortByCategory != 0) {
                return sortByCategory;
            }

            return helperSortByTag(e1, e2);
        case "tags":
            sortByTag = helperSortByTag(e1, e2);
            if (sortByTag != 0) {
                return sortByTag * this.sequence.getSequence();
            }

            sortByDate = helperSortByDate(e1, e2);
            if (sortByDate != 0) {
                return sortByDate;
            }

            sortByAmount = helperSortByAmount(e1, e2);
            if (sortByAmount != 0) {
                return sortByAmount;
            }

            sortByDesc = helperSortByDesc(e1, e2);
            if (sortByDesc != 0) {
                return sortByDesc;
            }

            return sortByCategory;
        case "category":
            sortByCategory = helperSortByCategory(e1, e2);
            if (sortByCategory != 0) {
                return sortByCategory * this.sequence.getSequence();
            }

            sortByDate = helperSortByDate(e1, e2);
            if (sortByDate != 0) {
                return sortByDate;
            }

            sortByAmount = helperSortByAmount(e1, e2);
            if (sortByAmount != 0) {
                return sortByAmount;
            }

            sortByDesc = helperSortByDesc(e1, e2);
            if (sortByDesc != 0) {
                return sortByDesc;
            }

            return helperSortByTag(e1, e2);
        default:
            //Should not reach this point as valid checks were carried out beforehand.
            return 1;
        }
    }

    /**
     * Sort the Entries by Categories.
     */
    public int helperSortByCategory(Entry e1, Entry e2) {
        return (e1.getCategory().getCategoryName().toLowerCase()).compareTo(e2.getCategory()
                .getCategoryName().toLowerCase());
    }

    /**
     * Sort the Entries by Tags.
     */
    public int helperSortByTag(Entry e1, Entry e2) {
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
                .compareToIgnoreCase(secondEntryStringofTags.toLowerCase());
    }

    public int helperSortByDate(Entry e1, Entry e2) {
        return (e1.getDate().getDate()).compareTo(e2.getDate().getDate());
    }

    public int helperSortByDesc(Entry e1, Entry e2) {
        return (e1.getDesc().fullDesc.toLowerCase()).compareTo(e2.getDesc().fullDesc.toLowerCase());
    }

    public int helperSortByAmount(Entry e1, Entry e2) {
        return (Double.valueOf(e1.getAmount().value)).compareTo(e2.getAmount().value);
    }
}
