package unrealunity.visit.logic.parser;

import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_DELETE_VISIT;
import static unrealunity.visit.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.logic.commands.DeleteVisitCommand;
import unrealunity.visit.testutil.TypicalIndexes;

public class DeleteVisitCommandParserTest {
    private DeleteVisitCommandParser parser = new DeleteVisitCommandParser();
    private final int nonEmptyIdx = 1;

    @Test
    public void parse_reportIndexSpecified_success() {
        // have report index
        Index targetIndex = TypicalIndexes.INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DELETE_VISIT + "1";
        DeleteVisitCommand expectedCommand = new DeleteVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON, nonEmptyIdx);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no report index
        userInput = targetIndex.getOneBased() + " " + PREFIX_DELETE_VISIT;
        expectedCommand = new DeleteVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON, -1);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
