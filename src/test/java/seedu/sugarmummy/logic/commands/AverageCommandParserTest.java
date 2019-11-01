package seedu.sugarmummy.logic.commands;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_PARAMETER;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_RECORD_TYPE;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.parser.AverageCommandParser;
import seedu.sugarmummy.model.record.RecordType;
import seedu.sugarmummy.model.statistics.AverageType;
import seedu.sugarmummy.model.statistics.RecordContainsRecordTypePredicate;

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
        assertParseSuccess(parser, " a/weekly rt/BMI n/3",
                new AverageCommand(bmiPredicate, AverageType.WEEKLY, RecordType.BMI, 3));
        assertParseSuccess(parser, " a/MONTHLY rt/BLOODSUGAR n/1",
                new AverageCommand(bloodSugarPredicate, AverageType.MONTHLY, RecordType.BLOODSUGAR, 1));
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
        assertParseFailure(parser, " a/WEEKLY rt/ANYHOW", MESSAGE_INVALID_RECORD_TYPE);

        // invalid count type
        assertParseFailure(parser, " a/WEEKLY rt/BLOODSUGAR n/five",
                String.format(MESSAGE_INVALID_PARAMETER, AverageCommand.MESSAGE_USAGE,
                        AverageCommand.MESSAGE_INVALID_COUNT));
    }
}
