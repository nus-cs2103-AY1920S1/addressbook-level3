package seedu.address.model.note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Note's Sorting condition.
 */
public class MultipleSortByCond {

    public static final String DATEMODIFIED = "datemodified";
    public static final String DATEADDED = "dateadded";
    public static final String NUMOFACCESS = "numofaccess";
    public static final String MESSAGE_CONSTRAINTS = "Sort condition must have at least one " + DATEADDED
            + ", or "
            + DATEMODIFIED + " or " + NUMOFACCESS + " separated by a whitespace each."
            + "\nDuplicate conditions are not allowed.";

    public final String[] sortConditions;
    public final MultipleCondNoteComparator multipleSortComparator;

    /**
     * Constructs an {@code MultipleSortByCond} object with the array of sort conditions as parameters.
     */
    public MultipleSortByCond(String[] sortConditions) {
        this.sortConditions = sortConditions;
        this.multipleSortComparator = buildComparator(sortConditions);
    }

    private Comparator<Note> parseSortByCond(String sortByCond) {
        //TODO: assert sortbycond here is valid
        switch (sortByCond.toLowerCase()) {
        case DATEADDED:
            return new SortByDateAdded();
        case NUMOFACCESS:
            return new SortByNumOfAccess();
        default:
            return new SortByDateModified();
        }
    }

    /**
     * Builds a comparator based on the sort conditions provided.
     *
     * @param sortConditions decreasing precedence of the sorting condition as the index number
     *                       of the sort condition increases in the String array.
     * @return
     */
    private MultipleCondNoteComparator buildComparator(String[] sortConditions) {
        ArrayList<Comparator<Note>> noteComparators = new ArrayList<>();
        for (String sortCond : sortConditions) {
            Comparator<Note> currentComparator = parseSortByCond(sortCond);
            noteComparators.add(currentComparator);
        }
        return new MultipleCondNoteComparator(noteComparators);

    }

    /**
     * Gets the Comparator object based on the sorting condition.
     *
     * @return Comparator object.
     */
    public Comparator<Note> getMultipleSortComparator() {
        return multipleSortComparator;
    }

    /**
     * Checks if given each string in the given array of string are valid sorting conditions.
     */
    private static boolean isValidIndividualSortByCond(String[] sortConditions) {
        for (String cond : sortConditions) {
            String sortByCond = cond.toLowerCase();
            if (!(sortByCond.toLowerCase().equals(DATEMODIFIED) || sortByCond.toLowerCase().equals(DATEADDED)
                    || sortByCond.toLowerCase().equals(NUMOFACCESS))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if given array of string has any duplicate strings.
     */
    private static boolean isDuplicateSortByCond(String[] sortConditions) {
        Set<String> checkDuplicate = new HashSet<>();
        for (String cond : sortConditions) {
            String currentCond = cond.toLowerCase();
            if (checkDuplicate.contains(currentCond)) {
                return true;
            } else {
                checkDuplicate.add(currentCond);
            }
        }
        return false;
    }

    /**
     * Checks if given array of string of sort conditions is empty.
     */
    private static boolean isEmptySortByCond(String[] sortConditions) {
        return sortConditions.length == 0;
    }

    /**
     * Returns true if given string conditions has no duplicates and are correct conditions individually.
     */
    public static boolean isValidSortByConditions(String[] sortConditions) {
        return !isDuplicateSortByCond(sortConditions) && isValidIndividualSortByCond(sortConditions)
                && !isEmptySortByCond(sortConditions);
    }

    @Override
    public String toString() {
        return String.join(", then by ", Arrays.asList(sortConditions));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MultipleSortByCond // instanceof handles nulls
                && sortConditions.equals(((MultipleSortByCond) other).sortConditions)); // state check
    }

    @Override
    public int hashCode() {
        return sortConditions.hashCode();
    }

    /**
     * Comparator class that compares notes based on its NumOfAccess attribute.
     */
    static class MultipleCondNoteComparator implements Comparator<Note> {
        private final List<Comparator<Note>> comparators;

        private MultipleCondNoteComparator(List<Comparator<Note>> comparators) {
            this.comparators = comparators;
        }

        /**
         * https://stackoverflow.com/questions/4258700/collections-sort-with-multiple-fields
         *
         * @param a
         * @param b
         * @return
         */
        @Override
        public int compare(Note a, Note b) {
            for (Comparator<Note> comparator : comparators) {
                int result = comparator.compare(a, b);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }
    }

    /**
     * Comparator class that compares notes based on its NumOfAccess attribute.
     */
    static class SortByNumOfAccess implements Comparator<Note> {
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
    static class SortByDateModified implements Comparator<Note> {
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
    static class SortByDateAdded implements Comparator<Note> {
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
