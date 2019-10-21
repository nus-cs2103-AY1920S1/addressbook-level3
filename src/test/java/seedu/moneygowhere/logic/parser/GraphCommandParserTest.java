package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.GraphCommand;
import seedu.moneygowhere.model.spending.Date;

class GraphCommandParserTest {
    private GraphCommandParser parser = new GraphCommandParser();

    @Test
    public void parse_validInputs_success() {
        Date date1 = new Date(VALID_DATE_AMY);
        Date date2 = new Date(VALID_DATE_BOB);

        // 2 valid different dates
        assertParseSuccess(parser, DATE_DESC_AMY + DATE_DESC_BOB ,
            new GraphCommand(date1, date2));

        // 2 valid same dates
        assertParseSuccess(parser, DATE_DESC_AMY + DATE_DESC_AMY ,
            new GraphCommand(date1, date1));
    }

    @Test
    public void parse_noInputsProvided_success() {
        //no dates provided
        GraphCommand expectedCommand = new GraphCommand();
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_invalidDates_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC , Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, DATE_DESC_BOB + INVALID_DATE_DESC , Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDateRange_failure() {
        // start date is later than end date
        assertParseFailure(parser, DATE_DESC_BOB + DATE_DESC_AMY ,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_moreThan2Parameters_failure() {
        // more than 2 date parameters
        assertParseFailure(parser, DATE_DESC_BOB + DATE_DESC_AMY + DATE_DESC_AMY ,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE));
    }

}
