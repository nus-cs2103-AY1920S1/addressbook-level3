package seedu.address.logic.parser;

import seedu.address.logic.commands.AddPageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.Name;
import seedu.address.model.diary.Page;
import seedu.address.model.diary.Title;


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

        // ASSUMPTION: Diary names are always single word
        String[] argsArr = args.trim().split(" ", 2);

        Name name = ParserUtil.parseName(argsArr[0]);
        Title title = ParserUtil.parseTitle(argsArr[1]);

        Page pageToAdd = new Page(title);
        return new AddPageCommand(pageToAdd, name);
    }

}
