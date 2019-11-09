package mams.logic.parser;

import static mams.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static mams.logic.parser.CommandParserTestUtil.assertParseFailure;
import static mams.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalIndexes.INDEX_SECOND;
import static mams.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import mams.logic.commands.ViewCommand;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArgs_throwParseException() {
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noRelevantPrefixesPresent_throwParseException() {
        assertParseFailure(parser,
                " t/1 y/1 8/ -s -all",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_parametersSpecifiedWithoutSeparatingSpace_throwParseException() {
        assertParseFailure(parser,
                "a/2s/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser,
                " a/2s/2m/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_allSpecifiedFieldsInvalid_throwsParseException() {

        // negative integers
        assertParseFailure(parser,
                " a/-2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " s/-2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " m/-5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " a/-2 s/-2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " a/-2 s/-2 m/-5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        // nonsense String fields
        assertParseFailure(parser,
                " a/alkfaklm",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " a/eafalm^ s/-eafaklm@ m/1222&&%",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        // trailing invalid characters behind valid indexes
        assertParseFailure(parser,
                " a/2   aedadea   s/2 m/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        // integer overflow
        assertParseFailure(parser,
                " a/2147483648",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " a/222222222222222222222222222",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

    }

    @Test
    void parse_someFieldsValidButAtLeastOneOtherFieldInvalid_throwParseException() {
        // negative indexes
        assertParseFailure(parser,
                " a/-2 s/2 m/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " a/2 s/-2 m/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " a/2 s/2 m/-5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " a/-2 s/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " m/2 s/ada",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " a/2.2 s/2.3 m/1.3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " s/alfkaflkan m/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

    }

    @Test
    void parse_unnecessaryPreamblePresent_throwParseException() {

        // valid format but unnecessary preamble present
        assertParseFailure(parser,
                " preamble s/1 m/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        // invalid format and unnecessary preamble present
        assertParseFailure(parser,
                " preamble s/alfkaflkan m/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        // unnecessary preamble present and no prefixes present
        assertParseFailure(parser,
                " preamble",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_allIndexesSpecifiedValid_parseSuccess() {

        ViewCommand.ViewCommandParameters params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " a/1 m/1 s/1",
                new ViewCommand(params));
        // different ordering of prefixes -> same object
        assertParseSuccess(parser,
                " s/1 a/1 m/1",
                new ViewCommand(params));

        // different index values
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_SECOND);
        params.setStudentIndex(INDEX_THIRD);
        assertParseSuccess(parser,
                " s/3 m/2 a/1",
                new ViewCommand(params));

    }

    @Test
    void parse_oneValidFieldSpecified_parseSuccess() {
        ViewCommand.ViewCommandParameters params;

        // only appeal specified
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " a/1",
                new ViewCommand(params));

        // only module specified
        params = new ViewCommand.ViewCommandParameters();
        params.setModuleIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " m/1",
                new ViewCommand(params));

        // only student specified
        params = new ViewCommand.ViewCommandParameters();
        params.setStudentIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " s/1",
                new ViewCommand(params));
    }

    @Test
    void parse_someValidFieldsSpecified_parseSuccess() {
        ViewCommand.ViewCommandParameters params;

        // only appeal and module specified
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " a/1 m/1",
                new ViewCommand(params));

        // only appeal and student specified
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " a/1 s/1",
                new ViewCommand(params));

        // only student and module specified
        params = new ViewCommand.ViewCommandParameters();
        params.setModuleIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " s/1 m/1",
                new ViewCommand(params));
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        ViewCommand.ViewCommandParameters params;

        // one field repeated
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_SECOND);
        assertParseSuccess(parser,
                " a/1 a/2 a/3 a/4 a/1 a/3 a/2",
                new ViewCommand(params));

        // two fields repeated
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_THIRD);
        assertParseSuccess(parser,
                " a/1 m/2 a/1 m/2 m/3 a/1",
                new ViewCommand(params));

        // three fields repeated
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_THIRD);
        params.setStudentIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " a/1 s/3 s/2 m/2  s/2 a/1  s/2 m/2 s/1 m/3 a/1",
                new ViewCommand(params));
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        ViewCommand.ViewCommandParameters params;

        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " a/aakflm m/1 s/1 a/1",
                new ViewCommand(params));

        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " a/1 m/aeakfam s/1 m/1",
                new ViewCommand(params));

        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_FIRST);
        assertParseSuccess(parser,
                " a/1 m/1 s/afafakfmaklm s/1",
                new ViewCommand(params));
    }
}
