package dukecooks.logic.parser.diary;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_IMAGE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_DESCRIPTION;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_TITLE;

import java.util.stream.Stream;

import dukecooks.logic.commands.diary.AddPageCommand;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.Prefix;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.Image;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.Title;


/**
 * Parses input arguments and creates a new AddDiaryCommand object
 */
public class AddPageCommandParser implements Parser<AddPageCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPageCommand
     * and returns an AddPageCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPageCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIARY_NAME, PREFIX_PAGE_TITLE,
                        PREFIX_IMAGE, PREFIX_PAGE_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_DIARY_NAME, PREFIX_PAGE_TITLE, PREFIX_IMAGE,
                PREFIX_PAGE_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPageCommand.MESSAGE_USAGE));
        }

        DiaryName diaryName = ParserUtil.parseDiaryName(argMultimap.getValue(PREFIX_DIARY_NAME).get());
        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_PAGE_TITLE).get());
        Image image = ParserUtil.parseImage(argMultimap.getValue(PREFIX_IMAGE).get());
        PageDescription description =
                ParserUtil.parsePageDescription(argMultimap.getValue(PREFIX_PAGE_DESCRIPTION).get());

        Page pageToAdd = new Page(title, description, image);
        return new AddPageCommand(pageToAdd, diaryName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
