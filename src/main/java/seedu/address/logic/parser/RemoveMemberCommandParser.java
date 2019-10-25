package seedu.address.logic.parser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RemoveMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses the given {@code String} of arguments in the context of RemoveMemberCommand
 * and returns a RemoveMemberCommand object for execution.
 * @throws ParseException if the user input does not conform to the expected formart.
 */
public class RemoveMemberCommandParser implements Parser<RemoveMemberCommand> {
    public RemoveMemberCommand parse(String args) throws ParseException {
        String memberName = ParserUtil.parseName(args).toString();

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new RemoveMemberCommand(memberName, new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
