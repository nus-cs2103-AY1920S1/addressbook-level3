package seedu.address.logic.parser;

import seedu.address.logic.commands.loadCommands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Word;

import java.io.File;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORD;

public class ImportCommandParser implements Parser<ImportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_WORD, PREFIX_FILEPATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_WORD, PREFIX_FILEPATH) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        Word word = ParserUtil.parseWord(argMultimap.getValue(PREFIX_WORD).get());
        File directory = ParserUtil.parseFile(argMultimap.getValue(PREFIX_FILEPATH).get());

        return new ImportCommand(word.toString(), directory);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}