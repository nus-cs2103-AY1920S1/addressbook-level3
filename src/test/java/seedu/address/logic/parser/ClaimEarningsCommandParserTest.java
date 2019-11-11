package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClaimEarningsCommand;
import seedu.address.model.earnings.Claim;

public class ClaimEarningsCommandParserTest {

    private ClaimEarningsCommandParser parser = new ClaimEarningsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClaimEarningsCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_invalidClaim_throwsParseException() {
        assertParseFailure(parser, "3 claim/done",
                String.format(Claim.MESSAGE_CONSTRAINTS));

        assertParseFailure(parser, "-4",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClaimEarningsCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "3 claim/finished",
                String.format(Claim.MESSAGE_CONSTRAINTS));

        assertParseFailure(parser, "3 claim/waiting",
                String.format(Claim.MESSAGE_CONSTRAINTS));
    }
}
