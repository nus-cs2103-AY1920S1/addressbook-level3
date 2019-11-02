package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;

import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            days = Integer.parseInt(argMultimap.getValue(PREFIX_DAYS).orElse("-1"));
        } catch (NumberFormatException nfe) {
            throw new ParseException(MESSAGE_INVALID_DAYS);
        } catch (Exception ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE), ex);
        }

        return new DeleteAppointmentCommand(description, days);
    }
}
