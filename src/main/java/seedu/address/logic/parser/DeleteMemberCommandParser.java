package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MemberId;

public class DeleteMemberCommandParser implements Parser<DeleteMemberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMemberCommand parse(String args) throws ParseException {
        try {
            MemberId id = ParserUtil.parseMemberId(args);
            return new DeleteMemberCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE), pe);
        }
    }
}
