package cs.f10.t1.nursetraverse.logic.parser.appointment;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs.f10.t1.nursetraverse.logic.parser.ParserUtil.manageIndexParseException;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.appointment.DeletePermanentAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.parser.Parser;
import cs.f10.t1.nursetraverse.logic.parser.ParserUtil;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object
 */
public class DeletePermanentAppointmentCommandParser implements Parser<DeletePermanentAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePermanentAppointmentCommand
     * and returns a DeletePermanentAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePermanentAppointmentCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePermanentAppointmentCommand(index);
        } catch (ParseException pe) {
            //This will always throw a ParseException
            manageIndexParseException(pe, DeletePermanentAppointmentCommand.MESSAGE_USAGE);
            //This is included to ensure compiler complies
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePermanentAppointmentCommand.MESSAGE_USAGE), pe);
        }
    }

}
