package seedu.elisa.logic.parser;

import java.util.regex.Matcher;

import seedu.elisa.logic.commands.Command;
import seedu.elisa.logic.commands.DownCommand;
import seedu.elisa.logic.commands.FindCommand;
import seedu.elisa.logic.commands.GameCommand;
import seedu.elisa.logic.commands.ShowCommand;
import seedu.elisa.logic.commands.SortCommand;
import seedu.elisa.logic.commands.UpCommand;
import seedu.elisa.logic.parser.exceptions.FocusModeException;
import seedu.elisa.logic.parser.exceptions.ParseException;
import seedu.elisa.model.ElisaCommandHistory;

/**
 * The parser that is to be used by ELISA when it is in focus mode.
 * It prevents certain commands from being parsed when it is in focus mode.
 */
public class FocusElisaParser extends ElisaParser {
    public FocusElisaParser(ElisaCommandHistory elisaCommandHistory) {
        super(elisaCommandHistory);
    }

    @Override
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = super.inputToMatcher(userInput);

        final String commandWord = matcher.group("commandWord");
        final String description = matcher.group("description");
        final String flags = " " + matcher.group("flags");

        switch(commandWord.toLowerCase()) {
        case "event":
        case "reminder":
        case SortCommand.COMMAND_WORD:
        case ShowCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD:
        case UpCommand.COMMAND_WORD:
        case DownCommand.COMMAND_WORD:
        case GameCommand.COMMAND_WORD:
            throw new FocusModeException();
        default:
            return super.parseCommandHelper(commandWord, description, flags);
        }
    }
}
