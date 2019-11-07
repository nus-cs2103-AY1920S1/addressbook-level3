package unrealunity.visit.logic.parser;

import static java.util.Objects.requireNonNull;
import static unrealunity.visit.commons.core.Messages.MESSAGE_EMPTY;
import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_DAYS;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_DAYS;

import unrealunity.visit.commons.exceptions.IllegalValueException;
import unrealunity.visit.logic.commands.DeleteAppointmentCommand;
import unrealunity.visit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteAppointmentCommand} object
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteAppointmentCommand}
     * and returns a {@code DeleteAppointmentCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAYS);

        String description;
        int days;
        try {
            description = argMultimap.getPreamble();
            if (description.trim().isEmpty()) {
                throw new IllegalValueException(MESSAGE_EMPTY);
            }
            days = Integer.parseInt(argMultimap.getValue(PREFIX_DAYS).orElse("-1"));
        } catch (NumberFormatException nfe) {
            throw new ParseException(MESSAGE_INVALID_DAYS);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_EMPTY);
        } catch (Exception ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE), ex);
        }

        return new DeleteAppointmentCommand(description, days);
    }
}
