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

        EngagementType = ParserUtil.parseType(argMultimap.getValue(PREFIX_ENGAGEMENT_TYPE).get());

        Engagement engagement = Engagement.make();

        return new AddCommand(engagement);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
