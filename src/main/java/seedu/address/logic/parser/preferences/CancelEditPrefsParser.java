package seedu.address.logic.parser.preferences;

import seedu.address.logic.commands.preferences.CancelEditPrefsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class CancelEditPrefsParser implements Parser<CancelEditPrefsCommand> {
    @Override
    public CancelEditPrefsCommand parse(String userInput) throws ParseException {
        return new CancelEditPrefsCommand();
    }
}
