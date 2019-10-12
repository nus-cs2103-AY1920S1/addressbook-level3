package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.AddCommand;
import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.AddRegimeCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.exercise.Calories;
import seedu.exercise.model.exercise.Date;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.Muscle;
import seedu.exercise.model.exercise.Name;
import seedu.exercise.model.exercise.Quantity;
import seedu.exercise.model.exercise.Unit;
import seedu.exercise.model.regime.RegimeName;

/**
 * Parses input arguments and creates a new AddExerciseCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_NAME, PREFIX_DATE, PREFIX_CALORIES,
                        PREFIX_QUANTITY, PREFIX_UNIT, PREFIX_MUSCLE, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_CATEGORY) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        String category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());

        if (category.equals("exercise")) {
            return parseExercise(argMultimap);
        }

        if (category.equals("regime")) {
            return parseRegime(argMultimap);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExerciseCommand.MESSAGE_USAGE));
    }

    /**
     * Parses arguments and returns AddExerciseCommand for execution
     */
    private AddExerciseCommand parseExercise(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE, PREFIX_CALORIES,
                PREFIX_QUANTITY, PREFIX_UNIT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddExerciseCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Calories calories = ParserUtil.parseCalories(argMultimap.getValue(PREFIX_CALORIES).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Unit unit = ParserUtil.parseUnit(argMultimap.getValue(PREFIX_UNIT).get());
        Set<Muscle> muscleList = ParserUtil.parseMuscles(argMultimap.getAllValues(PREFIX_MUSCLE));

        Exercise exercise = new Exercise(name, date, calories, quantity, unit, muscleList);

        return new AddExerciseCommand(exercise);
    }

    /**
     * Parses arguments and returns AddRegimeCommand for execution
     */
    private AddRegimeCommand parseRegime(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRegimeCommand.MESSAGE_USAGE));
        }

        RegimeName regimeName = ParserUtil.parseRegimeName(argMultimap.getValue(PREFIX_NAME).get());
        List<Index> indexes = ParserUtil.parseIndexes(argMultimap.getAllValues(PREFIX_INDEX));

        return new AddRegimeCommand(indexes, regimeName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
