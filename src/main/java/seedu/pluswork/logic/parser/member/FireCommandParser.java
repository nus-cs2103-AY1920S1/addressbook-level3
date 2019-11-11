package seedu.pluswork.logic.parser.member;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.stream.Stream;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.member.FireCommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.member.MemberId;

/**
 * Parses input arguments and creates a new RemoveMemberFromTask parser object
 */
public class FireCommandParser implements Parser<FireCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FireCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_INDEX, PREFIX_MEMBER_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_INDEX, PREFIX_MEMBER_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FireCommand.MESSAGE_USAGE));
        }

        MemberId id = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK_INDEX).get());

        return new FireCommand(index, id);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
