package seedu.address.logic.parser.cvscommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.csvcommand.ExportCommand;
import seedu.address.logic.commands.csvcommand.ExportMentorCommand;
import seedu.address.logic.commands.csvcommand.ExportParticipantCommand;
import seedu.address.logic.commands.csvcommand.ExportTeamCommand;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.csvcommandparser.ExportCommandParser;

public class ExportCommandParserTest {

    private static final String PREFIX_FILE_PATH = " " + CliSyntax.PREFIX_FILE_PATH;
    private static final String PREAMBLE_MENTOR = "mentor";
    private static final String PREAMBLE_PARTICIPANT = "participant";
    private static final String PREAMBLE_TEAM = "team";
    private final Parser parser = new ExportCommandParser();

    @Test
    public void parse_validParametersPassedIn_exportCommandReturned() {
        String filePath = "";
        // Empty file path -> default file path
        assertParseSuccess(
                parser,
                "",
                new ExportCommand(filePath)
        );

        // Pass in file path
        filePath = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREFIX_FILE_PATH + filePath,
                new ExportCommand(filePath)
        );
    }

    @Test
    public void parse_validParametersPassedIn_exportMentorCommandReturned() {
        String filePath = "";
        // Empty file path -> default file path
        assertParseSuccess(
                parser,
                PREAMBLE_MENTOR,
                new ExportMentorCommand(filePath)
        );

        // Pass in file path
        filePath = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREAMBLE_MENTOR + PREFIX_FILE_PATH + filePath,
                new ExportMentorCommand(filePath)
        );
    }

    @Test
    public void parse_validParametersPassedIn_exportParticipantCommandReturned() {
        String filePath = "";
        // Empty file path -> default file path
        assertParseSuccess(
                parser,
                PREAMBLE_PARTICIPANT,
                new ExportParticipantCommand(filePath)
        );

        // Pass in file path
        filePath = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREAMBLE_PARTICIPANT + PREFIX_FILE_PATH + filePath,
                new ExportParticipantCommand(filePath)
        );
    }

    @Test
    public void parse_validParametersPassedIn_exportTeamCommandReturned() {
        String filePath = "";
        // Empty file path -> default file path
        assertParseSuccess(
                parser,
                PREAMBLE_TEAM,
                new ExportTeamCommand(filePath)
        );

        // Pass in file path
        filePath = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREAMBLE_TEAM + PREFIX_FILE_PATH + filePath,
                new ExportTeamCommand(filePath)
        );
    }

    @Test
    public void parse_invalidParametersPassedIn_parseExceptionThrown() {
        // Non-csv file passed
        String invalidFileName = "Alfred.txt";
        assertParseFailure(
                parser,
                PREFIX_FILE_PATH + invalidFileName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE)
        );

        // Invalid preamble/entity
        String invalidPreamble = "Participants";
        String validFileName = "Alfred.csv";
        assertParseFailure(
                parser,
                invalidPreamble + PREFIX_FILE_PATH + validFileName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE)
        );
    }

}
