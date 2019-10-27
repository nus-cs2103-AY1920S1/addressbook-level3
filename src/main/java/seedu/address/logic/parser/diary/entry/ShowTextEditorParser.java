package seedu.address.logic.parser.diary.entry;

import seedu.address.logic.commands.diary.entry.ShowTextEditorCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link ShowTextEditorCommand}.
 */
public class ShowTextEditorParser implements Parser<ShowTextEditorCommand> {
    @Override
    public ShowTextEditorCommand parse(String userInput) throws ParseException {
        return new ShowTextEditorCommand();
    }
}
