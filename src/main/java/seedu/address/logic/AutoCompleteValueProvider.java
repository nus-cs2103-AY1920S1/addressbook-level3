package seedu.address.logic;

import java.util.SortedSet;

public interface AutoCompleteValueProvider {

    SortedSet<String> getValues();

}
