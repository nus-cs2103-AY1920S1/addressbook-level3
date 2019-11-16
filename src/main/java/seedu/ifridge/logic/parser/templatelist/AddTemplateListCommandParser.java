package seedu.ifridge.logic.parser.templatelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.ifridge.logic.commands.templatelist.AddTemplateListCommand;
import seedu.ifridge.logic.parser.ArgumentMultimap;
import seedu.ifridge.logic.parser.ArgumentTokenizer;
import seedu.ifridge.logic.parser.Parser;
import seedu.ifridge.logic.parser.ParserUtil;
import seedu.ifridge.logic.parser.Prefix;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * Parses input arguments and creates a new AddTemplateItemCommand object
 */
public class AddTemplateListCommandParser implements Parser<AddTemplateListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTemplateListCommand
     * and returns an AddTemplateListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTemplateListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTemplateListCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        UniqueTemplateItems template = new UniqueTemplateItems(name);

        return new AddTemplateListCommand(template);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
