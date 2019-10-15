package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_MORE_THAN;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.FilterCommand;

public class FilterCommandParserTest {
    private FilterCommandParser parser;
    private String properArguments;

    @BeforeEach
    public void set_up() {
        parser = new FilterCommandParser();
        properArguments = FIELD_NAME_PRICE + " " + QUANTIFY_MORE_THAN + " 4.00";

    }

    @Test
    public void parse_emptyArguments_failure() {
        String noArgumentsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.NO_ARGUMENTS_USAGE);
        // Empty Line
        assertParseFailure(parser, "", noArgumentsMessage);

        // Empty Line with Spaces
        assertParseFailure(parser, "      ", noArgumentsMessage);

        // Empty Line with tons of Spaces
        assertParseFailure(parser, "                          ", noArgumentsMessage);
    }
}
