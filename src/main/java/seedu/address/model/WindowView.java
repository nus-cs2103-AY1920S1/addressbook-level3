package seedu.address.model;

import static java.util.Objects.requireNonNull;

/**
 * Changes the view of the UI.
 */
public class WindowView {

    public static final String MESSAGE_CONSTRAINTS =
            "WindowView should only contain the destinations "
                    + "that are valid (i.e. calendar or student_profile), and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX =
            "calendar|earnings|student_profile|notepad|reminders|task";
    private static String tab;
    private static int indexNumber;

    public WindowView(String tabChange) {
        requireNonNull(tabChange);
        tab = tabChange;
        assignIndex(tabChange);
    }

    public WindowView(String tabChange, Integer indexNum) {
        requireNonNull(tabChange);
        requireNonNull(indexNum);
        tab = tabChange;
        indexNumber = indexNum;
    }

    public Integer getIndexNumber() {
        return indexNumber;
    }

    /**
     * Assigns the index for the relevant tab.
     * @param dest destination of the change tab.
     */
    public static void assignIndex(String dest) {
        int index = 0;
        if (tab.equals("calendar")) {
            index = 1;
        } else if (tab.equals("earnings")) {
            index = 2;
        } else if (tab.equals("student_profile")) {
            index = 3;
        } else if (tab.equals("notepad")) {
            index = 4;
        } else if (tab.equals("reminders")) {
            index = 5;
        } else if (tab.equals("task")) {
            index = 6;
        }
        new WindowView(tab, index);
    }

    /**
     * Returns true if a given string is a valid week number.
     */
    public static boolean isValidWindowView(String test) {
        return test.matches(VALIDATION_REGEX);
    }

}
