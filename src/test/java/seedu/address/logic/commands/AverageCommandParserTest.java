package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PARAMETER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AverageCommandParser;
import seedu.address.model.record.RecordType;
import seedu.address.model.statistics.AverageType;

public class AverageCommandParserTest {
    private AverageCommandParser parser = new AverageCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, " a/DAILY rt/BLOODSUGAR n/8",
            new AverageCommand(AverageType.DAILY, RecordType.BLOODSUGAR, 8));
        assertParseSuccess(parser, " a/weekly rt/BLOODSUGAR n/3",
            new AverageCommand(AverageType.WEEKLY, RecordType.BLOODSUGAR, 3));
        assertParseSuccess(parser, " a/MONTHLY rt/BLOODSUGAR n/1",
            new AverageCommand(AverageType.MONTHLY, RecordType.BLOODSUGAR, 1));
    }

    @Test
    public void parse_optionalFieldAbsent_success() {

        // missing count prefix
        assertParseSuccess(parser, " a/DAILY rt/BLOODSUGAR",
            new AverageCommand(AverageType.DAILY, RecordType.BLOODSUGAR, 5));
        assertParseSuccess(parser, " a/weekly rt/BLOODSUGAR",
            new AverageCommand(AverageType.WEEKLY, RecordType.BLOODSUGAR, 5));
        assertParseSuccess(parser, " a/MONTHLY rt/BLOODSUGAR",
            new AverageCommand(AverageType.MONTHLY, RecordType.BLOODSUGAR, 5));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AverageCommand.MESSAGE_USAGE);

        // missing average type prefix
        assertParseFailure(parser, " rt/BLOODSUGAR", expectedMessage);

        // missing record type prefix
        assertParseFailure(parser, " a/DAILY", expectedMessage);

        // all prefixes missing prefix
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid average type
        assertParseFailure(parser, " a/YEARLY rt/BLOODSUGAR",
            String.format(MESSAGE_INVALID_PARAMETER, AverageCommand.MESSAGE_USAGE,
                AverageCommand.MESSAGE_INVALID_AVGTYPE));

        // invalid record type
        assertParseFailure(parser, " a/WEEKLY rt/ANYHOW",
            "System does not accommodate such a record type.");

        // invalid count type
        assertParseFailure(parser, " a/WEEKLY rt/BLOODSUGAR n/five",
            String.format(MESSAGE_INVALID_PARAMETER, AverageCommand.MESSAGE_USAGE,
                AverageCommand.MESSAGE_INVALID_COUNT));
    }
}
