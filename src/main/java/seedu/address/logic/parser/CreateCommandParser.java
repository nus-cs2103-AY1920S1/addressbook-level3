package seedu.address.logic.parser;

import seedu.address.logic.commands.loadCommands.CreateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wordbank.WordBank;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CreateCommandParser implements Parser<CreateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns an CreateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateCommand parse(String name) throws ParseException {
        String trimmedArgs = name.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
        }
        WordBank wordBank = new WordBank(trimmedArgs);
        return new CreateCommand(wordBank);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
