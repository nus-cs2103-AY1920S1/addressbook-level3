package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.AckAppCommand;
import seedu.address.logic.commands.AckAppCommand.EditEventStatus;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.Status;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AckAppCommandParser implements Parser<AckAppCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AckAppCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID);

        EditEventStatus editEventStatus = new EditEventStatus();

        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            editEventStatus.setReferenceId(
                    ParserUtil.parsePatientReferenceId(argMultimap.getValue(PREFIX_ID).get()));
        }

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AckAppCommand.MESSAGE_USAGE));
        }
        editEventStatus.ackStatus();
        ReferenceId referenceId = ParserUtil.parsePatientReferenceId(argMultimap.getPreamble());


        return new AckAppCommand(referenceId, editEventStatus);
    }
}
