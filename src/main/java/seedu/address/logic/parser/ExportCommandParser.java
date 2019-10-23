package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEXES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Parses input arguments and returns a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILENAME, PREFIX_INDEXES);

        if(!argMultimap.getValue(PREFIX_FILENAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        String fileName = argMultimap.getValue(PREFIX_FILENAME).get();
        Optional<Set<Index>> targetIndexes = Optional.empty();

        if (argMultimap.getValue(PREFIX_INDEXES).isPresent()) {
            targetIndexes = Optional.of(
                    ParserUtil.parseIndexes(argMultimap.getAllValues(PREFIX_INDEXES)));
        }

        return new ExportCommand(fileName, targetIndexes);
    }
}
