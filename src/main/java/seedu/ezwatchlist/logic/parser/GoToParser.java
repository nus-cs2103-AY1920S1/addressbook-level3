package seedu.ezwatchlist.logic.parser;

import seedu.ezwatchlist.logic.commands.GoToCommand;

/**
 * Parses input arguments and creates a new GoToParser object
 */
public class GoToParser implements Parser<GoToCommand> {
    @Override
    public GoToCommand parse(String args, String currentPanel) {
        return new GoToCommand(args);
    }
}
