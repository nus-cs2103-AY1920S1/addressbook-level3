package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

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
    public void parse_validNameKeywords_returnsFindCommand() {
        BookmarkContainsKeywordsPredicate predicate = new BookmarkContainsKeywordsPredicate(
                Arrays.asList("coding", "algorithm"), Collections.emptyList(), Collections.emptyList());
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, "coding algorithm", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n coding \n \t algorithm  \t", expectedFindCommand);
    }

    @Test
    public void parse_validTagKeywords_returnsFindCommand() {
        BookmarkContainsKeywordsPredicate predicate = new BookmarkContainsKeywordsPredicate(Collections.emptyList(),
                Arrays.asList("coding", "algorithm"), Collections.emptyList());

        FindCommand expectedFindCommand = new FindCommand(predicate);

        // no leading and trailing whitespaces
        String input1 = " " + PREFIX_TAG + "coding" + " " + PREFIX_TAG + "algorithm";
        assertParseSuccess(parser, input1, expectedFindCommand);

        // multiple whitespaces between keywords
        String input2 = " " + PREFIX_TAG + "coding   " + PREFIX_TAG + "    algorithm";
        assertParseSuccess(parser, input2, expectedFindCommand);
    }

    @Test
    public void parse_validFolderKeywords_returnsFindCommand() {
        BookmarkContainsKeywordsPredicate predicate = new BookmarkContainsKeywordsPredicate(Collections.emptyList(),
                Collections.emptyList(), Arrays.asList("coding", "algorithm"));

        FindCommand expectedFindCommand = new FindCommand(predicate);

        // no leading and trailing whitespaces
        String input1 = " " + PREFIX_FOLDER + "coding" + " " + PREFIX_FOLDER + "algorithm";
        assertParseSuccess(parser, input1, expectedFindCommand);

        // multiple whitespaces between keywords
        String input2 = " " + PREFIX_FOLDER + "coding   " + PREFIX_FOLDER + "    algorithm";
        assertParseSuccess(parser, input2, expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        BookmarkContainsKeywordsPredicate predicate = new BookmarkContainsKeywordsPredicate(
                Arrays.asList("name", "url"),
                Arrays.asList("tag"),
                Arrays.asList("folder"));

        FindCommand expectedFindCommand = new FindCommand(predicate);

        // no leading and trailing whitespaces
        String input1 = "name url " + PREFIX_TAG + "tag " + PREFIX_FOLDER + "folder";
        assertParseSuccess(parser, input1, expectedFindCommand);

        // multiple whitespaces between keywords
        String input2 = "  name   url " + PREFIX_TAG + "tag   " + PREFIX_FOLDER + "    folder";
        assertParseSuccess(parser, input2, expectedFindCommand);
    }

}
