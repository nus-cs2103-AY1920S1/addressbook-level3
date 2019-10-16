package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;

import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTaskToMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MemberId;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class AddTaskToMemberParser implements Parser<AddTaskToMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetDeadline
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskToMemberCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_INDEX, PREFIX_MEMBER_ID);

        Index taskIndex;
        MemberId memberId;

        try {
            memberId = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());
        } catch (DateTimeParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTaskToMemberCommand.MESSAGE_USAGE), pe);
        }

        try {
            taskIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTaskToMemberCommand.MESSAGE_USAGE), pe);
        }
        return new AddTaskToMemberCommand(taskIndex, memberId);
    }

}
