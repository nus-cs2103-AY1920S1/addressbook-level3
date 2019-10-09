package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_EMPTY;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AckAppCommand;
import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.commands.AppointmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.ContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AckAppCommandParser implements Parser<AckAppCommand> {

//    public static List<Appointment> list;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AckAppCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AckAppCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.parsePatientReferenceId(argMultimap.getPreamble());


        return new AckAppCommand(referenceId);
    }
}
