package seedu.address.logic.parser.csvcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.util.List;

import seedu.address.logic.commands.csvcommand.ImportCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses {@code userInput} into an ImportCommand.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    @Override
    public ImportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE_PATH);
        if (!argMultimap.getPreamble().isBlank()
                || !AlfredParserUtil.arePrefixesPresent(argMultimap, PREFIX_FILE_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        List<String> fileNames = argMultimap.getAllValues(PREFIX_FILE_PATH);
        if (fileNames.isEmpty() || fileNames.size() > 2 || this.areInvalid(fileNames)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        try {
            if (fileNames.size() == 1) {
                return new ImportCommand(fileNames.get(0));
            }
            return new ImportCommand(fileNames.get(0), fileNames.get(1));
        } catch (CommandException ce) {
            throw new ParseException(ce.getMessage());
        }
    }

    private boolean areInvalid(List<String> fileNames) {
        return fileNames.stream().anyMatch(fileName -> !fileName.toLowerCase().endsWith(".csv"));
    }

}
