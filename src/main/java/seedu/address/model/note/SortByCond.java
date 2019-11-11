package seedu.address.model.note;

import java.util.Comparator;

/**
 * Represents a Note's Sorting condition.
 */
public class SortByCond {
    public static final String DATEMODIFIED = "datemodified";
    public static final String DATEADDED = "dateadded";
    public static final String NUMOFACCESS = "numofaccess";
    public static final String MESSAGE_CONSTRAINTS = "Sort condition should be either " + DATEADDED + ", or "
            + DATEMODIFIED + " or " + NUMOFACCESS;

    public final String sortByCond;
    public final Comparator<Note> sortComparator;

    /**
     * Constructs an {@code SortByCond} field with date modified as the default condition.
     */
    public SortByCond() {
        this.sortByCond = DATEMODIFIED;
        this.sortComparator = new SortByDateModified();
    }

    /**
     * Constructs an {@code SortByCond} field
     * @param sortByCond condition for the sorting of notes.
     */
    public SortByCond(String sortByCond) {
        switch (sortByCond.toLowerCase()) {
        case DATEADDED:
            this.sortByCond = DATEADDED;
            this.sortComparator = new SortByDateAdded();
            break;
        case NUMOFACCESS:
            this.sortByCond = NUMOFACCESS;
            this.sortComparator = new SortByNumOfAccess();
            break;
        default:
            this.sortByCond = DATEMODIFIED;
            this.sortComparator = new SortByDateModified();
            break;
        }
    }


    /**
     * Gets the Comparator object based on the sorting condition.
     * @return Comparator object.
     */
    public Comparator<Note> getSortComparator() {
        return sortComparator;
    }

    /**
     * Returns true if given string is a valid sorting condition.
     */
    public static boolean isValidSortByCond(String sortByCond) {
        String editedSortByCond = sortByCond.toLowerCase();
        return sortByCond.toLowerCase().equals(DATEMODIFIED) || sortByCond.toLowerCase().equals(DATEADDED)
                || sortByCond.toLowerCase().equals(NUMOFACCESS);
    }

    @Override
    public String toString() {
        return sortByCond;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortByCond // instanceof handles nulls
                && sortByCond.equals(((SortByCond) other).sortByCond)); // state check
    }

    @Override
    public int hashCode() {
        return sortByCond.hashCode();
    }

    /**
     * Comparator class that compares notes based on its NumOfAccess attribute.
     */
    class SortByNumOfAccess implements Comparator<Note> {
        @Override
        public int compare(Note a, Note b) {
            Integer numOfAccessA = Integer.valueOf(a.getNumOfAccess().numOfAccess);
            Integer numOfAccessB = Integer.valueOf(b.getNumOfAccess().numOfAccess);
            return (numOfAccessB.compareTo(numOfAccessA));
        }
    }
    /**
     * Comparator class that compares notes based on its DateModified attribute.
     */
    class SortByDateModified implements Comparator<Note> {
        @Override
        public int compare(Note a, Note b) {
            if (a.getDateModified().value.before(b.getDateModified().value)) {
                return 1;
            } else if (a.getDateModified().value.after(b.getDateModified().value)) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    /**
     * Comparator class that compares notes based on its DateAdded attribute.
     */
    class SortByDateAdded implements Comparator<Note> {
        @Override
        public int compare(Note a, Note b) {
            if (a.getDateAdded().value.before(b.getDateAdded().value)) {
                return 1;
            } else if (a.getDateAdded().value.after(b.getDateAdded().value)) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
