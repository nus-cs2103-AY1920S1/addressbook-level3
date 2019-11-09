package seedu.ezwatchlist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_EPISODES;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_SEASONS;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.core.messages.Messages;
import seedu.ezwatchlist.logic.commands.WatchCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.ui.MainWindow;

/**
 * Parses input arguments and creates a new WatchCommand object
 */
public class WatchCommandParser implements Parser<WatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WatchCommand
     * and returns a WatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WatchCommand parse(String args, String currentPanel) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NUM_OF_EPISODES, PREFIX_NUM_OF_SEASONS);

        Index index;

        if (currentPanel.equals(MainWindow.SEARCH_TAB) || currentPanel.equals(MainWindow.STATISTICS_TAB)) {
            throw new ParseException(Messages.MESSAGE_INVALID_COMMAND);
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, WatchCommand.MESSAGE_USAGE), pe);
        }

        int numOfEpisodesWatched;
        int numOfSeasonsWatched;
        boolean seasonsArePresent = false;
        boolean episodesArePresent = false;
        WatchCommand.WatchShowDescriptor watchShowDescriptor = new WatchCommand.WatchShowDescriptor();

        if (argMultimap.getValue(PREFIX_NUM_OF_SEASONS).isPresent()) {
            numOfSeasonsWatched =
                    ParserUtil.parseNumOfSeasonsWatched(argMultimap.getValue(PREFIX_NUM_OF_SEASONS).get());
            watchShowDescriptor.setNumOfSeasonsWatched(numOfSeasonsWatched);
            seasonsArePresent = true;
        }
        if (argMultimap.getValue(PREFIX_NUM_OF_EPISODES).isPresent()) {
            numOfEpisodesWatched =
                    ParserUtil.parseNumOfEpisodesWatched(argMultimap.getValue(PREFIX_NUM_OF_EPISODES).get());
            watchShowDescriptor.setNumOfEpisodesWatched(numOfEpisodesWatched);
            episodesArePresent = true;
        }

        return new WatchCommand(index, watchShowDescriptor, seasonsArePresent, episodesArePresent);
    }

}
