package seedu.moneygowhere.logic.sorting;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.moneygowhere.model.spending.Spending;

//@@author Nanosync
/**
 * Compares Spending objects.
 */
public class SpendingComparator implements Comparator<Spending> {
    private Set<SortField> fields;

    /**
     * Creates a Spending comparator, ordered by Date in descending order
     * and Cost by descending order.
     */
    public SpendingComparator() {
        fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.DATE, SortOrder.DESCENDING));
        fields.add(new SortField(SortAttribute.COST, SortOrder.DESCENDING));
        fields.add(new SortField(SortAttribute.NAME, SortOrder.ASCENDING));
        fields.add(new SortField(SortAttribute.REMARK, SortOrder.ASCENDING));
    }

    /**
     * Creates a Spending comparator sorted by a defined order.
     *
     * @param fields Fields to be sorted and their order
     */
    public SpendingComparator(Set<SortField> fields) {
        this.fields = fields;
    }

    @Override
    public int compare(Spending o1, Spending o2) {
        int rank = 0;
        for (SortField field : fields) {
            if (rank != 0) {
                break;
            }

            switch (field.getAttribute()) {
            case DATE:
                rank = o1.getDate().compareTo(o2.getDate());
                break;
            case COST:
                rank = o1.getCost().compareTo(o2.getCost());
                break;
            case REMARK:
                rank = o1.getRemark().compareTo(o2.getRemark());
                break;
            case NAME:
                rank = o1.getName().compareTo(o2.getName());
                break;
            default:
                throw new IllegalArgumentException("Spending comparator field is unrecognised");
            }

            // Invert the order if the sort order
            if (field.getOrder() == SortOrder.DESCENDING) {
                rank = -rank;
            }
        }

        return rank;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Sorting by: ");
        for (SortField field : fields) {
            sb.append(field);
            sb.append(", ");
        }

        return sb.toString().substring(0, sb.lastIndexOf(","));
    }
}
