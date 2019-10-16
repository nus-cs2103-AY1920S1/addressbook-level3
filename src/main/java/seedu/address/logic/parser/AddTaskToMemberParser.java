package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTaskToMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MemberId;

/**
 * Parses input arguments and creates a new AddTaskToMember parser object
 */
public class AddTaskToMemberParser implements Parser<AddTaskToMemberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskToMemberCommand parse(String args) throws ParseException {
        try {
            MemberId id = ParserUtil.parseMemberId(args);
            Index index = ParserUtil.parseIndex(args);

            return new AddTaskToMemberCommand(index, id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskToMemberCommand.MESSAGE_USAGE), pe);
        }
    }
}
