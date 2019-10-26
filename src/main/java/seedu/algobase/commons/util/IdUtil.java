package seedu.algobase.commons.util;

/**
 * A container for a utility function to generate Id
 */
public class IdUtil {

    /**
     * Generate Id based on unix timestamp.
     */
    public static long generateId() {
        return System.currentTimeMillis() / 1000L;
    }

}
