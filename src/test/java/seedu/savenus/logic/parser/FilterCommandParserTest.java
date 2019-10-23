package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_LESS_THAN;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_MORE_THAN;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

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
    public void create_proper_command() {
        String legitLine = FIELD_NAME_PRICE + " " + QUANTIFY_LESS_THAN + " 4.20";
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20"};
        FilterCommand expectedCommand = new FilterCommand(Arrays.asList(legitFields));
        assertParseSuccess(parser, legitLine, expectedCommand);
    }

    @Test
    public void create_wrong_command() {
        String falseLine = " ";
        assertParseFailure(parser, falseLine, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                QuantifierParser.NO_ARGUMENTS_USAGE + "\n" + FilterCommand.EXAMPLE_USAGE));
    }
}
