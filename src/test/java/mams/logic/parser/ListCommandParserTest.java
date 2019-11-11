package mams.logic.parser;

import static mams.logic.parser.CliSyntax.OPTION_APPEAL;
import static mams.logic.parser.CliSyntax.OPTION_MODULE;
import static mams.logic.parser.CliSyntax.OPTION_STUDENT;
import static mams.logic.parser.CommandParserTestUtil.assertParseFailure;
import static mams.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static mams.logic.parser.ListCommandParser.MESSAGE_OPTIONS_NOT_RECOGNIZED;

import org.junit.jupiter.api.Test;

import mams.logic.commands.ListCommand;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_listAllMamsItems() {
        assertParseSuccess(parser,
                "",
                new ListCommand(true, true, true));
    }

    @Test
    public void parse_whiteSpaceEmptyArgs_listAllMamsItems() {
        assertParseSuccess(parser,
                "         ",
                new ListCommand(true, true, true));
    }

    @Test
    public void parse_validArgsOnlyOneParamPresent_listOneItemTypeOnly() {
        // list appeals only
        assertParseSuccess(parser,
                OPTION_APPEAL.toString(),
                new ListCommand(true, false, false));

        // list modules only
        assertParseSuccess(parser,
                OPTION_MODULE.toString(),
                new ListCommand(false, true, false));

        // list students only
        assertParseSuccess(parser,
                OPTION_STUDENT.toString(),
                new ListCommand(false, false, true));
    }

    @Test
    public void parse_validArgsCombinationOfParams_parseSuccessful() {
        // list appeals and modules only
        assertParseSuccess(parser,
                OPTION_MODULE + " " + OPTION_APPEAL,
                new ListCommand(true, true, false));

        // list modules and students only
        assertParseSuccess(parser,
                OPTION_STUDENT + " " + OPTION_MODULE,
                new ListCommand(false, true, true));

        // list students and appeals only
        assertParseSuccess(parser,
                OPTION_APPEAL + " " + OPTION_STUDENT,
                new ListCommand(true, false, true));

        // duplicate tags, list student and appeals
        assertParseSuccess(parser,
                OPTION_STUDENT + " " + OPTION_STUDENT + " " + OPTION_STUDENT + " "
                + OPTION_APPEAL + " " + OPTION_APPEAL + " " + OPTION_STUDENT,
                new ListCommand(true, false, true));

        // duplicate tags, list modules
        assertParseSuccess(parser,
                OPTION_MODULE + " " + OPTION_MODULE + " " + OPTION_MODULE + " " + OPTION_MODULE
                        + " " + OPTION_MODULE,
                new ListCommand(false, true, false));

        // list all three items
        assertParseSuccess(parser,
                OPTION_MODULE + " " + OPTION_STUDENT + " " + OPTION_APPEAL,
                new ListCommand(true, true, true));
    }

    /**
     * Blankspaces should not affect parsing ability of the parser.
     */
    @Test
    public void parse_validParamsWithPresenceOfBlankSpace_parseSuccessful() {

        final String blankSpace = "     ";

        // leading blank-space
        assertParseSuccess(parser,
                blankSpace + OPTION_MODULE + " " + OPTION_STUDENT + " " + OPTION_APPEAL,
                new ListCommand(true, true, true));

        // trailing blank-space
        assertParseSuccess(parser,
                OPTION_MODULE + " " + OPTION_STUDENT + " " + OPTION_APPEAL + blankSpace,
                new ListCommand(true, true, true));

        // leading and trailing blank-space
        assertParseSuccess(parser,
                blankSpace + OPTION_MODULE + " " + OPTION_STUDENT + " " + OPTION_APPEAL + blankSpace,
                new ListCommand(true, true, true));

        // large blank-space between arguments
        assertParseSuccess(parser,
                OPTION_MODULE + " " + blankSpace + OPTION_STUDENT + " " + OPTION_APPEAL,
                new ListCommand(true, true, true));

        // leading and trailing blank-space, as well as all arguments separated by large blank-spaces
        assertParseSuccess(parser,
                blankSpace + OPTION_MODULE + " " + blankSpace + OPTION_STUDENT + " " + blankSpace
                        + OPTION_APPEAL + blankSpace,
                new ListCommand(true, true, true));

        // sanity check: different success combination of params
        // leading and trailing blank-space, as well as all arguments separated by large blank-spaces
        assertParseSuccess(parser,
                blankSpace + OPTION_MODULE + " " + blankSpace + OPTION_STUDENT + " " + blankSpace,
                new ListCommand(false, true, true));
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
                        + ListCommand.MESSAGE_USAGE);

        // valid parameters but not space delimited
        assertParseFailure(parser,
                OPTION_APPEAL.toString() + OPTION_MODULE.toString(),
                String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, OPTION_APPEAL.toString()
                        + OPTION_MODULE.toString())
                        + "\n"
                        + ListCommand.MESSAGE_USAGE);

        // some valid parameters but presence of invalid parameter
        assertParseFailure(parser,
                OPTION_APPEAL.toString() + " " + OPTION_MODULE.toString()
                        + " " + invalidParam1,
                String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, invalidParam1)
                        + "\n"
                        + ListCommand.MESSAGE_USAGE);

        // some valid options but presence of invalid option
        assertParseFailure(parser,
                OPTION_APPEAL.toString() + " " + OPTION_MODULE.toString()
                        + " " + new Option(invalidParam2),
                String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, new Option(invalidParam2))
                        + "\n"
                        + ListCommand.MESSAGE_USAGE);

        // no partial matching is allowed, ie. -ssssss should not be parsed as the valid option -s
        assertParseFailure(parser,
                OPTION_STUDENT.toString() + invalidParam2,
                String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, OPTION_STUDENT.toString()
                        + invalidParam2)
                        + "\n"
                        + ListCommand.MESSAGE_USAGE);
    }
}
