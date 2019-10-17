package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.StatsCommand;
import seedu.address.model.spending.Date;

public class StatsCommandParserTest {

    private StatsCommandParser parser = new StatsCommandParser();

    @Test
    public void parse_validInputs_success() {
        Date date1 = new Date(VALID_DATE_AMY);
        Date date2 = new Date(VALID_DATE_BOB);

        // 2 valid different dates
        assertParseSuccess(parser, DATE_DESC_AMY + DATE_DESC_BOB ,
            new StatsCommand(date1, date2));

        // 2 valid same dates
        assertParseSuccess(parser, DATE_DESC_AMY + DATE_DESC_AMY ,
            new StatsCommand(date1, date1));
    }

    @Test
    public void parse_noInputsProvided_success() {
       //no dates provided
        StatsCommand expectedCommand = new StatsCommand();
        assertParseSuccess(parser," ", expectedCommand);
    }

    @Test
    public void parse_invalidDates_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC , Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,DATE_DESC_BOB + INVALID_DATE_DESC , Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDateRange_failure() {
        // invalid date
        assertParseFailure(parser,DATE_DESC_BOB + DATE_DESC_AMY ,  String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
    }
}
