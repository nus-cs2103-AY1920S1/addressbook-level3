package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses the given {@code String} of arguments in the context of RemoveMemberCommand
 * and returns a RemoveMemberCommand object for execution.
 * @throws ParseException if the user input does not conform to the expected formart.
 */
public class RemoveMemberCommandParser implements Parser<RemoveMemberCommand> {
    public RemoveMemberCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveMemberCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveMemberCommand.MESSAGE_USAGE), pe);
        }
    }

}
