package dukecooks.logic.parser.exercise;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INTENSITY;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PRIMARY_MUSCLE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_SECONDARY_MUSCLE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_SETS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.exercise.EditExerciseCommand;
import dukecooks.logic.commands.exercise.EditExerciseCommand.EditExerciseDescriptor;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.workout.exercise.details.Distance;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.model.workout.exercise.details.Sets;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditExerciseCommandParser implements Parser<EditExerciseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditExerciseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRIMARY_MUSCLE, PREFIX_SECONDARY_MUSCLE,
                        PREFIX_INTENSITY, PREFIX_DISTANCE, PREFIX_REPETITIONS,
                        PREFIX_SETS, PREFIX_WEIGHT);

        Index index;

        try {
            index = WorkoutPlannerParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditExerciseCommand.MESSAGE_USAGE), pe);
        }

        EditExerciseCommand.EditExerciseDescriptor editExerciseDescriptor = new EditExerciseDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editExerciseDescriptor.setExerciseName(WorkoutPlannerParserUtil
                    .parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_PRIMARY_MUSCLE).isPresent()) {
            editExerciseDescriptor.setPrimaryMuscle(WorkoutPlannerParserUtil.parseMuscleType(argMultimap
                    .getValue(PREFIX_PRIMARY_MUSCLE).get()));
        }

        if (!argMultimap.getAllValues(PREFIX_SECONDARY_MUSCLE).isEmpty()) {
            editExerciseDescriptor.setSecondaryMuscles(WorkoutPlannerParserUtil
                    .parseSecondaryMuscle(argMultimap.getAllValues(PREFIX_SECONDARY_MUSCLE)));
        }

        if (argMultimap.getValue(PREFIX_INTENSITY).isPresent()) {
            editExerciseDescriptor.setIntensity(WorkoutPlannerParserUtil.parseIntensity(argMultimap
                    .getValue(PREFIX_INTENSITY).get()));
        }

        Set<ExerciseDetail> exerciseDetails = new HashSet<>();

        if (argMultimap.getValue(PREFIX_DISTANCE).isPresent()) {
            Distance distance = WorkoutPlannerParserUtil.parseDistance(argMultimap
                   .getValue(PREFIX_DISTANCE).get());
            exerciseDetails.add(distance);
        }

        if (argMultimap.getValue(PREFIX_REPETITIONS).isPresent()) {
            Repetitions reps = WorkoutPlannerParserUtil.parseRepetitions(argMultimap
                    .getValue(PREFIX_REPETITIONS).get());
            exerciseDetails.add(reps);
        }

        if (argMultimap.getValue(PREFIX_SETS).isPresent()) {
            Sets sets = WorkoutPlannerParserUtil.parseSets(argMultimap
                    .getValue(PREFIX_SETS).get());
            exerciseDetails.add(sets);
        }

        if (argMultimap.getValue(PREFIX_WEIGHT).isPresent()) {
            ExerciseWeight weight = WorkoutPlannerParserUtil.parseWeight(argMultimap
                    .getValue(PREFIX_WEIGHT).get());
            exerciseDetails.add(weight);
        }

        if (exerciseDetails.isEmpty()) {
            editExerciseDescriptor.setExerciseDetails(null);
        } else {
            editExerciseDescriptor.setExerciseDetails(exerciseDetails);
        }

        if (!editExerciseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditExerciseCommand.MESSAGE_NOT_EDITED);
        }

        return new EditExerciseCommand(index, editExerciseDescriptor);
    }
}
