package seedu.address.testutil;

import seedu.address.commons.exceptions.ViewException;
import seedu.address.model.View;

/**
 * A utility class containing a list of {@code View} objects to be used in tests.
 */
public class Views {

    private static View firstView;

    static {
        try {
            firstView = new View("contacts", 1);
        } catch (ViewException e) {
            e.printStackTrace();
        }
    }

    private static View secondView;

    static {
        try {
            secondView = new View("claims", 2);
        } catch (ViewException e) {
            e.printStackTrace();
        }
    }

    private static View thirdView;

    static {
        try {
            thirdView = new View("incomes", 3);
        } catch (ViewException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the view
     */
    public static View getFirstView() {
        return firstView;
    }

    /**
     * Gets the view
     */
    public static View getSecondView() {
        return secondView;
    }

    /**
     * Gets the view
     */
    public static View getThirdView() {
        return thirdView;
    }
}
