//@@author wongsm7
package seedu.address.logic.parser.queue;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.queue.BreakCommand;
import seedu.address.logic.commands.queue.ResumeCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class BreakCommandParser implements Parser<ReversibleActionPairCommand> {
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private Model model;
    private ObservableList<Room> filteredRoomList;
    private Index index;


    public BreakCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        index = ParserUtil.parseIndex(args);

        filteredRoomList = model.getConsultationRoomList();
        if (filteredRoomList.size() < index.getOneBased()) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        Room roomToEdit = filteredRoomList.get(index.getZeroBased());
        Room editedRoom = new Room(roomToEdit.getDoctor(), Optional.empty(), true);
        return new ReversibleActionPairCommand(new BreakCommand(roomToEdit, editedRoom),
                new ResumeCommand(editedRoom, roomToEdit));
    }
}

