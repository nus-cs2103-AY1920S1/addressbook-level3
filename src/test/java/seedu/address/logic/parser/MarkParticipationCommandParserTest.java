package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkParticipationCommand;

public class MarkParticipationCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkParticipationCommand.MESSAGE_USAGE);

    private MarkParticipationCommandParser parser = new MarkParticipationCommandParser();

    @Test
    public void parse_null_failure() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        //non-numeric index specified
        assertParseFailure(parser, "one", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndexesSpecified_success() {
        ArrayList<Index> targetIndexes = new ArrayList<>();
        targetIndexes.add(INDEX_FIRST);
        targetIndexes.add(INDEX_SECOND);
        targetIndexes.add(INDEX_THIRD);
        String userInput = "1,2,3";
        MarkParticipationCommand expectedCommand = new MarkParticipationCommand(targetIndexes);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


}
