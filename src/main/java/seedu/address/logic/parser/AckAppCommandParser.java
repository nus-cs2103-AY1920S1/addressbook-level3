package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_EMPTY;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;

import java.util.List;
import seedu.address.logic.commands.AckAppCommand;
import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.commands.AppointmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.events.Appointment;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AckAppCommandParser implements Parser<AckAppCommand> {

//    public static List<Appointment> list;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AckAppCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_EMPTY, AckAppCommand.MESSAGE_USAGE + " debug empty"));
        }

        if (!isInteger(trimmedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AckAppCommand.MESSAGE_USAGE + " debug non_integer "));
        }

//        if (true) {
//            throw new ParseException("trimmedArgs: " + "||" + trimmedArgs + "||" + "args: " + "||"+args + "||");
//        }

        return new AckAppCommand(Integer.valueOf(trimmedArgs));
    }

    private static boolean isInteger(String strNum) {
        try{
            Integer num = Integer.valueOf(strNum);
            if(num > 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

}
