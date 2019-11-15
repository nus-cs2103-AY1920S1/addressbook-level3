package seedu.pluswork.logic.parser.multiline;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.commands.multiline.AddDCommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.member.MemberId;

public class AddDCommandParser implements Parser<AddDCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE, PREFIX_MEMBER_ID);

        MemberId memId = null;
        LocalDateTime deadline = null;

        //parse MemberId & Deadline
        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            if (!arePrefixesPresent(argMultimap, PREFIX_DEADLINE)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDCommand.MESSAGE_USAGE));
            } else {
                deadline = DateTimeUtil.parseDateTime(argMultimap.getValue(PREFIX_DEADLINE).get());
                return new AddDCommand(deadline);
            }
        } else {
            if (!arePrefixesPresent(argMultimap, PREFIX_DEADLINE)
                    || !argMultimap.getPreamble().isEmpty()) {
                memId = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());
                return new AddDCommand(memId);
            } else {
                deadline = DateTimeUtil.parseDateTime(argMultimap.getValue(PREFIX_DEADLINE).get());
                memId = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());
                return new AddDCommand(deadline, memId);
            }
        }

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
