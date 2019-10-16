package seedu.address.logic.parser.templateList.template;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.templateList.template.DeleteTemplateItemCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTemplateItemCommand object
 */
public class DeleteTemplateItemCommandParser implements Parser<DeleteTemplateItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTemplateItemCommand
     * and returns a DeleteTemplateItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTemplateItemCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_ITEM_INDEX) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTemplateItemCommand.MESSAGE_USAGE));
        }

        Index templateIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        Index itemIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ITEM_INDEX).get());

        return new DeleteTemplateItemCommand(templateIndex, itemIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
