package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMemberToTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MemberId;

/**
 * Parses input arguments and creates a new AddMemberToTask parser object
 */
public class AddMemberToTaskParser implements Parser<AddMemberToTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMemberToTaskCommand parse(String args) throws ParseException {
        try {
            MemberId id = ParserUtil.parseMemberId(args);
            Index index = ParserUtil.parseIndex(args);

            return new AddMemberToTaskCommand(index, id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberToTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}
