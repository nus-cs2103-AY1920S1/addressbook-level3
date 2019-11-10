package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import mams.commons.core.Messages;
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

        if (argMultimap.getValue(PREFIX_MODULE).isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ModCommand.MESSAGE_USAGE_ADD_MOD));
        }

        if (argMultimap.getValue(PREFIX_STUDENT).isEmpty()) {
            throw new ParseException(ModCommand.MESSAGE_MISSING_MATRICID_OR_INDEX);
        }

        if (argMultimap.getAllValues(PREFIX_MODULE).size() > 1) {
            throw new ParseException(ModCommand.MESSAGE_MORE_THAN_ONE_MODULE);
        }

        if (argMultimap.getAllValues(PREFIX_STUDENT).size() > 1) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ModCommand.MESSAGE_MORE_THAN_ONE_IDENTIFIER));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ModCommand.MESSAGE_NO_PREAMBLE
                            + ModCommand.MESSAGE_USAGE_ADD_MOD));
        }

        studentIdentifier = argMultimap.getAllValues(PREFIX_STUDENT).get(0);
        moduleIdentifier = argMultimap.getAllValues(PREFIX_MODULE).get(0);

        if (containUnknownArguments(studentIdentifier, moduleIdentifier)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ModCommand.MESSAGE_UNKNOWN_ARGUMENT_ADDMOD));
        }

        if (!isMatricId(studentIdentifier)) {
            ParserUtil.parseIndex(studentIdentifier);
        }
        if (!isModuleCode(moduleIdentifier)) {
            ParserUtil.parseIndex(moduleIdentifier);
        }

        return new AddModCommand.AddModCommandBuilder(moduleIdentifier,
                studentIdentifier).build();
    }

    private boolean isModuleCode (String moduleIdentifier) {
        return moduleIdentifier.substring(0, 1).toLowerCase().contains("c");
    }

    private boolean isMatricId (String studentIdentifier) {
        return studentIdentifier.substring(0, 1).toLowerCase().contains("a");
    }

    private boolean containUnknownArguments(String studentIdentifier, String moduleIdentifier) {
        return (studentIdentifier.contains("/") || moduleIdentifier.contains("/"));
    }
}
