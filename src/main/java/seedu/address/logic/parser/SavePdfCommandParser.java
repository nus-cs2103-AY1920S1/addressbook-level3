package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PDF;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PDF, PREFIX_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_PDF)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SavePdfCommand.MESSAGE_USAGE));
        }

        String documentType = ParserUtil.parsePdfDocumentType(argMultimap.getValue(PREFIX_PDF).get());

        Optional<LocalDate> optionalLocalDate;
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            optionalLocalDate = Optional.of(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATETIME).get()));
        } else {
            optionalLocalDate = Optional.empty();
        }

        return new SavePdfCommand(documentType, optionalLocalDate);
    }
}
