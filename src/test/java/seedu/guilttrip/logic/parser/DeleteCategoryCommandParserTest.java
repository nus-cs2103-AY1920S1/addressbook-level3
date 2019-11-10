package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_NAME_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_TYPE_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.INVALID_CATEGORY_TYPE_BUDGET;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_CATEGORY_TYPE_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_OLD_CATEGORY_NAME;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.deletecommands.DeleteCategoryCommand;
import seedu.guilttrip.logic.parser.deletecommandparsers.DeleteCategoryCommandParser;
import seedu.guilttrip.model.entry.Category;

public class DeleteCategoryCommandParserTest {

    private DeleteCategoryCommandParser parser = new DeleteCategoryCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, CATEGORY_TYPE_EXPENSE + CATEGORY_NAME_EXPENSE,
                new DeleteCategoryCommand(new Category(VALID_OLD_CATEGORY_NAME, VALID_CATEGORY_TYPE_EXPENSE)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //Invalid Category Type
        assertParseFailure(parser, INVALID_CATEGORY_TYPE_BUDGET + CATEGORY_NAME_EXPENSE,
                Category.MESSAGE_CONSTRAINTS_TYPE);
    }
}
