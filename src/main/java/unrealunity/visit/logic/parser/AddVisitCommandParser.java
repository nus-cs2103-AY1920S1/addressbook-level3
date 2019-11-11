package unrealunity.visit.logic.parser;

import static java.util.Objects.requireNonNull;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_VISIT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.commons.exceptions.IllegalValueException;
import unrealunity.visit.logic.commands.AddVisitCommand;
import unrealunity.visit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class AddVisitCommandParser implements Parser<AddVisitCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddVisitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VISIT);

        Index index;
        String date;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            // Take date from '/v' prefix or use current timing for report date.
            date = ParserUtil.parseVisitReport(argMultimap.getValue(PREFIX_VISIT)
                    .orElse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddVisitCommand.MESSAGE_USAGE), ive);
        }

        return new AddVisitCommand(index, date);
    }
}
