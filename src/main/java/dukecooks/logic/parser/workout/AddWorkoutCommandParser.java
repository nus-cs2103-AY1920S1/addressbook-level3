package dukecooks.logic.parser.workout;

import dukecooks.logic.commands.workout.AddWorkoutCommand;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.Prefix;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.WorkoutPlannerParserUtil;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.WorkoutName;

import java.util.stream.Stream;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_WORKOUT_NAME;

/**
 * Parses input arguments and creates a new AddWorkoutCommand object
 */
public class AddWorkoutCommandParser implements Parser<AddWorkoutCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddWorkoutCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddWorkoutCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_WORKOUT_NAME);

        if(!arePrefixesPresent(argumentMultimap, PREFIX_WORKOUT_NAME) || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWorkoutCommand.MESSAGE_USAGE)));
        }
        WorkoutName name = WorkoutPlannerParserUtil.parseWorkoutName(argumentMultimap.getValue(PREFIX_WORKOUT_NAME)
                .get());
        Workout workout = new Workout(name);
        return new AddWorkoutCommand(workout);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
