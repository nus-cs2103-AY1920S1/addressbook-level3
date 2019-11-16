package seedu.ifridge.logic.parser.templatelist.template;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.templatelist.template.ListTemplateItemCommand;
import seedu.ifridge.logic.parser.ArgumentMultimap;
import seedu.ifridge.logic.parser.ArgumentTokenizer;
import seedu.ifridge.logic.parser.Parser;
import seedu.ifridge.logic.parser.ParserUtil;
import seedu.ifridge.logic.parser.Prefix;
import seedu.ifridge.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new EditTemplateItemCommand object
 */
public class ListTemplateItemCommandParser implements Parser<ListTemplateItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTemplateItemCommand
     * and returns an EditTemplateItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListTemplateItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListTemplateItemCommand.MESSAGE_USAGE), pe);
        }

        return new ListTemplateItemCommand(index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
