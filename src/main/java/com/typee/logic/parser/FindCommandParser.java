package com.typee.logic.parser;

import static com.typee.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.typee.logic.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static com.typee.logic.parser.CliSyntax.PREFIX_START_TIME;

import com.typee.logic.commands.FindCommand;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.EngagementPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = getArgumentMultimap(args);
        EngagementPredicate engagementPredicate = new EngagementPredicate();

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            engagementPredicate.setLocation(
                    InteractiveParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()).toString());
        }

        //        if (argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
        //            LocalDateTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        //            LocalDateTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());
        //            engagementPredicate.setTimeSlot(new TimeSlot(startTime, endTime));
        //        }

        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            engagementPredicate.setPriority(
                    InteractiveParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()).toString());
        }

        if (argMultimap.getValue(PREFIX_ATTENDEES).isPresent()) {
            AttendeeList attendeeList = InteractiveParserUtil
                    .parseAttendees(argMultimap.getValue(PREFIX_ATTENDEES).get());
            String attendeeName = attendeeList.getAttendees().get(0).getName().toString();
            engagementPredicate.setAttendees(attendeeName);
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            engagementPredicate.setDescription(
                    InteractiveParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!engagementPredicate.isValid()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(engagementPredicate);
    }

    /**
     * Maps the user entered arguments to {@code FindCommand} prefixes.
     * @param args user input
     * @return {@code ArgumentMultimap} containing a mapping of prefixes to actual arguments.
     */
    private ArgumentMultimap getArgumentMultimap(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_ENGAGEMENT_TYPE, PREFIX_LOCATION, PREFIX_START_TIME,
                PREFIX_END_TIME, PREFIX_PRIORITY, PREFIX_ATTENDEES, PREFIX_DESCRIPTION);
    }
}

