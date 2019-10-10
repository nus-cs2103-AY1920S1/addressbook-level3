package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_RELEASE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RUNNING_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTOR;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.show.Show;
import seedu.address.model.show.Name;
import seedu.address.model.show.Date;
import seedu.address.model.show.IsWatched;
import seedu.address.model.show.Description;
import seedu.address.model.show.RunningTime;
import seedu.address.model.actor.Actor;
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE_OF_RELEASE, PREFIX_IS_WATCHED,
                        PREFIX_DESCRIPTION, PREFIX_RUNNING_TIME, PREFIX_ACTOR);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE_OF_RELEASE, PREFIX_IS_WATCHED)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Date dateOfRelease = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_OF_RELEASE).get());
        IsWatched isWatched = ParserUtil.parseIsWatched(argMultimap.getValue(PREFIX_IS_WATCHED).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        RunningTime runningTime = ParserUtil.parseRunningTime(argMultimap.getValue(PREFIX_RUNNING_TIME).get());
        Set<Actor> actorList = ParserUtil.parseActors(argMultimap.getAllValues(PREFIX_ACTOR));

        Show person = new Show(name, description, isWatched, dateOfRelease, runningTime, actorList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
