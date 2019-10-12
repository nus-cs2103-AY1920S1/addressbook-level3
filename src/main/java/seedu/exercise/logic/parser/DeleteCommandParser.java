package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.stream.Stream;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.DeleteCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.DeleteRegimeCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.regime.RegimeName;

/**
 * Parses input arguments and creates a new DeleteExerciseCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_NAME, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());

        if (category.equals("exercise")) {
            return parseExercise(argMultimap);
        }

        if (category.equals("regime")) {
            return parseRegime(argMultimap);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Parses arguments and returns DeleteExerciseCommand for execution
     */
    private DeleteExerciseCommand parseExercise(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteExerciseCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        return new DeleteExerciseCommand(index);
    }

    /**
     * Parses arguments and returns DeleteRegimeCommand for execution
     */
    private DeleteRegimeCommand parseRegime(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteRegimeCommand.MESSAGE_USAGE));
        }

        RegimeName name = ParserUtil.parseRegimeName(argMultimap.getValue(PREFIX_NAME).get());

        // index present, delete exercise in regime
        if (arePrefixesPresent(argMultimap, PREFIX_INDEX)) {

            List<Index> indexes = ParserUtil.parseIndexes(argMultimap.getAllValues(PREFIX_INDEX));
            return new DeleteRegimeCommand(name, indexes);

        } else { //index not present delete regime
            return new DeleteRegimeCommand(name, null);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
