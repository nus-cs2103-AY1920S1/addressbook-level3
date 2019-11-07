package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.SORT_TYPE_ASCENDING;
import static seedu.guilttrip.logic.commands.CommandTestUtil.SORT_TYPE_TIME;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_SORT_SEQUENCE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_SORT_TYPE;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.sortcommands.SortExpenseCommand;
import seedu.guilttrip.logic.parser.sortcommandparsers.SortExpenseCommandParser;
import seedu.guilttrip.model.entry.SortSequence;
import seedu.guilttrip.model.entry.SortType;

public class SortExpenseCommandParserTest {

    private SortExpenseCommandParser parser = new SortExpenseCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        SortSequence sortSeq = new SortSequence(VALID_SORT_SEQUENCE);
        SortType sortType = new SortType(VALID_SORT_TYPE);
        assertParseSuccess(parser, SORT_TYPE_TIME + SORT_TYPE_ASCENDING,
                new SortExpenseCommand(sortType, sortSeq));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortExpenseCommand.MESSAGE_USAGE));
    }
}
