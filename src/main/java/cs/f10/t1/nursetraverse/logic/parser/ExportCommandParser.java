package cs.f10.t1.nursetraverse.logic.parser;

import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_FILENAME;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_INDEXES;
import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Set;

import cs.f10.t1.nursetraverse.commons.core.Messages;
import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.ExportCommand;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and returns a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILENAME,
                PREFIX_INDEXES);

        if (!argMultimap.getValue(PREFIX_FILENAME).isPresent()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportCommand.MESSAGE_USAGE));
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
