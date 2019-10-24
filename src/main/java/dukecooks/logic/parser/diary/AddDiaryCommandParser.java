package dukecooks.logic.parser.diary;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DIARY_NAME;

import java.util.stream.Stream;

import dukecooks.logic.commands.diary.AddDiaryCommand;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.Prefix;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;

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

        DiaryName diaryName = ParserUtil.parseDiaryName(argMultimap.getValue(PREFIX_DIARY_NAME).get());

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
