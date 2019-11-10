//@@author wongsm7
package seedu.address.logic.parser.queue;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.queue.BreakCommand;
import seedu.address.logic.commands.queue.ResumeCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Parses input arguments and creates a new EnqueueCommand object
 */
public class ResumeCommandParser {
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private Model model;
    private ObservableList<Room> filteredRoomList;
    private Index index;


    public ResumeCommandParser(Model model) {
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
        Room editedRoom = new Room(roomToEdit.getDoctor(), roomToEdit.getCurrentPatient(), false);
        return new ReversibleActionPairCommand(new ResumeCommand(roomToEdit, editedRoom),
                new BreakCommand(editedRoom, roomToEdit));
    }
}
