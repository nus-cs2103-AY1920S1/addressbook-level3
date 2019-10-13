package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.List;

import mams.logic.commands.ClashCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * temp
 */
public class ClashCommandParser implements Parser<ClashCommand> {

    @Override
    public ClashCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        if (argMultimap.getValueSize(PREFIX_MODULE_CODE) == 2) {
            List<String> moduleCodes = argMultimap.getAllValues(PREFIX_MODULE_CODE);
            return new ClashCommand(moduleCodes.get(0), moduleCodes.get(1));
        } else {
            throw new ParseException(ClashCommand.MESSAGE_INVALID_MODULE_CODE);
        }
    }
}
