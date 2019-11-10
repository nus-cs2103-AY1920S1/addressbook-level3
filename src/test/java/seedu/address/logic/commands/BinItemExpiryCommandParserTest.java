package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.BINITEMEXPIRY_DAYS_THIRTY;
import static seedu.address.logic.commands.CommandTestUtil.BINITEMEXPIRY_HOURS_THIRTY;
import static seedu.address.logic.commands.CommandTestUtil.BINITEMEXPIRY_INVALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.BINITEMEXPIRY_MINUTES_THIRTY;
import static seedu.address.logic.commands.CommandTestUtil.BINITEMEXPIRY_MONTHS_THIRTY;
import static seedu.address.logic.commands.CommandTestUtil.BINITEMEXPIRY_SECONDS_THIRTY;
import static seedu.address.logic.commands.CommandTestUtil.BINITEMEXPIRY_YEARS_THIRTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_POSITIVE_INT;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.BinItemExpiryCommandParser;

public class BinItemExpiryCommandParserTest {
    private BinItemExpiryCommandParser parser = new BinItemExpiryCommandParser();

    @Test
    public void parse_validAmount_success() {
        int thirty = 30;

        // Valid seconds
        assertParseSuccess(parser, BINITEMEXPIRY_SECONDS_THIRTY, new BinItemExpiryCommand(thirty, ChronoUnit.SECONDS));

        // Valid minutes
        assertParseSuccess(parser, BINITEMEXPIRY_MINUTES_THIRTY, new BinItemExpiryCommand(thirty, ChronoUnit.MINUTES));

        // Valid hours
        assertParseSuccess(parser, BINITEMEXPIRY_HOURS_THIRTY, new BinItemExpiryCommand(thirty, ChronoUnit.HOURS));

        // Valid days
        assertParseSuccess(parser, BINITEMEXPIRY_DAYS_THIRTY, new BinItemExpiryCommand(thirty, ChronoUnit.DAYS));

        // Valid months
        assertParseSuccess(parser, BINITEMEXPIRY_MONTHS_THIRTY, new BinItemExpiryCommand(thirty, ChronoUnit.MONTHS));

        // Valid years
        assertParseSuccess(parser, BINITEMEXPIRY_YEARS_THIRTY, new BinItemExpiryCommand(thirty, ChronoUnit.YEARS));
    }

    @Test
    public void parse_invalidAmount_failure() {
        assertParseFailure(parser, BINITEMEXPIRY_INVALID_AMOUNT, MESSAGE_INVALID_POSITIVE_INT);
    }

}
