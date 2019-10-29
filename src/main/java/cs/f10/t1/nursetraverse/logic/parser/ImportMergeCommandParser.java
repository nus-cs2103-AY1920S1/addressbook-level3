package cs.f10.t1.nursetraverse.logic.parser;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_FILENAME;
import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.logic.commands.ImportMergeCommand;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;

/**
 * Parses input and returns a new ImportMergeCommand object.
 */
public class ImportMergeCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ImportMergeCommand
     * and returns an ImportMergeCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ImportMergeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILENAME);

        if (!argMultimap.getValue(PREFIX_FILENAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportMergeCommand.MESSAGE_USAGE));
        }

        String fileName = argMultimap.getValue(PREFIX_FILENAME).get();
        return new ImportMergeCommand(fileName);
    }
}
