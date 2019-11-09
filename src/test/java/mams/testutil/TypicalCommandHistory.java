package mams.testutil;

import static mams.testutil.TypicalTimeStamps.TIME_STAMP_1;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_2;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_3;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_4;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_5;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_6;

import java.util.Arrays;

import mams.logic.history.CommandHistory;
import mams.logic.history.InputOutput;

/**
 * Utility class for building a sample CommandHistory populated with dummy InputOutput data
 * for testing purposes.
 */
public class TypicalCommandHistory {

    // Defined explicitly instead of imported to reduce dependencies
    public static final String SUCCESS_COMMAND_1 = "list";
    public static final String SUCCESS_COMMAND_OUTPUT_1 = "Listed all appeals\nListed all modules\nListed all students";
    public static final String SUCCESS_COMMAND_2 = "list -a";
    public static final String SUCCESS_COMMAND_OUTPUT_2 = "Listed all appeals\n";

    public static final String UNSUCCESSFUL_COMMAND_1 = "afoaref";
    public static final String UNSUCCESSFUL_COMMAND_2 = "cs2103";
    public static final String UNSUCCESSFUL_COMMAND_OUTPUT = "Unknown command";

    public static final InputOutput SUCCESSFUL_IO_1 = new InputOutput(SUCCESS_COMMAND_1, SUCCESS_COMMAND_OUTPUT_1,
            true, TIME_STAMP_1);
    public static final InputOutput SUCCESSFUL_IO_2 = new InputOutput(SUCCESS_COMMAND_2, SUCCESS_COMMAND_OUTPUT_2,
            true, TIME_STAMP_2);

    public static final InputOutput UNSUCCESSFUL_IO_1 = new InputOutput(UNSUCCESSFUL_COMMAND_1,
            UNSUCCESSFUL_COMMAND_OUTPUT,
            false,
            TIME_STAMP_3);
    public static final InputOutput UNSUCCESSFUL_IO_2 = new InputOutput(UNSUCCESSFUL_COMMAND_2,
            UNSUCCESSFUL_COMMAND_OUTPUT,
            false,
            TIME_STAMP_4);

    // not used as part of #getTypicalCommandHistory()
    public static final String SUCCESSFUL_COMMAND_3 = "list -a -s";
    public static final String SUCCESSFUL_COMMAND_OUTPUT_3 = "Listed all appeals\nListed all students\n";

    public static final String SUCCESSFUL_COMMAND_4 = "list -s";
    public static final String SUCCESSFUL_COMMAND_OUTPUT_4 = "Listed all students\n";

    public static final InputOutput SUCCESSFUL_IO_3 = new InputOutput(SUCCESSFUL_COMMAND_3,
            SUCCESSFUL_COMMAND_OUTPUT_3,
            true,
            TIME_STAMP_5);
    public static final InputOutput SUCCESSFUL_IO_4 = new InputOutput(SUCCESSFUL_COMMAND_4,
            SUCCESSFUL_COMMAND_OUTPUT_4,
            true,
            TIME_STAMP_6);

    private TypicalCommandHistory() {} // prevents instantiation

    public static CommandHistory getTypicalCommandHistory() {
        return new CommandHistory(Arrays.asList(SUCCESSFUL_IO_1, SUCCESSFUL_IO_2,
                UNSUCCESSFUL_IO_1, UNSUCCESSFUL_IO_2));
    }
}
