package dukecooks.logic.parser.mealplan;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.mealplan.ViewMealPlanCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.testutil.TypicalIndexes;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ViewMealPlanCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ViewMealPlanCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ViewMealPlanCommandParserTest {

    private ViewMealPlanCommandParser parser = new ViewMealPlanCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new ViewMealPlanCommand(TypicalIndexes.INDEX_FIRST_PAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
