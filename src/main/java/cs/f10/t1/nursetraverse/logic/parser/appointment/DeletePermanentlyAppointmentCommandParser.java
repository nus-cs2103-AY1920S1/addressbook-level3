package cs.f10.t1.nursetraverse.logic.parser.appointment;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs.f10.t1.nursetraverse.logic.parser.ParserUtil.manageIndexParseException;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.appointment.DeletePermanentlyAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.parser.Parser;
import cs.f10.t1.nursetraverse.logic.parser.ParserUtil;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePermanentAppointmentCommand object
 */
public class DeletePermanentlyAppointmentCommandParser implements Parser<DeletePermanentlyAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePermanentAppointmentCommand
     * and returns a DeletePermanentAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePermanentlyAppointmentCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePermanentlyAppointmentCommand(index);
        } catch (ParseException pe) {
            //This will always throw a ParseException
            manageIndexParseException(pe, DeletePermanentlyAppointmentCommand.MESSAGE_USAGE);
            //This is included to ensure compiler complies
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePermanentlyAppointmentCommand.MESSAGE_USAGE), pe);
        }
    }

}
