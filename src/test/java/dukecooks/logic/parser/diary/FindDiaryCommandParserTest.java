package dukecooks.logic.parser.diary;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.diary.FindDiaryCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.diary.components.DiaryNameContainsKeywordsPredicate;


public class FindDiaryCommandParserTest {

    private FindDiaryCommandParser parser = new FindDiaryCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindDiaryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindDiaryCommand expectedFindDiaryCommand =
                new FindDiaryCommand(new DiaryNameContainsKeywordsPredicate(Arrays.asList("Asian", "Food")));
        CommandParserTestUtil.assertParseSuccess(parser, "Asian Food", expectedFindDiaryCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Asian \n \t Food  \t", expectedFindDiaryCommand);
    }

}
