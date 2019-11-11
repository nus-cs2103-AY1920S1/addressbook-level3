//@@author wongsm7
package seedu.address.logic.parser.queue;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.queue.AddConsultationRoomCommand;
import seedu.address.logic.commands.queue.RemoveRoomCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.model.person.Person;
import seedu.address.model.queue.Room;


/**
 * Parses input arguments and creates a new ReversibleActionPairCommand object.
 */
public class AddConsultationRoomCommandParser implements Parser<ReversibleActionPairCommand> {

    private Index index;
    private List<Person> lastShownList;

    public AddConsultationRoomCommandParser(Model model) {
        this.lastShownList = model.getFilteredStaffList();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddConsultationRoomCommand object
     * and returns a ReversibleActionPairCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddConsultationRoomCommand.MESSAGE_USAGE));
        }
        ReferenceId referenceId = ParserUtil.getEntryFromList(lastShownList, index).getReferenceId();
        Room roomToAdd = new Room(referenceId);
        return new ReversibleActionPairCommand(new AddConsultationRoomCommand(roomToAdd),
                new RemoveRoomCommand(roomToAdd));
    }
}
