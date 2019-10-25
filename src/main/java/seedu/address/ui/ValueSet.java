package seedu.address.ui;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ValueSet<T> {

    private List<T> backingList;

    public ValueSet(List<T> backingList) {
        this.backingList = backingList;
    }

    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(t -> values.add(t.toString()));
        return values;
    }

}
