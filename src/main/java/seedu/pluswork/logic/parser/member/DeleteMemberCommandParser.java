package seedu.pluswork.logic.parser.member;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;

import java.util.stream.Stream;

import seedu.pluswork.logic.commands.member.DeleteMemberCommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.member.MemberId;

/**
 * Parses input arguments and creates a new DeleteMemberCommand object
 */
public class DeleteMemberCommandParser implements Parser<DeleteMemberCommand> {
    public static final String MESSAGE_NO_ID = "Please enter the member ID of the member you want to delete.";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMemberCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEMBER_ID);

        MemberId id;

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER_ID)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE));
        }

        try {
            id = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteMemberCommand(id);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
