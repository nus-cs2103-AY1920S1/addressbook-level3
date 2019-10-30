package seedu.address.diaryfeature.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_MEMORY;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Date;

import seedu.address.diaryfeature.logic.commands.FindSpecificCommand;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddCommand object
*/

public class FindSpecificCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public Command parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DATE, PREFIX_PLACE, PREFIX_MEMORY);


        Title title;
        Date date;
        Place place;
        Memory memory;
        try {
            title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            place = ParserUtil.parsePlace(argMultimap.getValue(PREFIX_PLACE).get());
            memory = ParserUtil.parseMemory(argMultimap.getValue(PREFIX_MEMORY).get());


        } catch (TitleException | java.text.ParseException ex) {
            return new ErrorCommand(ex);
        }


        DiaryEntry entry = new DiaryEntry(title, date, place, memory);

        return new FindSpecificCommand();
    }
}















