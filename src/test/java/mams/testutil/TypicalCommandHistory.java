package mams.testutil;

import java.util.Arrays;

import mams.logic.CommandHistory;
import mams.logic.InputOutput;

/**
 * Utility class for building a sample CommandHistory populated with dummy InputOutput data
 * for testing purposes.
 */
public class TypicalCommandHistory {

    // Defined explicitly instead of imported to reduce dependencies
    public static final String VALID_COMMAND_1 = "list";
    public static final String VALID_COMMAND_OUTPUT_1 = "Listed all appeals\nListed all modules\nListed all students";
    public static final String VALID_COMMAND_2 = "list -a";
    public static final String VALID_COMMAND_OUTPUT_2 = "Listed all appeals\n";

    // Invalid in this context means commands that will produce an error message,
    // and does not refer to invalid Strings.
    public static final String INVALID_COMMAND_1 = "afoaref";
    public static final String INVALID_COMMAND_2 = "cs2103";
    public static final String INVALID_COMMAND_OUTPUT = "Unknown command";

    public static final InputOutput VALID_IO_1 = new InputOutput(VALID_COMMAND_1, VALID_COMMAND_OUTPUT_1);
    public static final InputOutput VALID_IO_2 = new InputOutput(VALID_COMMAND_2, VALID_COMMAND_OUTPUT_2);

    public static final InputOutput INVALID_IO_1 = new InputOutput(INVALID_COMMAND_1, INVALID_COMMAND_OUTPUT);
    public static final InputOutput INVALID_IO_2 = new InputOutput(INVALID_COMMAND_2, INVALID_COMMAND_OUTPUT);

    // not used as part of #getTypicalCommandHistory()
    public static final String VALID_COMMAND_3 = "list -a -s";
    public static final String VALID_COMMAND_OUTPUT_3 = "Listed all appeals\nListed all students\n";

    public static final String VALID_COMMAND_4 = "list -s";
    public static final String VALID_COMMAND_OUTPUT_4 = "Listed all students\n";

    public static final InputOutput VALID_IO_3 = new InputOutput(VALID_COMMAND_3, VALID_COMMAND_OUTPUT_3);
    public static final InputOutput VALID_IO_4 = new InputOutput(VALID_COMMAND_4, VALID_COMMAND_OUTPUT_4);

    private TypicalCommandHistory() {} // prevents instantiation

    public static CommandHistory getTypicalCommandHistory() {
        return new CommandHistory(Arrays.asList(VALID_IO_1, VALID_IO_2, INVALID_IO_1, INVALID_IO_2));
    }
}
