package seedu.address.logic.parser;

import seedu.address.logic.commands.RemoveMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of RemoveMemberCommand
 * and returns a RemoveMemberCommand object for execution.
 * @throws ParseException if the user input does not conform to the expected formart.
 */
public class RemoveMemberCommandParser implements Parser<RemoveMemberCommand> {
    public RemoveMemberCommand parse(String args) throws ParseException {
        String memberName = ParserUtil.parseName(args).toString();
        return new RemoveMemberCommand(memberName);
    }
}
