package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveMemberFromTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MemberId;

/**
 * Parses input arguments and creates a new RemoveMemberFromTask parser object
 */
public class RemoveMemberFromTaskParser implements Parser<RemoveMemberFromTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveMemberFromTaskCommand parse(String args) throws ParseException {
        try {
            MemberId id = ParserUtil.parseMemberId(args);
            Index index = ParserUtil.parseIndex(args);

            return new RemoveMemberFromTaskCommand(index, id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveMemberFromTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}
