package dukecooks.logic.parser.health;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.function.Predicate;
import java.util.stream.Stream;

import dukecooks.logic.commands.ListCommand;
import dukecooks.logic.commands.health.ListHealthByTypeCommand;
import dukecooks.logic.commands.health.ListHealthCommand;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.Prefix;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Type;

/**
 * Parses input arguments and creates a new AddRecipeCommand object
 */
public class ListRecordCommandParser implements Parser<ListCommand> {

    private Predicate<Record> predicate;

    /**
     * Parses the given {@code String} of arguments in the context of the EditRecordCommand
     * and returns an EditRecordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListHealthCommand();
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListHealthCommand.MESSAGE_USAGE));
        }

        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        predicate = x -> x.getType().equals(type);
        ListHealthByTypeCommand.setCurrentTypeView(type);
        return new ListHealthByTypeCommand(predicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
