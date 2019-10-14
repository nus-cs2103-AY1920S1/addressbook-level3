package com.typee.logic.parser;

import static com.typee.logic.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static com.typee.logic.parser.CliSyntax.PREFIX_START_TIME;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.AddCommand;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.person.Name;
import com.typee.model.person.Person;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                ArgumentTokenizer.tokenize(args, PREFIX_ENGAGEMENT_TYPE,
                        PREFIX_START_TIME, PREFIX_END_TIME,
                        PREFIX_ATTENDEES, PREFIX_DESCRIPTION, PREFIX_LOCATION, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_ENGAGEMENT_TYPE,
                PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_ATTENDEES, PREFIX_DESCRIPTION, PREFIX_LOCATION, PREFIX_PRIORITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        EngagementType engagementType = ParserUtil.parseType(argMultimap.getValue(PREFIX_ENGAGEMENT_TYPE).get());
        LocalDateTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        LocalDateTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());
        List<Person> attendees = parseAttendees(argMultimap.getValue(PREFIX_ATTENDEES).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());

        Engagement engagement = Engagement.of(engagementType, startTime, endTime,
                attendees, location, description, priority);

        return new AddCommand(engagement);
    }

    private List<Person> parseAttendees(String attendees) {
        List<Person> attendeesList = Arrays.stream(attendees.split(","))
                .map(name -> name.trim())
                .map(name -> new Person(ParserUtil.parseNameDeterministic(name)))
                .filter(name -> name != null)
                .collect(Collectors.toList());
        return attendeesList;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
