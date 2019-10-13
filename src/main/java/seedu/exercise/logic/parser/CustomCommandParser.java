package seedu.exercise.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_FULL_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_PARAMETER_TYPE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SHORT_NAME;

import seedu.exercise.logic.commands.CustomCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.exercise.CustomProperty;
import seedu.exercise.model.exercise.ParameterType;

/**
 * Parses input arguments and creates a new CustomCommand object.
 */
public class CustomCommandParser implements Parser<CustomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CustomCommand and
     * returns a {@code CustomCommand} object for execution.
     *
     * @param args the arguments for a custom command
     * @return a {@code CustomCommand} object representing the command to be executed
     * @throws ParseException if the user does not conform to the expected format
     */
    public CustomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SHORT_NAME, PREFIX_FULL_NAME,
            PREFIX_PARAMETER_TYPE);

        if (!argMultimap.arePrefixesPresent(PREFIX_SHORT_NAME, PREFIX_FULL_NAME, PREFIX_PARAMETER_TYPE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
        }

        Prefix shortName = ParserUtil.parseShortName(argMultimap.getValue(PREFIX_SHORT_NAME).get());
        String fullName = ParserUtil.parseFullName(argMultimap.getValue(PREFIX_FULL_NAME).get());
        ParameterType paramType = ParserUtil.parseParameterType(argMultimap.getValue(PREFIX_PARAMETER_TYPE).get());

        CustomProperty customProperty = new CustomProperty(shortName, fullName, paramType);

        return new CustomCommand(customProperty);
    }


}
