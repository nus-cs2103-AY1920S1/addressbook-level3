package seedu.pluswork.logic.parser.calendar;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_FILE_PATH;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

import net.fortuna.ical4j.model.Calendar;
import seedu.pluswork.logic.commands.calendar.AddCalendarCommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.calendar.DataAccess;
import seedu.pluswork.model.calendar.FilePath;
import seedu.pluswork.model.member.MemberName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCalendarCommandParser implements Parser<AddCalendarCommand> {

//    private static int count = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCalendarCommand parse(String args) throws ParseException, FileNotFoundException {

//        Sample calendars
//        switch (count % 3) {
//        case 0:
//            args = "add-calendar mn/Gabriel fp/" +
//                    System.getProperty("user.dir") +
//                    "\\data\\gabriel_calendar.ics";
//            break;
//        case 1:
//            args = "add-calendar mn/Abhi fp/" +
//                    System.getProperty("user.dir") +
//                    "\\data\\abhi_calendar.ics";
//            break;
//        case 2:
//            args = "add-calendar mn/Lynn fp/" +
//                    System.getProperty("user.dir") +
//                    "\\data\\lynn_calendar.ics";
//            break;
//        }
//        count ++;


        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEMBER_NAME, PREFIX_FILE_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER_NAME, PREFIX_FILE_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCalendarCommand.MESSAGE_USAGE));
        }

        FilePath filePath = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILE_PATH).get());

        MemberName memberName = ParserUtil.parseMemberName(argMultimap.getValue(PREFIX_MEMBER_NAME).get());
        String calendarStorageFormat = DataAccess.getCalendarStorageFormat(filePath);
        Calendar calendar = ParserUtil.parseCalendar(calendarStorageFormat);

        CalendarWrapper memberCalendar = new CalendarWrapper(memberName, calendar, calendarStorageFormat);

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
