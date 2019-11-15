package seedu.pluswork.logic.parser.calendar;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

import seedu.pluswork.logic.commands.calendar.DeleteCalendarCommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.member.MemberName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class DeleteCalendarCommandParser implements Parser<DeleteCalendarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCalendarCommand parse(String args) throws ParseException, FileNotFoundException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEMBER_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCalendarCommand.MESSAGE_USAGE));
        }

        MemberName memberName = ParserUtil.parseMemberName(argMultimap.getValue(PREFIX_MEMBER_NAME).get());
        return new DeleteCalendarCommand(memberName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
