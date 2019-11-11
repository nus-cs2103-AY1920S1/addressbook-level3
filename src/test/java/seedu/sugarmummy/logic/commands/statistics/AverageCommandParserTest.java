package seedu.sugarmummy.logic.commands.statistics;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_PARAMETER;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_POSSIBLE_AVERAGE_TYPE;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_POSSIBLE_COUNT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_POSSIBLE_RECORD_TYPE;
import static seedu.sugarmummy.logic.commands.statistics.AverageCommand.MESSAGE_INVALID_AVGTYPE;
import static seedu.sugarmummy.logic.commands.statistics.AverageCommand.MESSAGE_INVALID_COUNT;
import static seedu.sugarmummy.logic.commands.statistics.AverageCommand.MESSAGE_USAGE;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.parser.statistics.AverageCommandParser;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.statistics.AverageType;
import seedu.sugarmummy.model.statistics.predicates.RecordContainsRecordTypePredicate;

//@@author chen-xi-cx

public class AverageCommandParserTest {
    private final RecordContainsRecordTypePredicate bloodSugarPredicate =
            new RecordContainsRecordTypePredicate(RecordType.BLOODSUGAR);
    private final RecordContainsRecordTypePredicate bmiPredicate =
            new RecordContainsRecordTypePredicate(RecordType.BMI);
    private AverageCommandParser parser = new AverageCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, " a/DAILY rt/BLOODSUGAR n/8",
                new AverageCommand(bloodSugarPredicate, AverageType.DAILY, RecordType.BLOODSUGAR, 8));
        assertParseSuccess(parser, " a/weekly rt/BMI n/1",
                new AverageCommand(bmiPredicate, AverageType.WEEKLY, RecordType.BMI, 1));
        assertParseSuccess(parser, " a/MONTHLY rt/BLOODSUGAR n/12",
                new AverageCommand(bloodSugarPredicate, AverageType.MONTHLY, RecordType.BLOODSUGAR, 12));
    }

    @Test
    public void parse_optionalFieldAbsent_success() {

        // missing count prefix
        assertParseSuccess(parser, " a/DAILY rt/BMI",
                new AverageCommand(bmiPredicate, AverageType.DAILY, RecordType.BMI, 5));
        assertParseSuccess(parser, " a/weekly rt/BLOODSUGAR",
                new AverageCommand(bloodSugarPredicate, AverageType.WEEKLY, RecordType.BLOODSUGAR, 5));
        assertParseSuccess(parser, " a/MONTHLY rt/BMI",
                new AverageCommand(bmiPredicate, AverageType.MONTHLY, RecordType.BMI, 5));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

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
                String.format(MESSAGE_INVALID_PARAMETER, MESSAGE_INVALID_AVGTYPE, MESSAGE_POSSIBLE_AVERAGE_TYPE));

        // invalid record type
        assertParseFailure(parser, " a/WEEKLY rt/ANYHOW",
                String.format(MESSAGE_INVALID_PARAMETER, AverageCommand.MESSAGE_INVALID_RECORD_TYPE,
                MESSAGE_POSSIBLE_RECORD_TYPE));

        // invalid count type
        assertParseFailure(parser, " a/WEEKLY rt/BLOODSUGAR n/five",
                String.format(MESSAGE_INVALID_PARAMETER, MESSAGE_INVALID_COUNT, MESSAGE_POSSIBLE_COUNT));
        assertParseFailure(parser, " a/WEEKLY rt/BLOODSUGAR n/13",
                String.format(MESSAGE_INVALID_PARAMETER, MESSAGE_INVALID_COUNT, MESSAGE_POSSIBLE_COUNT));
        assertParseFailure(parser, " a/WEEKLY rt/BLOODSUGAR n/0",
                String.format(MESSAGE_INVALID_PARAMETER, MESSAGE_INVALID_COUNT, MESSAGE_POSSIBLE_COUNT));
        assertParseFailure(parser, " a/WEEKLY rt/BLOODSUGAR n/-1",
                String.format(MESSAGE_INVALID_PARAMETER, MESSAGE_INVALID_COUNT, MESSAGE_POSSIBLE_COUNT));
    }
}
