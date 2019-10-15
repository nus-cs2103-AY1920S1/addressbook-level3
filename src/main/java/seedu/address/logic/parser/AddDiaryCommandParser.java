package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIARY_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddDiaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.DiaryName;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIARY_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DIARY_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDiaryCommand.MESSAGE_USAGE));
        }

        DiaryName diaryName = ParserUtil.parseName(argMultimap.getValue(PREFIX_DIARY_NAME).get());

        Diary diary = new Diary(diaryName);

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
