package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEALID;
import static mams.logic.parser.CliSyntax.PREFIX_MATRICID;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.List;

import mams.logic.commands.ClashAppealCommand;
import mams.logic.commands.ClashCommand;
import mams.logic.commands.ClashModCommand;
import mams.logic.commands.ClashStudentCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * temp
 */
public class ClashCommandParser implements Parser<ClashCommand> {

    @Override
    public ClashCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MATRICID, PREFIX_MODULE_CODE, PREFIX_APPEALID);

        if (argMultimap.getValueSize(PREFIX_MODULE_CODE) == 2) {
            List<String> moduleCodes = argMultimap.getAllValues(PREFIX_MODULE_CODE);
            return new ClashModCommand(moduleCodes.get(0), moduleCodes.get(1));
        } else if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()
                && argMultimap.getValue(PREFIX_MATRICID).isEmpty()
                && argMultimap.getValue(PREFIX_APPEALID).isEmpty()
                && argMultimap.getValueSize(PREFIX_MODULE_CODE) != 2) {
            throw new ParseException(ClashCommand.MESSAGE_INVALID_MODULE_CODE);
        } else if (argMultimap.getValueSize(PREFIX_MATRICID) == 1) {
            return new ClashStudentCommand(argMultimap.getAllValues(PREFIX_MATRICID).get(0));
        } else if (argMultimap.getValue(PREFIX_MODULE_CODE).isEmpty()
                && argMultimap.getValue(PREFIX_MATRICID).isPresent()
                && argMultimap.getValue(PREFIX_APPEALID).isEmpty()
                && argMultimap.getValueSize(PREFIX_MATRICID) != 1) {
            throw new ParseException(ClashCommand.MESSAGE_INVALID_MATRICID);
        } else if (argMultimap.getValueSize(PREFIX_APPEALID) == 1) {
            return new ClashAppealCommand(argMultimap.getAllValues(PREFIX_APPEALID).get(0));
        } else if (argMultimap.getValue(PREFIX_MODULE_CODE).isEmpty()
                && argMultimap.getValue(PREFIX_MATRICID).isEmpty()
                && argMultimap.getValue(PREFIX_APPEALID).isPresent()
                && argMultimap.getValueSize(PREFIX_APPEALID) != 1) {
            throw new ParseException(ClashCommand.MESSAGE_INVALID_APPEALID);
        } else {
            throw new ParseException(ClashCommand.MESSAGE_USAGE);
        }
    }
}
