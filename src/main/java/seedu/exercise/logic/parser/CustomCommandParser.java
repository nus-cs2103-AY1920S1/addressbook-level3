package seedu.exercise.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CUSTOM_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_FULL_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_PARAMETER_TYPE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_REMOVE_CUSTOM;

import java.util.Optional;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.CustomAddCommand;
import seedu.exercise.logic.commands.CustomCommand;
import seedu.exercise.logic.commands.CustomRemoveCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.property.custom.CustomProperty;
import seedu.exercise.model.property.custom.ParameterType;

//@@author weihaw08
/**
 * Parses input arguments and creates a new CustomAddCommand object.
 */
public class CustomCommandParser implements Parser<CustomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CustomAddCommand and
     * returns a {@code CustomAddCommand} object for execution.
     *
     * @param args the arguments for a custom command
     * @return a {@code CustomAddCommand} object representing the command to be executed
     * @throws ParseException if the user does not conform to the expected format
     */
    public CustomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CUSTOM_NAME, PREFIX_FULL_NAME,
            PREFIX_PARAMETER_TYPE, PREFIX_REMOVE_CUSTOM, PREFIX_INDEX);

        if (areValidPrefixesForAddCustom(argMultimap)) {
            return parseCustomAdd(argMultimap);
        } else if (isValidPrefixForRemoveCustom(argMultimap)) {
            return parseCustomRemove(argMultimap);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if the prefixes required for adding a new custom property are all present.
     */
    private boolean areAddPrefixesPresent(ArgumentMultimap argMultimap) {
        return argMultimap.arePrefixesPresent(PREFIX_CUSTOM_NAME, PREFIX_FULL_NAME, PREFIX_PARAMETER_TYPE);
    }

    /**
     * Returns true if the prefix required for removing a custom property is present.
     */
    private boolean isRemovePrefixPresent(ArgumentMultimap argMultimap) {
        return argMultimap.arePrefixesPresent(PREFIX_REMOVE_CUSTOM);
    }

    /**
     * Returns true if the only prefixes present are the prefixes required for adding a new custom property.
     */
    private boolean areValidPrefixesForAddCustom(ArgumentMultimap argMultimap) {
        return areAddPrefixesPresent(argMultimap) && !isRemovePrefixPresent(argMultimap)
            && argMultimap.getPreamble().isEmpty();
    }

    /**
     * Returns true if the only prefix present for the custom command is the prefix required for removing a
     * custom property.
     */
    private boolean isValidPrefixForRemoveCustom(ArgumentMultimap argMultimap) {
        return isRemovePrefixPresent(argMultimap)
            && argMultimap.getPreamble().isEmpty()
            && !argMultimap.arePrefixesPresent(PREFIX_CUSTOM_NAME)
            && !argMultimap.arePrefixesPresent(PREFIX_FULL_NAME)
            && !argMultimap.arePrefixesPresent(PREFIX_PARAMETER_TYPE);
    }

    /**
     * Returns a new {@code CustomAddCommand} based on the values in the {@code argMultimap}.
     */
    private CustomAddCommand parseCustomAdd(ArgumentMultimap argMultimap) throws ParseException {
        Prefix prefix = ParserUtil.parsePrefixName(argMultimap.getValue(PREFIX_CUSTOM_NAME).get());
        String fullName = ParserUtil.parseFullName(argMultimap.getValue(PREFIX_FULL_NAME).get());
        ParameterType paramType = ParserUtil.parseParameterType(argMultimap.getValue(PREFIX_PARAMETER_TYPE).get());
        CustomProperty customProperty = new CustomProperty(prefix, fullName, paramType);
        return new CustomAddCommand(customProperty);
    }

    /**
     * Returns a new {@code CustomRemoveCommand} based on the values in the {@code argMultimap}.
     */
    private CustomRemoveCommand parseCustomRemove(ArgumentMultimap argMultimap) throws ParseException {
        String fullName = ParserUtil.parseFullName(argMultimap.getValue(PREFIX_REMOVE_CUSTOM).get());
        Optional<Index> index = Optional.empty();
        if (argMultimap.arePrefixesPresent(PREFIX_INDEX)) {
            index = Optional.of(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get()));
        }
        return new CustomRemoveCommand(fullName, index);
    }
}
