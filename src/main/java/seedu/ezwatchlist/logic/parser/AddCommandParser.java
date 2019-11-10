package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DATE_OF_RELEASE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_RUNNING_TIME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.ezwatchlist.commons.core.messages.Messages;

import seedu.ezwatchlist.logic.commands.AddCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args, String currentPanel) throws ParseException {
        if (currentPanel.equals("search-list")) {
            int index = ParserUtil.parseAddIndex(args);
            return new AddCommand(index);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TYPE, PREFIX_DATE_OF_RELEASE, PREFIX_IS_WATCHED,
                        PREFIX_DESCRIPTION, PREFIX_RUNNING_TIME, PREFIX_ACTOR);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException("hello" + String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());

        Date dateOfRelease;
        IsWatched isWatched;
        Description description;
        RunningTime runningTime;
        Set<Actor> actorList = new HashSet<>();

        if (argMultimap.getValue(PREFIX_DATE_OF_RELEASE).isPresent()) {
            String getDateFromUserInput = argMultimap.getValue(PREFIX_DATE_OF_RELEASE).get();
            dateOfRelease = ParserUtil.parseDateAddEditCommand(getDateFromUserInput);
        } else {
            dateOfRelease = new Date();
        }

        if (argMultimap.getValue(PREFIX_IS_WATCHED).isPresent()) {
            isWatched = ParserUtil.parseIsWatched(argMultimap.getValue(PREFIX_IS_WATCHED).get());
        } else {
            isWatched = new IsWatched("false");
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        } else {
            description = new Description(null);
        }

        if (argMultimap.getValue(PREFIX_RUNNING_TIME).isPresent()) {
            runningTime = ParserUtil.parseRunningTime(argMultimap.getValue(PREFIX_RUNNING_TIME).get());
        } else {
            runningTime = new RunningTime();
        }

        if (argMultimap.getValue(PREFIX_ACTOR).isPresent()) {
            actorList = ParserUtil.parseActors(argMultimap.getAllValues(PREFIX_ACTOR));
        }


        if (type.equals("movie")) {
            Movie movie = new Movie(name, description, isWatched, dateOfRelease, runningTime, actorList);
            return new AddCommand(movie);
        } else { // show type is "tv"
            TvShow tvShow = new TvShow(name, description, isWatched, dateOfRelease, runningTime, actorList,
                    0, 0, new ArrayList<>());
            return new AddCommand(tvShow);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
