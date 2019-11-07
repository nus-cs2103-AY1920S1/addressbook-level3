package seedu.guilttrip.logic.parser.remindercommandparsers;

import seedu.guilttrip.logic.commands.remindercommands.TogglePopUpCommand;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Creates instance of TogglePopUp command.
 */
public class TogglePopUpCommandParser implements Parser<TogglePopUpCommand> {
    @Override
    public TogglePopUpCommand parse(String args) throws ParseException {
        boolean willShowPopUp = ParserUtil.parseBool(args);
        return new TogglePopUpCommand(willShowPopUp);
    }
}
