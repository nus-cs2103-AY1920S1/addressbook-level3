package seedu.ezwatchlist.logic.parser;

import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.WatchCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;


import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_EPISODES;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_SEASONS;

/**
 * Parses input arguments and creates a new WatchCommand object
 */
public class WatchCommandParser implements Parser<WatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WatchCommand
     * and returns a WatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WatchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NUM_OF_EPISODES, PREFIX_NUM_OF_SEASONS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, WatchCommand.MESSAGE_USAGE), pe);
        }

        int numOfEpisodesWatched;
        int numOfSeasonsWatched;
        boolean isToggle = true;
        WatchCommand.WatchShowDescriptor watchShowDescriptor = new WatchCommand.WatchShowDescriptor();

        if (argMultimap.getValue(PREFIX_NUM_OF_EPISODES).isPresent()) {
            numOfEpisodesWatched = ParserUtil.parseNumOfEpisodesWatched(
                    argMultimap.getValue(PREFIX_NUM_OF_EPISODES).get());
            watchShowDescriptor.setNumOfEpisodesWatched(numOfEpisodesWatched);
            isToggle = false;
        } else {

        }
        /*
        if (argMultimap.getValue(PREFIX_NUM_OF_SEASONS).isPresent()) {
            numOfSeasonsWatched = ParserUtil.parseNumOfEpisodesWatched(
                    argMultimap.getValue(PREFIX_NUM_OF_SEASONS).get());
            watchShowDescriptor.setNumberOfSeasonsWatched(numOfSeasonsWatched);
        }*/

        if(argMultimap.getValue(PREFIX_NUM_OF_EPISODES).isPresent()
                && argMultimap.getValue(PREFIX_NUM_OF_SEASONS).isPresent()) {

        }
        return new WatchCommand(index, watchShowDescriptor, isToggle);
    }
}
