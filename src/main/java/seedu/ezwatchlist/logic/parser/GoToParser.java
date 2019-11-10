package seedu.ezwatchlist.logic.parser;

import seedu.ezwatchlist.logic.commands.GoToCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GoToParser object
 */
public class GoToParser implements Parser<GoToCommand> {

    //userinput is "search" for example
    @Override
    public GoToCommand parse(String args, String currentPanel) throws ParseException {
        String goTo = args.toLowerCase();
        if (goToEqualCurrentPanel(args, currentPanel)) {

        }
        if (goTo.equals("watchlist") || args.equals("1")) {
            return new GoToCommand("1");
        }
        if (goTo.equals("watched") || args.equals("2")) {
            return new GoToCommand("2");
        }
        if (goTo.equals("search") || args.equals("3")) {
            return new GoToCommand("3");
        }
        if (goTo.equals("statistics") || args.equals("4")) {
            return new GoToCommand("4");
        }
        throw new ParseException(GoToCommand.MESSAGE_UNSUCCESSFUL_INPUT);
    }

    /**
     * goToEqualCurrentPanel checks if user input is same as the name of current panel user is currently at.
     * @param args
     * @param currentPanel
     * @return
     */
    public boolean goToEqualCurrentPanel(String args, String currentPanel) {
        String goTo = args.toLowerCase();
        switch (currentPanel) {
        case "watch-list":
            return goTo.equals("watchlist");

        case "watched-list":
            return goTo.equals("watched");

        case "search-list":
            return goTo.equals("search");

        case "statistics tab":
            return goTo.equals("statistics");

        default:
            return false;
        }

    }

}
