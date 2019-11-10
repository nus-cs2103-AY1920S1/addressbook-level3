package seedu.moneygowhere.testutil;

import java.util.LinkedHashSet;

import seedu.moneygowhere.logic.sorting.SortAttribute;
import seedu.moneygowhere.logic.sorting.SortField;
import seedu.moneygowhere.logic.sorting.SortOrder;

/**
 * A utility class for Sorting.
 */
public class SortUtil {

    /**
     * Gets the default sort field set which sorts by both date and cost in descending order.
     * @return Sort field set
     */
    public static LinkedHashSet<SortField> getDefaultSortFieldSet() {
        LinkedHashSet<SortField> sortFields = new LinkedHashSet<>();
        sortFields.add(new SortField(SortAttribute.DATE, SortOrder.DESCENDING));
        sortFields.add(new SortField(SortAttribute.COST, SortOrder.DESCENDING));
        sortFields.add(new SortField(SortAttribute.NAME, SortOrder.ASCENDING));
        sortFields.add(new SortField(SortAttribute.REMARK, SortOrder.ASCENDING));
        return sortFields;
    }
}
