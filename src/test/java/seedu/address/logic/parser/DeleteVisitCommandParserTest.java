package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_VISIT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteVisitCommand;

public class DeleteVisitCommandParserTest {
    private DeleteVisitCommandParser parser = new DeleteVisitCommandParser();
    private final int nonEmptyIdx = 1;

    @Test
    public void parse_reportIndexSpecified_success() {
        // have report index
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DELETE_VISIT + "1";
        DeleteVisitCommand expectedCommand = new DeleteVisitCommand(INDEX_FIRST_PERSON, nonEmptyIdx);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no report index
        userInput = targetIndex.getOneBased() + " " + PREFIX_DELETE_VISIT;
        expectedCommand = new DeleteVisitCommand(INDEX_FIRST_PERSON, -1);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
