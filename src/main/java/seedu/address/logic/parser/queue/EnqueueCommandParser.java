//@@author wongsm7
package seedu.address.logic.parser.queue;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.queue.DequeueCommand;
import seedu.address.logic.commands.queue.EnqueueCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReferenceId;

/**
 * Parses input arguments and creates a new ReversibleActionPairCommand object.
 */
public class EnqueueCommandParser implements Parser<ReversibleActionPairCommand> {

    public static final String MESSAGE_ENQUEUE_STAFF =
            "Staff doctors cannot be placed in the patient queue.";

    /**
     * Parses the given {@code String} of arguments in the context of the EnqueueCommand object
     * and returns a ReversibleActionPairCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnqueueCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.lookupPatientReferenceId(
                argMultimap.getPreamble(),
                MESSAGE_ENQUEUE_STAFF);
        return new ReversibleActionPairCommand(new EnqueueCommand(referenceId), new DequeueCommand(referenceId));
    }
}
