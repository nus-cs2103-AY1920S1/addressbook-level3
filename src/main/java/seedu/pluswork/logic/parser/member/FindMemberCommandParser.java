package seedu.pluswork.logic.parser.member;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.pluswork.logic.commands.member.FindMemberCommand;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.member.MemberNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMemberCommand object
 */
public class FindMemberCommandParser implements Parser<FindMemberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindTaskCommand
     * and returns a FindTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMemberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMemberCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindMemberCommand(new MemberNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
