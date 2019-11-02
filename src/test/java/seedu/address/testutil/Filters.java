package seedu.address.testutil;

import seedu.address.model.SortFilter;

/**
 * Filters for SortCommand
 */
//@@author {lawncegoh}
public class Filters {

    public static final SortFilter FIRST_FILTER = new SortFilter("name", 1);

    public static final SortFilter SECOND_FILTER = new SortFilter("date", 2);
}
