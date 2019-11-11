package seedu.pluswork.logic.parser.member;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.commands.member.FindMemberCommand;
import seedu.pluswork.model.member.MemberNameContainsKeywordsPredicate;

public class FindMemberCommandParserTest {
    private FindMemberCommandParser parser = new FindMemberCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() throws CommandException {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindMemberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws CommandException {
        // no leading and trailing whitespaces
        FindMemberCommand expectedFindCommand =
                new FindMemberCommand(new MemberNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }
}
