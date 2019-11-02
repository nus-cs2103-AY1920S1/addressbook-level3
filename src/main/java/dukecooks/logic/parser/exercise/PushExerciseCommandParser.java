package dukecooks.logic.parser.exercise;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_SETS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_TIMING;
import static dukecooks.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_WORKOUT_INDEX;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.exercise.PushExerciseCommand;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.Prefix;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.workout.exercise.details.Distance;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.model.workout.exercise.details.Sets;
import dukecooks.model.workout.exercise.details.Timing;

/**
 * Parses input arguments and creates a new PushExerciseCommand object.
 */
public class PushExerciseCommandParser implements Parser<PushExerciseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PushExerciseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_INDEX, PREFIX_WORKOUT_INDEX,
                        PREFIX_DISTANCE, PREFIX_REPETITIONS,
                        PREFIX_SETS, PREFIX_WEIGHT, PREFIX_TIMING);

        if (!arePrefixesPresent(argMultimap, PREFIX_EXERCISE_INDEX, PREFIX_WORKOUT_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PushExerciseCommand.MESSAGE_USAGE));
        }
        Index workoutIndex = WorkoutPlannerParserUtil.parseIndex(argMultimap.getValue(PREFIX_WORKOUT_INDEX).get());
        Index exerciseIndex = WorkoutPlannerParserUtil.parseIndex(argMultimap.getValue(PREFIX_EXERCISE_INDEX).get());

        Set<ExerciseDetail> exerciseDetailList = new HashSet<>();

        if (!argMultimap.getValue(PREFIX_DISTANCE).isEmpty()) {
            Distance distance = WorkoutPlannerParserUtil.parseDistance(argMultimap.getValue(PREFIX_DISTANCE).get());
            exerciseDetailList.add(distance);
        }

        if (!argMultimap.getValue(PREFIX_REPETITIONS).isEmpty()) {
            Repetitions repetitions = WorkoutPlannerParserUtil
                    .parseRepetitions(argMultimap.getValue(PREFIX_REPETITIONS).get());
            exerciseDetailList.add(repetitions);
        }

        if (!argMultimap.getValue(PREFIX_SETS).isEmpty()) {
            Sets sets = WorkoutPlannerParserUtil.parseSets(argMultimap.getValue(PREFIX_SETS).get());
            exerciseDetailList.add(sets);
        }

        if (!argMultimap.getValue(PREFIX_WEIGHT).isEmpty()) {
            ExerciseWeight exerciseWeight = WorkoutPlannerParserUtil
                    .parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get());
            exerciseDetailList.add(exerciseWeight);
        }

        if (!argMultimap.getValue(PREFIX_TIMING).isEmpty()) {
            Timing timing = WorkoutPlannerParserUtil
                    .parseTiming(argMultimap.getValue(PREFIX_TIMING).get());
            exerciseDetailList.add(timing);
        }

        return new PushExerciseCommand(workoutIndex, exerciseIndex, exerciseDetailList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


