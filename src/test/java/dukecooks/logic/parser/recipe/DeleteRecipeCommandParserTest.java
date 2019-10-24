package dukecooks.logic.parser.recipe;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.recipe.DeleteRecipeCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.testutil.TypicalIndexes;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteRecipeCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteRecipeCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteRecipeCommandParserTest {

    private DeleteRecipeCommandParser parser = new DeleteRecipeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new DeleteRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRecipeCommand.MESSAGE_USAGE));
    }
}
