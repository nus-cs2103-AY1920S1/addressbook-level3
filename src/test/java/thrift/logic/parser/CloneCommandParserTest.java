package thrift.logic.parser;

import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.logic.parser.CommandParserTestUtil.assertParseFailure;
import static thrift.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.CloneCommand;
import thrift.logic.commands.CommandTestUtil;
import thrift.model.clone.Occurrence;
import thrift.testutil.TypicalIndexes;

public class CloneCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloneCommand.MESSAGE_USAGE);

    private CloneCommandParser parser = new CloneCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no index specified
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, CommandTestUtil.OCCURRENCE_TOKEN, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsCloneCommand() {
        // Clone with index only
        assertParseSuccess(parser, CommandTestUtil.INDEX_TOKEN + "2", new CloneCommand(
                TypicalIndexes.INDEX_SECOND_TRANSACTION, new Occurrence("daily", 1)));

        // Clone with index and valid occurrence
        assertParseSuccess(parser, CommandTestUtil.INDEX_TOKEN + "3" + CommandTestUtil.OCCURRENCE_TOKEN
                + "yearly:5", new CloneCommand(TypicalIndexes.INDEX_THIRD_TRANSACTION,
                new Occurrence("yearly", 5)));
        assertParseSuccess(parser, CommandTestUtil.INDEX_TOKEN + "3" + CommandTestUtil.OCCURRENCE_TOKEN
                + "daily:1", new CloneCommand(TypicalIndexes.INDEX_THIRD_TRANSACTION,
                new Occurrence("daily", 1)));
        assertParseSuccess(parser, CommandTestUtil.INDEX_TOKEN + "3" + CommandTestUtil.OCCURRENCE_TOKEN
                + "monthly:12", new CloneCommand(TypicalIndexes.INDEX_THIRD_TRANSACTION,
                new Occurrence("monthly", 12)));
    }

    @Test
    public void parse_invalidCommand_throwsParseException() {
        assertParseFailure(parser, "clon", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as index
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "transaction number two",
                MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1 i/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidOccurrence_throwsParseException() {
        // negative integer value input for occurrence
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1" + CommandTestUtil.OCCURRENCE_TOKEN
                + "daily:-3", MESSAGE_INVALID_FORMAT);

        // input 0 for occurrence
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1" + CommandTestUtil.OCCURRENCE_TOKEN
                + "weekly:0", MESSAGE_INVALID_FORMAT);

        // not integer value input for occurrence
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1" + CommandTestUtil.OCCURRENCE_TOKEN
                + "monthly:three", MESSAGE_INVALID_FORMAT);

        // invalid frequency input for occurrence
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1" + CommandTestUtil.OCCURRENCE_TOKEN
                + "onceeveryday:3", MESSAGE_INVALID_FORMAT);

        // invalid format for occurrence
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1" + CommandTestUtil.OCCURRENCE_TOKEN
                + "once every day for 3 times", MESSAGE_INVALID_FORMAT);

        // number of occurrences beyond allowable limit
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1" + CommandTestUtil.OCCURRENCE_TOKEN
                + "daily:13", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1" + CommandTestUtil.OCCURRENCE_TOKEN
                + "weekly:13", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1" + CommandTestUtil.OCCURRENCE_TOKEN
                + "monthly:13", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1" + CommandTestUtil.OCCURRENCE_TOKEN
                + "yearly:6", MESSAGE_INVALID_FORMAT);
    }
}
