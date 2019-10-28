package seedu.address.model.util;


import seedu.address.model.note.Note;

import java.util.Comparator;

/**
 * Represents a File's encryption date and time in SecureIT.
 */
public class SortByUtil {
    public static final String DATEMODIFIED = "DateModified";
    public static final String DATECREATED = "DateCreated";
    public static final String NUMOFACCESS = "NumOfAccess";
    public static final String MESSAGE_CONSTRAINTS = "Sort condition should be either" + DATECREATED + ", or " +
            DATECREATED + " or " + NUMOFACCESS;

    public final String sortByCond;
    public final Comparator<Note> sortComparator;

    /**
     * Constructs an {@code EncryptedAt} field.
     *
     */
    public SortByUtil() {
        this.sortByCond = DATEMODIFIED;
//        return new SortByCond(sortByCond, new SortByDateCreated());
        this.sortComparator = new SortByDateModified();
    }

    public SortByUtil(String sortByCond) {
        switch (sortByCond) {
//        case DATECREATED:
////            this.sortByCond = DATECREATED;
////            this.sortComparator = new SortByDateCreated();
////             break;
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

    public Comparator<Note> getSortComparator() {
        return sortComparator;
    }

    public static boolean isValidSortByCond(String sortByCond) {
        return sortByCond.equals("DateModified") || sortByCond.equals("DateCreated") ||
                sortByCond.equals("NumOfAccess");
    }


    @Override
    public String toString() {
        return sortByCond;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortByUtil // instanceof handles nulls
                && sortByCond.equals(((SortByUtil) other).sortByCond)); // state check
    }

    @Override
    public int hashCode() {
        return sortByCond.hashCode();
    }

    class SortByNumOfAccess implements Comparator<Note> {
        public int compare(Note a, Note b) {
            Integer numOfAccessA = Integer.valueOf(a.getNumOfAccess().numOfAccess);
            Integer numOfAccessB = Integer.valueOf(b.getNumOfAccess().numOfAccess);
            return (numOfAccessB.compareTo(numOfAccessA));
        }
    }
    class SortByDateModified implements Comparator<Note> {
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
//    class SortByDateCreated implements Comparator<Note> {
//        public int compare(Note a, Note b) {
//            if (a.getDateCreated().value.before(b.getDateCreated().value)) {
//                return -1;
//            } else if (a.getDateCreated().value.after(b.getDateCreated().value)) {
//                return 1;
//            } else {
//                return 0;
//            }
//        }
//    }
}
