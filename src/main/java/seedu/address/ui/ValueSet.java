package seedu.address.ui;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ValueSet {

    private List<?> backingList;

    public ValueSet(List<?> backingList) {
        this.backingList = backingList;
    }

    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(obj -> values.add(obj.toString()));
        return values;
    }

}
