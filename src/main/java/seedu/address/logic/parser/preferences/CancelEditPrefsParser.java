package seedu.address.logic.parser.preferences;

import seedu.address.logic.commands.preferences.CancelEditPrefsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments into a {@code CancelEditPrefsCommand}.
 */
public class CancelEditPrefsParser implements Parser<CancelEditPrefsCommand> {
    @Override
    public CancelEditPrefsCommand parse(String userInput) throws ParseException {
        return new CancelEditPrefsCommand();
    }
}
