package seedu.address.diaryfeature.logic.parser;

import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_MEMORY;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import seedu.address.diaryfeature.logic.commands.AddCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.DiaryEntryParseException;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws DiaryEntryParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws DiaryEntryParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DATE, PREFIX_PLACE, PREFIX_MEMORY);
        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new DiaryEntryParseException();
        }
        Title title;
        Date date;
        Place place;
        Memory memory;
        try {
            title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            if (argMultimap.getValue(PREFIX_PLACE).isEmpty()) {
                place = new Place("Empty Place");
            } else {
                place = ParserUtil.parsePlace(argMultimap.getValue(PREFIX_PLACE).get());
            }
            if (argMultimap.getValue(PREFIX_MEMORY).isEmpty()) {
                memory = new Memory("Empty Memory");
            } else {
                memory = ParserUtil.parseMemory(argMultimap.getValue(PREFIX_MEMORY).get());
            }
        } catch (NullPointerException | NoSuchElementException err) {
            throw new DiaryEntryParseException();
        }
        DiaryEntry entry = new DiaryEntry(title, date, place, memory);
        return new AddCommand(entry);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
