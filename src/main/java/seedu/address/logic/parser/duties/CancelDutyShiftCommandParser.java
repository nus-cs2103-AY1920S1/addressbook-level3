//@@author woon17
package seedu.address.logic.parser.duties;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_STAFFLIST;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.duties.AddDutyShiftCommand;
import seedu.address.logic.commands.duties.CancelDutyShiftCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;


/**
 * Parses input arguments and creates a new CancelDutyShiftCommand object
 */
public class CancelDutyShiftCommandParser implements Parser<ReversibleActionPairCommand> {

    private List<Event> lastShownList;
    private Model model;

    public CancelDutyShiftCommandParser(Model model) {
        this.model = model;
        this.lastShownList = model.getFilteredDutyShiftList();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CancelDutyShiftCommand
     * and returns an CancelDutyShiftCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        Index index;

        if (!model.isListingAppointmentsOfSingleStaff()) {
            throw new ParseException(MESSAGE_NOT_STAFFLIST);
        }

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelDutyShiftCommand.MESSAGE_USAGE), e);
        }
        Event eventToDelete = ParserUtil.getEntryFromList(lastShownList, index);
        return new ReversibleActionPairCommand(
                new CancelDutyShiftCommand(eventToDelete),
                new AddDutyShiftCommand(eventToDelete));
    }
}
