package seedu.address.logic.parser.diary;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAGE_TITLE;

import seedu.address.logic.commands.diary.AddPageCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.components.DiaryName;
import seedu.address.model.diary.components.Page;
import seedu.address.model.diary.components.Title;


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
                ArgumentTokenizer.tokenize(args, PREFIX_DIARY_NAME, PREFIX_PAGE_TITLE);

        DiaryName diaryName = ParserUtil.parseDiaryName(argMultimap.getValue(PREFIX_DIARY_NAME).get());
        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_PAGE_TITLE).get());

        Page pageToAdd = new Page(title);
        return new AddPageCommand(pageToAdd, diaryName);
    }

}
