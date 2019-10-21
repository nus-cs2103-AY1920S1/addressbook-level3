package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_INDEX;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_TAB;
import static seedu.algobase.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.OpenTabCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.ModelEnum;

/**
 *  Parses input arguments and creates a new SwitchTabCommand object.
 */
public class OpenTabCommandParser implements Parser<OpenTabCommand> {
    @Override
    public OpenTabCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_MODEL_TAB, PREFIX_MODEL_INDEX);

        ModelEnum modelEnum;
        if (arePrefixesPresent(argMultimap, PREFIX_MODEL_TAB)) {
            modelEnum = ParserUtil.parseModelTab(argMultimap.getValue(PREFIX_MODEL_TAB).get());
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenTabCommand.MESSAGE_USAGE));
        }

        Index index;
        if (arePrefixesPresent(argMultimap, PREFIX_MODEL_INDEX)) {
            index = ParserUtil.parseModelIndex(argMultimap.getValue(PREFIX_MODEL_INDEX).get());
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenTabCommand.MESSAGE_USAGE));
        }

        return new OpenTabCommand(modelEnum, index);
    }
}
