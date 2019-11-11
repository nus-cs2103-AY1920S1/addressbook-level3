package seedu.address.logic.parser.statistics;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.statistics.StatisticsAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class StatisticsCommandParserTest {

    public static final String VALID_DATA = "src/test/data/SampleStatisticsData/ValidSampleStatistics.xlsx";
    public static final String INVALID_FILE_PATH = "invalid file path";

    public static final String VALID_COMMAND = " file/" + VALID_DATA;
    public static final String INVALID_COMMAND = " fil/" + VALID_DATA;
    public static final String INVALID_FILE_PATH_COMMAND = " file/" + INVALID_FILE_PATH;
    public static final String VALID_FILE_NAME_COMMAND = VALID_COMMAND + " print/my sample statistics";
    public static final String INVALID_FILE_NAME_COMMAND = VALID_COMMAND + " print/my/statistics";
    public static final String EMPTY_FILE_NAME_COMMAND = VALID_COMMAND + " print/";

    private final StatisticsCommandParser parser = new StatisticsCommandParser();

    @Test
    public void parseCommand_addValidCommand_success() throws Exception {
        Command command = parser.parse(VALID_COMMAND);
        assertTrue(command instanceof StatisticsAddCommand);
    }

    @Test
    public void parseCommand_saveValidCommand_success() throws Exception {
        Command command = parser.parse(VALID_FILE_NAME_COMMAND);
        assertTrue(command instanceof StatisticsAddCommand);
    }

    @Test
    public void parseCommand_invalidSyntax_throwsException() {
        assertThrows(ParseException.class,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, StatisticsAddCommand.MESSAGE_USAGE), () ->
                parser.parse(INVALID_COMMAND));
    }

    @Test
    public void parseCommand_addInvalidFilePath_throwsException() {
        assertThrows(ParseException.class, Messages.EXCEL_FILE_TYPE_ISSUE, () ->
            parser.parse(INVALID_FILE_PATH_COMMAND));
    }

    @Test
    public void parseCommand_addInvalidFileName_throwsException() {
        assertThrows(ParseException.class, Messages.MESSAGE_SAVE_STATS_FILE_ERROR, () ->
            parser.parse(INVALID_FILE_NAME_COMMAND));
    }

    @Test
    public void parseCommand_addEmptyFileName_throwsException() {
        assertThrows(ParseException.class, Messages.MESSAGE_SAVE_STATS_FILE_ERROR, () ->
            parser.parse(EMPTY_FILE_NAME_COMMAND));
    }

}
