package com.typee.logic.parser;

import static com.typee.logic.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static com.typee.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.AddCommand;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.engagement.TimeSlot;
import com.typee.model.engagement.exceptions.InvalidTimeException;

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
        ArgumentMultimap argMultimap = getArgumentMultimap(args);

        if (isInvalidMultimap(argMultimap)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        EngagementType engagementType = InteractiveParserUtil
                .parseType(argMultimap.getValue(PREFIX_ENGAGEMENT_TYPE).get());
        LocalDateTime startTime = InteractiveParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        LocalDateTime endTime = InteractiveParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());
        AttendeeList attendees = InteractiveParserUtil.parseAttendees(argMultimap.getValue(PREFIX_ATTENDEES).get());
        Location location = InteractiveParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        String description = InteractiveParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Priority priority = InteractiveParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());

        return makeAddCommand(engagementType, startTime, endTime, attendees, location, description, priority);
    }

    /**
     * Makes a new add command to add an engagement using the given arguments.
     * @param engagementType the type of the engagement.
     * @param startTime start time.
     * @param endTime end time.
     * @param attendees list of people attending the engagement.
     * @param location location of the engagement.
     * @param description description of the engagement.
     * @param priority priority level of the engagement.
     * @return an {@code AddCommand} to add the engagement.
     * @throws ParseException if the time arguments are invalid.
     */
    private AddCommand makeAddCommand(EngagementType engagementType, LocalDateTime startTime, LocalDateTime endTime,
                                      AttendeeList attendees, Location location,
                                      String description, Priority priority) throws ParseException {
        try {
            TimeSlot timeSlot = new TimeSlot(startTime, endTime);
            Engagement engagement = Engagement.of(engagementType, timeSlot,
                    attendees, location, description, priority);

            return new AddCommand(engagement);
        } catch (InvalidTimeException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if the arguments don't correspond to the add command's parameters.
     *
     * @param argMultimap user input arguments mapped by their prefixes.
     * @return true if the command entered is invalid.
     */
    private boolean isInvalidMultimap(ArgumentMultimap argMultimap) {
        return (!arePrefixesPresent(argMultimap, PREFIX_ENGAGEMENT_TYPE,
                PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_ATTENDEES, PREFIX_DESCRIPTION, PREFIX_LOCATION, PREFIX_PRIORITY)
                || !argMultimap.getPreamble().isEmpty());
    }

    /**
     * Maps the user entered arguments to their corresponding prefixes.
     *
     * @param args user input
     * @return {@code ArgumentMultimap} containing a mapping of prefixes to actual arguments.
     */
    private ArgumentMultimap getArgumentMultimap(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_ENGAGEMENT_TYPE,
                PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_ATTENDEES, PREFIX_DESCRIPTION, PREFIX_LOCATION, PREFIX_PRIORITY);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
