package seedu.address.diaryfeature.logic.parser.exceptions;


/**
 * Generic Empty Exception if the user leaves the argument blank
 */
public class EmptyArgumentException extends Exception {
    private String parserName;
    private static String filler = "Something is wrong with the ";
    private String usage;

    /**
     * Generates EmptyArgumentException with the specific parser and how to use it
     *
     * @param parserName specific parser from which it came from
     * @param usage      how to use the command
     */

    public EmptyArgumentException(String parserName, String usage) {
        this.parserName = parserName;
        this.usage = usage;
    }


    /**
     * Generates EmptyArgumentException with the specific parser
     *
     * @param parserName specific parser from which it came from
     */
    public EmptyArgumentException(String parserName) {
        this.parserName = parserName;
        this.usage = "";
    }

    /**
     * @return String representation of the Error message
     */
    public String toString() {
        return filler + parserName + " command" + "\n" + usage;
    }
}


