package seedu.ezwatchlist.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.util.StringUtil;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.Episode;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.RunningTime;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_TYPE = "Type can only be 'movie' or 'tv'.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_NUM_OF_EPISODES = "Number of episodes is an unsigned integer.";
    public static final String MESSAGE_INVALID_NUM_OF_SEASONS = "Number of seasons is a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_INDEX2 = "Index cannot be equal or less than 0, or "
            + "larger than Java Max Value";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format. Must be 'dd/MM/yyyy'.";


    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String type} into a {@code type}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static String parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim().toLowerCase();
        if (!trimmedType.equals("movie") && !trimmedType.equals("tv")) {
            throw new ParseException(MESSAGE_INVALID_TYPE);
        }
        return trimmedType;
    }

    /**
     * Parses a {@code String date} into a {@code date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        String[] dateArr = trimmedDate.split("/");
        String newDate = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
        Date resultDate = new Date(newDate);
        return resultDate;
    }

    /**
     * Parses a {@code String date} into a {@code date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDateAddEditCommand(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            java.util.Date date1 = dateFormat.parse(trimmedDate);
            String output = dateFormat.format(date1);
            return parseDate(output);
        } catch (java.text.ParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    /**
     * Parses a {@code String isWatched} into an {@code IsWatched}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code isWatched} is invalid.
     */
    public static IsWatched parseIsWatched(String isWatched) throws ParseException {
        requireNonNull(isWatched);
        String trimmedIsWatched = isWatched.trim().toLowerCase();
        if (!IsWatched.isValidIsWatched(trimmedIsWatched)) {
            throw new ParseException(IsWatched.MESSAGE_CONSTRAINTS);
        }
        return new IsWatched(trimmedIsWatched);
    }

    /**
     * Parses a {@code String description} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String runningTime} into an {@code RunningTIME}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code runningTime} is invalid.
     */
    public static RunningTime parseRunningTime(String runningTime) throws ParseException {
        requireNonNull(runningTime);
        String trimmedRunningTime = runningTime.trim();
        try {
            if (!RunningTime.isValidRunningTime(Integer.parseInt(trimmedRunningTime))) {
                throw new ParseException(RunningTime.MESSAGE_CONSTRAINTS2);
            }
        } catch (NumberFormatException e) {
            throw new ParseException(RunningTime.MESSAGE_CONSTRAINTS2);
        } catch (ParseException e) {
            throw e;
        }
        return new RunningTime(Integer.parseInt(trimmedRunningTime));
    }

    /**
     * Parses a {@code String actor} into a {@code Actor}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code actor} is invalid.
     */
    public static Actor parseActor(String actor) throws ParseException {
        requireNonNull(actor);
        String trimmedActor = actor.trim();
        if (!Actor.isValidActorName(trimmedActor)) {
            throw new ParseException(Actor.MESSAGE_CONSTRAINTS);
        }
        return new Actor(trimmedActor);
    }

    /**
     * Parses {@code Collection<String> actors} into a {@code Set<Actor>}.
     */
    public static Set<Actor> parseActors(Collection<String> actors) throws ParseException {
        requireNonNull(actors);
        final Set<Actor> actorSet = new HashSet<>();
        for (String actorName : actors) {
            actorSet.add(parseActor(actorName));
        }
        return actorSet;
    }

    /**
     * Parses a {@code String numOfEpisodesWatched} into an {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code numOfEpisodesWatched} is invalid.
     */
    public static int parseNumOfEpisodesWatched(String numOfEpisodesWatched) throws ParseException {
        requireNonNull(numOfEpisodesWatched);
        String trimmedNumOfEpisodesWatched = numOfEpisodesWatched.trim();
        int intNumberOfEpisodesWatched;

        try {
            intNumberOfEpisodesWatched = Integer.parseInt(trimmedNumOfEpisodesWatched);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_NUM_OF_EPISODES);
        }

        if (!Episode.isValidEpisodeNum(Integer.parseInt(numOfEpisodesWatched))) {
            throw new ParseException(MESSAGE_INVALID_NUM_OF_EPISODES);
        }

        return intNumberOfEpisodesWatched;
    }

    /**
     * Parses a {@code String numOfSeasonsWatched} into an {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code numOfSeasonsWatched} is invalid.
     */
    public static int parseNumOfSeasonsWatched(String numOfSeasonsWatched) throws ParseException {
        requireNonNull(numOfSeasonsWatched);
        String trimmedNumOfSeasonsWatched = numOfSeasonsWatched.trim();
        int intNumberOfSeasonsWatched;

        try {
            intNumberOfSeasonsWatched = Integer.parseInt(trimmedNumOfSeasonsWatched);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_NUM_OF_SEASONS);
        }

        if (intNumberOfSeasonsWatched <= 0) {
            throw new ParseException(MESSAGE_INVALID_NUM_OF_SEASONS);
        }

        return intNumberOfSeasonsWatched;
    }

    /**
     * Parses a {@code String args} into an {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code args} is invalid.
     */
    public static int parseAddIndex(String args) throws ParseException {
        requireNonNull(args);
        String trimmedargs = args.trim();
        int index;

        try {
            index = Integer.parseInt(trimmedargs);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        if (index <= 0 || index > Integer.MAX_VALUE) {
            throw new ParseException(MESSAGE_INVALID_INDEX2);
        }
        return index;
    }
}
