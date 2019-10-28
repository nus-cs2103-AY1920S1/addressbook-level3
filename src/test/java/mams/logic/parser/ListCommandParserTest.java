package mams.logic.parser;

import static mams.logic.parser.CommandParserTestUtil.assertParseSuccess;

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
                " -a",
                new ListCommand(true, false, false));

        // list modules only
        assertParseSuccess(parser,
                " -m",
                new ListCommand(false, true, false));

        // list students only
        assertParseSuccess(parser,
                " -s",
                new ListCommand(false, false, true));
    }

    @Test
    public void parse_validArgsCombinationOfParams_parseSuccessful() {
        // list appeals and modules only
        assertParseSuccess(parser,
                " -a -m ",
                new ListCommand(true, true, false));

        // list modules and students only
        assertParseSuccess(parser,
                " -s -m",
                new ListCommand(false, true, true));

        // list students and appeals only
        assertParseSuccess(parser,
                " -s -a",
                new ListCommand(true, false, true));

        // duplicate tags, list student and appeals
        assertParseSuccess(parser,
                " -s -a -s -a -s -a -s -a",
                new ListCommand(true, false, true));

        // duplicate tags, list modules
        assertParseSuccess(parser,
                " -m -m -m -m -m -m -m -m",
                new ListCommand(false, true, false));

        // list all three items
        assertParseSuccess(parser,
                " -s -a -m",
                new ListCommand(true, true, true));
    }
}
