//@@author shutingy reused

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCategoryCommand;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;


public class ListCategoryCommandParserTest {

    private FindCategoryCommandParser parser = new FindCategoryCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListCategoryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCategoryCommand() {
        // no leading and trailing whitespaces
        ListCategoryCommand expectedListCategoryCommand =
                new ListCategoryCommand(new CategoryContainsAnyKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedListCategoryCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedListCategoryCommand);
    }

}
