package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;

import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.List;

import mams.logic.commands.AddModCommand;
import mams.logic.commands.ModCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddModCommand object
 */
public class AddModCommandParser implements Parser<AddModCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModCommand
     * and returns an AddModCommand object for execution. (Only argument checking is done here)
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddModCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_MODULE);
        String studentIdentifier;
        String moduleIdentifier;
        String indexForStudent;
        String indexForModule;

        if (argMultimap.getValue(PREFIX_MODULE).isEmpty()) {
            throw new ParseException(ModCommand.MESSAGE_USAGE_ADD_MOD);
        }

        if (argMultimap.getValue(PREFIX_STUDENT).isEmpty()) {
            throw new ParseException(ModCommand.MESSAGE_MISSING_MATRICID_OR_INDEX);
        }

        if (argMultimap.getAllValues(PREFIX_MODULE).size() > 1) {
            throw new ParseException(ModCommand.MESSAGE_MORE_THAN_ONE_MODULE);
        }

        if (argMultimap.getAllValues(PREFIX_STUDENT).size() > 1) {
            throw new ParseException(ModCommand.MESSAGE_MORE_THAN_ONE_IDENTIFIER);
        }

        studentIdentifier = argMultimap.getAllValues(PREFIX_STUDENT).get(0);
        moduleIdentifier = argMultimap.getAllValues(PREFIX_MODULE).get(0);

        if(!isMatricId(studentIdentifier)) {
            ParserUtil.parseIndex(studentIdentifier);
        }
        if (!isModuleCode(moduleIdentifier)) {
            ParserUtil.parseIndex(moduleIdentifier);
        }

        return new AddModCommand.AddModCommandBuilder(studentIdentifier,
                moduleIdentifier).build();
    }

    //@@author: adapted from chensu2436
    /**
     * Returns true if PREFIX_MODULE comes with module codes, not indices.
     * @param moduleIdentifier List of inputs after PREFIX_MODULE
     * @return true if PREFIX_MODULE comes with module codes, not indices
     */
    private boolean isModuleCode(String moduleIdentifier) {
        return moduleIdentifier.toLowerCase().contains("cs");
    }
    //@@author

    private boolean isMatricId(String studentIdentifier) {
        return studentIdentifier.toLowerCase().contains("a");
    }
}
