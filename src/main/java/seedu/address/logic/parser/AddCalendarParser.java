package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.FileNotFoundException;
import java.util.Set;
import java.util.stream.Stream;

import net.fortuna.ical4j.model.Calendar;

import seedu.address.logic.commands.AddCalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.CalendarWrapper;
import seedu.address.model.calendar.DataAccess;
import seedu.address.model.calendar.FilePath;
import seedu.address.model.tag.Tag;
import seedu.address.model.member.MemberName;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCalendarParser implements Parser<AddCalendarCommand> {

    private static int count = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCalendarCommand parse(String args) throws ParseException, FileNotFoundException {
        switch (count % 3) {
        case 0:
            args = "add-calendar mn/Gabriel fp/" +
                    System.getProperty("user.dir") +
                    "\\data\\gabriel_calendar.ics";
            break;
        case 1:
            args = "add-calendar mn/Abhi fp/" +
                    System.getProperty("user.dir") +
                    "\\data\\abhi_calendar.ics";
            break;
        case 2:
            args = "add-calendar mn/Lynn fp/" +
                    System.getProperty("user.dir") +
                    "\\data\\lynn_calendar.ics";
            break;
        }
        //Remove after testing
        count ++;
        //
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEMBER_NAME, PREFIX_FILE_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER_NAME, PREFIX_FILE_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCalendarCommand.MESSAGE_USAGE));
        }

        MemberName memberName = ParserUtil.parseMemberName(argMultimap.getValue(PREFIX_MEMBER_NAME).get());
        FilePath filePath = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILE_PATH).get());

        Calendar calendar = DataAccess.getCalendarFile(filePath);
        CalendarWrapper memberCalendar = new CalendarWrapper(memberName, calendar);

        return new AddCalendarCommand(memberCalendar);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

