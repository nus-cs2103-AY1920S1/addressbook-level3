package seedu.address.logic.parser.exercise;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTENSITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIMARY_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SETS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.exercise.AddExerciseCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.exercise.components.ExerciseName;
import seedu.address.model.exercise.components.Intensity;
import seedu.address.model.exercise.components.MuscleType;
import seedu.address.model.exercise.components.MusclesTrained;
import seedu.address.model.exercise.details.Distance;
import seedu.address.model.exercise.details.ExerciseDetail;
import seedu.address.model.exercise.details.ExerciseWeight;
import seedu.address.model.exercise.details.Repetitions;
import seedu.address.model.exercise.details.Sets;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddExerciseCommandParser implements Parser<AddExerciseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExerciseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRIMARY_MUSCLE,
                        PREFIX_INTENSITY, PREFIX_DISTANCE, PREFIX_REPETITIONS,
                        PREFIX_SETS, PREFIX_WEIGHT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRIMARY_MUSCLE, PREFIX_INTENSITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExerciseCommand.MESSAGE_USAGE));
        }

        ExerciseName exerciseName = WorkoutPlannerParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        MuscleType primaryMuscle = WorkoutPlannerParserUtil
                .parseMuscleType(argMultimap.getValue(PREFIX_PRIMARY_MUSCLE).get());
        MusclesTrained musclesTrained = new MusclesTrained(primaryMuscle, new ArrayList<>());
        Intensity intensity = WorkoutPlannerParserUtil.parseIntensity(argMultimap.getValue(PREFIX_INTENSITY).get());
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

        Exercise exercise = new Exercise(exerciseName, musclesTrained, intensity, exerciseDetailList);

        return new AddExerciseCommand(exercise);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
