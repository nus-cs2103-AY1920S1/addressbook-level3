package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.RemoveRoomCommand;
import seedu.address.logic.commands.UndoRemoveRoomCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class RemoveRoomCommandParser implements Parser<ReversibleActionPairCommand> {

    private List<Room> lastShownList;

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
        try {
            Index index = ParserUtil.parseIndex(args);
            Room roomToRemove = ParserUtil.getEntryFromList(lastShownList, index);
            RemoveRoomCommand removeRoomCommand = new RemoveRoomCommand(roomToRemove.getDoctor());
            return new ReversibleActionPairCommand(removeRoomCommand,
                    new UndoRemoveRoomCommand(roomToRemove.getDoctor(), index.getZeroBased()));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRoomCommand.MESSAGE_USAGE), pe);
        }
    }

}
