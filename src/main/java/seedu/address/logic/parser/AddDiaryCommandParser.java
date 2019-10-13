package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddDiaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;

/**
 * Parses input arguments and creates a new AddDiaryCommand object
 */
public class AddDiaryCommandParser implements Parser<AddDiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDiaryCommand
     * and returns an AddDiaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDiaryCommand parse(String args) throws ParseException {

        Name name = ParserUtil.parseName(args);

        Diary diary = new Diary(name);

        return new AddDiaryCommand(diary);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
