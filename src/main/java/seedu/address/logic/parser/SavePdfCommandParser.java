package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.Optional;

import seedu.address.logic.commands.SavePdfCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SavePdfCommand object
 */
public class SavePdfCommandParser implements Parser<SavePdfCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SavePdfCommand
     * and returns a SavePdfCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SavePdfCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new SavePdfCommand(Optional.empty());
        }

        try {
            Optional<LocalDate> date = Optional.of(ParserUtil.parseDate(args));
            return new SavePdfCommand(date);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SavePdfCommand.MESSAGE_USAGE), pe);
        }
    }
}
