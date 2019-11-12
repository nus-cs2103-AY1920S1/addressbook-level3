package seedu.address.logic.parser.cheatsheet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cheatsheet.ViewSpecificCheatSheetTagCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewSpecificCheatSheetTagCommand object
 */
public class ViewSpecificCheatSheetTagCommandParser implements Parser<ViewSpecificCheatSheetTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewFlashcardCommand
     * and returns a ViewFlashcardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewSpecificCheatSheetTagCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewSpecificCheatSheetTagCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSpecificCheatSheetTagCommand.MESSAGE_USAGE), pe);
        }
    }
}
