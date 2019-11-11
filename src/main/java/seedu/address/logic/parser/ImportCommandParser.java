//@@author LeowWB

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT_PATH;

import java.util.stream.Stream;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.export.ExportPath;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXPORT_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_EXPORT_PATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }


        ExportPath exportPath = ParserUtil.parseExportPath(argMultimap.getValue(PREFIX_EXPORT_PATH).get());

        return new ImportCommand(exportPath);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
