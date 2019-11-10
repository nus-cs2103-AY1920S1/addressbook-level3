//@@author wongsm7
package seedu.address.logic.parser.queue;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.queue.DequeueCommand;
import seedu.address.logic.commands.queue.UndoDequeueCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DequeueCommandParser implements Parser<ReversibleActionPairCommand> {
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private List<ReferenceId> lastShownList;

    public DequeueCommandParser(Model model) {
        this.lastShownList = model.getQueueList();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        Index index = ParserUtil.parseIndex(args);
        if (lastShownList.size() < index.getOneBased() || index.getOneBased() > lastShownList.size()) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        ReferenceId personToDequeue = ParserUtil.getEntryFromList(lastShownList, index);
        DequeueCommand dequeueCommand = new DequeueCommand(personToDequeue);
        return new ReversibleActionPairCommand(dequeueCommand,
                new UndoDequeueCommand(personToDequeue, index.getZeroBased()));

    }

}
