package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTENSITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIMARY_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SETS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.*;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditExerciseCommand;
import seedu.address.logic.commands.EditExerciseCommand.EditExerciseDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.details.*;
import seedu.address.model.exercise.MusclesTrained;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditExerciseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditExerciseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRIMARY_MUSCLE,
                        PREFIX_INTENSITY, PREFIX_DISTANCE, PREFIX_REPETITIONS,
                        PREFIX_SETS, PREFIX_WEIGHT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExerciseCommand.MESSAGE_USAGE), pe);
        }

        EditExerciseCommand.EditExerciseDescriptor editExerciseDescriptor = new EditExerciseDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editExerciseDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_PRIMARY_MUSCLE).isPresent()) {
            editExerciseDescriptor.setPrimaryMuscle(ParserUtil.parseMuscleType(argMultimap
                    .getValue(PREFIX_PRIMARY_MUSCLE).get()));
        }

        Set<ExerciseDetail> exerciseDetails = new HashSet<>();

        argMultimap.getValue(PREFIX_DISTANCE).ifPresent(value -> {
            Distance distance;
            try {
                distance = ParserUtil.parseDistance(value);
                exerciseDetails.add(distance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        argMultimap.getValue(PREFIX_REPETITIONS).ifPresent(value -> {
            Repetitions reps;
            try {
                reps = ParserUtil.parseRepetitions(value);
                exerciseDetails.add(reps);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        argMultimap.getValue(PREFIX_SETS).ifPresent(value -> {
            Sets sets;
            try {
                sets = ParserUtil.parseSets(value);
                exerciseDetails.add(sets);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        argMultimap.getValue(PREFIX_WEIGHT).ifPresent(value -> {
            Weight weight;
            try {
                weight = ParserUtil.parseWeight(value);
                exerciseDetails.add(weight);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        editExerciseDescriptor.setExerciseDetails(exerciseDetails);

        if (!editExerciseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditExerciseCommand.MESSAGE_NOT_EDITED);
        }

        return new EditExerciseCommand(index, editExerciseDescriptor);
    }
}
