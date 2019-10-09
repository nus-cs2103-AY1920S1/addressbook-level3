package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.TabCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

public class TabCommandParser implements Parser<TabCommand> {

    public TabCommand parse(String args) throws ParseException {
        requireNonNull(args);
        TabCommand.Tab tab;
        try {
            tab = ParserUtil.parseTab(args);
        } catch (ParseException pe) {
            throw pe;
        }

        return new TabCommand(tab);
    }
}
