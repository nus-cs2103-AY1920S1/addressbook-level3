package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_INDEX;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_TYPE;
import static seedu.algobase.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.OpenTabCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.ModelType;

/**
 *  Parses input arguments and creates a new OpenTabCommand object.
 */
public class OpenTabCommandParser implements Parser<OpenTabCommand> {
    @Override
    public OpenTabCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_MODEL_TYPE, PREFIX_MODEL_INDEX);

        ModelType modelType;
        if (arePrefixesPresent(argMultimap, PREFIX_MODEL_TYPE)) {
            modelType = ParserUtil.parseModelType(argMultimap.getValue(PREFIX_MODEL_TYPE).get());
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

        return new OpenTabCommand(modelType, index);
    }
}
