package mams.logic.parser;

import static mams.logic.parser.CommandParserTestUtil.assertParseFailure;
import static mams.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static mams.logic.parser.HistoryCommandParser.MESSAGE_OPTIONS_NOT_RECOGNIZED;
import static mams.logic.parser.HistoryCommandParser.OPTION_HIDE_OUTPUT;
import static mams.logic.parser.HistoryCommandParser.OPTION_HIDE_UNSUCCESSFUL;

import org.junit.jupiter.api.Test;

import mams.logic.commands.HistoryCommand;
import mams.logic.history.HistoryFilterSettings;

public class HistoryCommandParserTest {

    private HistoryCommandParser parser = new HistoryCommandParser();

    @Test
    public void parse_noParameters_returnsHistoryCommand() {
        assertParseSuccess(parser, " ", new HistoryCommand(false, HistoryFilterSettings.SHOW_ALL));
    }

    @Test
    public void parse_hideOutput_returnsHistoryCommand() {
        assertParseSuccess(parser,
                " " + OPTION_HIDE_OUTPUT.toString(),
                new HistoryCommand(true, HistoryFilterSettings.SHOW_ALL));
    }

    @Test
    public void parse_hideUnsuccessfulCommands_returnsHistoryCommand() {
        assertParseSuccess(parser,
                " " + OPTION_HIDE_UNSUCCESSFUL.toString(),
                new HistoryCommand(false, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL));
    }

    @Test
    public void parse_hideUnsuccessfulCommandsAndHideOutput_returnsHistoryCommand() {
        assertParseSuccess(parser,
                " " + OPTION_HIDE_UNSUCCESSFUL.toString()
                        + " " + OPTION_HIDE_OUTPUT.toString(),
                new HistoryCommand(true, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL));
    }

    /**
     * Blankspaces should not affect parsing ability of the parser.
     */
    @Test
    public void parse_validParamsWithPresenceOfBlankSpace_parseSuccessful() {

        final String blankSpace = "     ";

        // leading blank-space
        assertParseSuccess(parser, blankSpace
                + " " + OPTION_HIDE_UNSUCCESSFUL.toString()
                        + " " + OPTION_HIDE_OUTPUT.toString(),
                new HistoryCommand(true, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL));

        // trailing blank-space
        assertParseSuccess(parser, " " + OPTION_HIDE_UNSUCCESSFUL.toString()
                        + " " + OPTION_HIDE_OUTPUT.toString() + blankSpace,
                new HistoryCommand(true, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL));

        // leading and trailing blank-spaces
        assertParseSuccess(parser, blankSpace + " " + OPTION_HIDE_UNSUCCESSFUL.toString()
                        + " " + OPTION_HIDE_OUTPUT.toString() + blankSpace,
                new HistoryCommand(true, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL));

        // large blank-space between arguments
        assertParseSuccess(parser, OPTION_HIDE_UNSUCCESSFUL.toString()
                        + " " + blankSpace + OPTION_HIDE_OUTPUT.toString(),
                new HistoryCommand(true, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL));

        // sanity check: different success combination of params
        // leading and trailing blank-space, as well as all arguments separated by large blank-spaces
        assertParseSuccess(parser, blankSpace + OPTION_HIDE_UNSUCCESSFUL.toString()
                        + " " + blankSpace,
                new HistoryCommand(false, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL));
    }

    @Test
    public void parse_someInvalidArgumentsProvided_throwsParseException() {
        final String invalidParam1 = "lkemfwal";
        final String invalidParam2 = "invalid";

        // all invalid parameters
        assertParseFailure(parser,
                invalidParam1 + " " + invalidParam2,
                String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, invalidParam1 + " " + invalidParam2)
                        + "\n"
                        + HistoryCommand.MESSAGE_USAGE);

        // valid parameters but not space delimited
        assertParseFailure(parser,
                OPTION_HIDE_OUTPUT.toString() + OPTION_HIDE_UNSUCCESSFUL.toString(),
                String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, OPTION_HIDE_OUTPUT.toString()
                        + OPTION_HIDE_UNSUCCESSFUL.toString())
                        + "\n"
                        + HistoryCommand.MESSAGE_USAGE);

        // some valid parameters but presence of invalid parameter
        assertParseFailure(parser,
                OPTION_HIDE_OUTPUT.toString() + " " + OPTION_HIDE_UNSUCCESSFUL.toString()
                        + " " + invalidParam2,
                String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, invalidParam2)
                        + "\n"
                        + HistoryCommand.MESSAGE_USAGE);

        // some valid options but presence of invalid option
        assertParseFailure(parser,
                OPTION_HIDE_OUTPUT.toString() + " " + OPTION_HIDE_UNSUCCESSFUL.toString()
                        + " " + new Option(invalidParam1),
                String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, new Option(invalidParam1).toString())
                        + "\n"
                        + HistoryCommand.MESSAGE_USAGE);

        // no partial matching is allowed, eg. -oinvalid should not be parsed as the valid option -o
        assertParseFailure(parser,
                OPTION_HIDE_OUTPUT.toString() + invalidParam2,
                String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, OPTION_HIDE_OUTPUT.toString()
                        + invalidParam2)
                        + "\n"
                        + HistoryCommand.MESSAGE_USAGE);
    }

}
