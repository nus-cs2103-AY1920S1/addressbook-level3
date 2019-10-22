package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.List;

import mams.logic.commands.ClashAppealCommand;
import mams.logic.commands.ClashCommand;
import mams.logic.commands.ClashModCommand;
import mams.logic.commands.ClashStudentCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClashCommand object
 */
public class ClashCommandParser implements Parser<ClashCommand> {

    @Override
    public ClashCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_MODULE, PREFIX_APPEAL);

        if (argMultimap.getValueSize(PREFIX_MODULE) == 2) {
            List<String> moduleCodes = argMultimap.getAllValues(PREFIX_MODULE);
            return new ClashModCommand(moduleCodes.get(0), moduleCodes.get(1));
        } else if (argMultimap.getValue(PREFIX_MODULE).isPresent()
                && argMultimap.getValue(PREFIX_STUDENT).isEmpty()
                && argMultimap.getValue(PREFIX_APPEAL).isEmpty()
                && argMultimap.getValueSize(PREFIX_MODULE) != 2) {
            throw new ParseException(ClashCommand.MESSAGE_INVALID_MODULE_CODE);
        } else if (argMultimap.getValueSize(PREFIX_STUDENT) == 1) {
            return new ClashStudentCommand(argMultimap.getAllValues(PREFIX_STUDENT).get(0));
        } else if (argMultimap.getValue(PREFIX_MODULE).isEmpty()
                && argMultimap.getValue(PREFIX_STUDENT).isPresent()
                && argMultimap.getValue(PREFIX_APPEAL).isEmpty()
                && argMultimap.getValueSize(PREFIX_STUDENT) != 1) {
            throw new ParseException(ClashCommand.MESSAGE_INVALID_MATRICID);
        } else if (argMultimap.getValueSize(PREFIX_APPEAL) == 1) {
            return new ClashAppealCommand(argMultimap.getAllValues(PREFIX_APPEAL).get(0));
        } else if (argMultimap.getValue(PREFIX_MODULE).isEmpty()
                && argMultimap.getValue(PREFIX_STUDENT).isEmpty()
                && argMultimap.getValue(PREFIX_APPEAL).isPresent()
                && argMultimap.getValueSize(PREFIX_APPEAL) != 1) {
            throw new ParseException(ClashCommand.MESSAGE_INVALID_APPEALID);
        } else {
            throw new ParseException(ClashCommand.MESSAGE_USAGE);
        }
    }
}
