//@@author wongsm7
package seedu.address.logic.parser.queue;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.queue.AddConsultationRoomCommand;
import seedu.address.logic.commands.queue.RemoveRoomCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class RemoveRoomCommandParser implements Parser<ReversibleActionPairCommand> {
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private List<Room> lastShownList;
    private Index index;

    public RemoveRoomCommandParser(Model model) {
        this.lastShownList = model.getConsultationRoomList();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        index = ParserUtil.parseIndex(args);

        if (lastShownList.size() < index.getOneBased()) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        Room roomToRemove = ParserUtil.getEntryFromList(lastShownList, index);
        return new ReversibleActionPairCommand(new RemoveRoomCommand(roomToRemove),
                new AddConsultationRoomCommand(roomToRemove));

    }

}
