package mams.logic.parser;

import static mams.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static mams.logic.parser.CommandParserTestUtil.assertParseFailure;
import static mams.logic.parser.CommandParserTestUtil.assertParseSuccess;

import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalIndexes.INDEX_SECOND;
import static mams.testutil.TypicalModules.CS1010;
import static mams.testutil.TypicalModules.CS1020;

import org.junit.jupiter.api.Test;

import mams.logic.commands.ClashCommand;

public class ClashCommandParserTest {

    private ClashCommandParser parser = new ClashCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noRelevantPrefixesPresent_throwsParseException() {
        assertParseFailure(parser,
                " t/1 y/1 8/ -s -all",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_someIrrelevantPrefixesPresent_throwsParseException() {

        // irrelevant prefix without input
        assertParseFailure(parser,
                " a/1 t/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        // irrelevant prefix with input
        assertParseFailure(parser,
                " s/1 t/1 y/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreAmple_throwsParseException() {
        assertParseFailure(parser,
                " p/ a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " alice a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyFields_throwsParseException() {
        // single prefix
        assertParseFailure(parser,
                " a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));
        assertParseFailure(parser,
                " m/",
                ClashCommand.MESSAGE_NEED_TWO_MODULES);
        assertParseFailure(parser,
                " s/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        // multiple prefixes
        assertParseFailure(parser,
                " s/ m/ a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        // some empty fields
        assertParseFailure(parser,
                " s/1 m/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFields_throwsParseException() {

        // negative index
        assertParseFailure(parser,
                " a/-2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " s/-2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " m/-5 m/-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        // trailing invalid characters behind valid indexes
        assertParseFailure(parser,
                " a/4 sdfsdferggdsss",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " s/1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        // invalid integer or integer overflow
        assertParseFailure(parser,
                " a/0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " s/222222222222222222222222222",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        // mixture of module code and index
        assertParseFailure(parser,
                " m/1 m/cs1020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " m/cs1020 m/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClashCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_incorrectNumberOfFields_throwsParseException() {

        // checking two or more appeals
        assertParseFailure(parser, " a/1 a/2", ClashCommand.MESSAGE_ONLY_ONE_ITEM_ALLOWED);
        assertParseFailure(parser, " a/1 a/2 a/3 a/4", ClashCommand.MESSAGE_ONLY_ONE_ITEM_ALLOWED);

        // checking two or more students
        assertParseFailure(parser, " s/1 s/2", ClashCommand.MESSAGE_ONLY_ONE_ITEM_ALLOWED);
        assertParseFailure(parser, " s/1 s/2 s/3 s/4", ClashCommand.MESSAGE_ONLY_ONE_ITEM_ALLOWED);

        // only 1 module field
        assertParseFailure(parser, " m/5", ClashCommand.MESSAGE_NEED_TWO_MODULES);
        assertParseFailure(parser, " m/cs1231", ClashCommand.MESSAGE_NEED_TWO_MODULES);

        // more than 2 module fields
        assertParseFailure(parser, " m/cs1231 m/cs2030 m/cs2040", ClashCommand.MESSAGE_NEED_TWO_MODULES);
        assertParseFailure(parser, " m/2 m/3 m/5 m/7", ClashCommand.MESSAGE_NEED_TWO_MODULES);
    }

    @Test
    public void parse_validArgs_returnsClashCommand() {

        ClashCommand.ClashCommandParameters params = new ClashCommand.ClashCommandParameters();

        // valid appeal index
        params.setAppealIndex(INDEX_FIRST);
        assertParseSuccess(parser, " a/1", new ClashCommand(params));

        // valid module indices
        params = new ClashCommand.ClashCommandParameters();
        params.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        assertParseSuccess(parser, " m/1 m/2", new ClashCommand(params));

        // valid module codes
        params = new ClashCommand.ClashCommandParameters();
        params.setModuleCodes(CS1010.getModuleCode(), CS1020.getModuleCode());
        assertParseSuccess(parser, " m/CS1010 m/CS1020", new ClashCommand(params));

        // valid student index
        params = new ClashCommand.ClashCommandParameters();
        params.setStudentIndex(INDEX_FIRST);
        assertParseSuccess(parser, " s/1", new ClashCommand(params));

    }
}
