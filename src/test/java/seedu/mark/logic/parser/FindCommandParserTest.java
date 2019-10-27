package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.FindCommand;
import seedu.mark.model.predicates.BookmarkContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        BookmarkContainsKeywordsPredicate predicate = new BookmarkContainsKeywordsPredicate();
        predicate.setIdentifierPredicate(Arrays.asList("coding", "algorithm"));
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, "coding algorithm", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n coding \n \t algorithm  \t", expectedFindCommand);
    }

}
