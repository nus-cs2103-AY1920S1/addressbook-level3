package unrealunity.visit.logic.parser;

import static java.util.Objects.requireNonNull;
import static unrealunity.visit.commons.core.Messages.MESSAGE_EMPTY;
import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_DAYS;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_DAYS;

import unrealunity.visit.commons.exceptions.IllegalValueException;
import unrealunity.visit.logic.commands.ReminderCommand;
import unrealunity.visit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RemindCommand} object
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemindCommand}
     * and returns a {@code RemindCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAYS);

        String description;
        int days;

        try {
            description = argMultimap.getPreamble();
            if (description == null || description.trim().equals("")) {
                throw new IllegalValueException(MESSAGE_EMPTY);
            }
            days = Integer.parseInt(argMultimap.getValue(PREFIX_DAYS).orElse("7"));
            if (days < 0) {
                throw new NumberFormatException(String.format(MESSAGE_INVALID_DAYS));
            }
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DAYS));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_EMPTY));
        } catch (Exception ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE), ex);
        }

        return new ReminderCommand(description, days);
    }
}
