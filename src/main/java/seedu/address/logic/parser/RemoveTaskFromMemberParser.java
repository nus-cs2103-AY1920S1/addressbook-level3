package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveTaskFromMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MemberId;

/**
 * Parses input arguments and creates a new RemoveTaskFromMember parser object
 */
public class RemoveTaskFromMemberParser implements Parser<RemoveTaskFromMemberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTaskFromMemberCommand parse(String args) throws ParseException {
        try {
            MemberId id = ParserUtil.parseMemberId(args);
            Index index = ParserUtil.parseIndex(args);

            return new RemoveTaskFromMemberCommand(index, id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTaskFromMemberCommand.MESSAGE_USAGE), pe);
        }
    }
}
