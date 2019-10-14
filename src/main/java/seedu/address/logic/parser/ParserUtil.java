package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.details.*;
import seedu.address.model.details.unit.DistanceUnit;
import seedu.address.model.details.unit.WeightUnit;
import seedu.address.model.exercise.Intensity;
import seedu.address.model.exercise.MuscleType;
import seedu.address.model.exercise.Name;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String muscle} into a {@code MuscleType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code muscle} is invalid.
     */
    public static MuscleType parseMuscleType(String muscle) throws ParseException {
        requireNonNull(muscle);
        String trimmedMuscleType = muscle.trim();
        if (!MuscleType.isValidMuscleType(trimmedMuscleType)) {
            throw new ParseException(MuscleType.MESSAGE_CONSTRAINTS);
        }

        return new MuscleType(trimmedMuscleType);
    }

    /**
     * Parses a {@code String intensity} into an {@code Intensity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code intensity} is invalid.
     */
    public static Intensity parseIntensity(String intensity) throws ParseException {
        requireNonNull(intensity);
        String trimmedIntensity = intensity.trim();
        if (!Intensity.isValidIntensity(trimmedIntensity)) {
            throw new ParseException(Intensity.MESSAGE_CONSTRAINTS);
        }
        switch(intensity){

        case "1":
        case "low":
            return Intensity.LOW;

        case "2":
        case "medium":
            return Intensity.MEDIUM;

        case "3":
        case "high":
            return Intensity.HIGH;

        default:
            return null;
        }
    }

    /**
     * Parses a {@code String weight} into a {@code Weight}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be interpreted to form the magnitude and unit
     *
     * @throws ParseException if the given {@code weight} is invalid.
     */
    public static Weight parseWeight(String weight) throws ParseException {
        requireNonNull(weight);
        String trimmedWeight = weight.trim();
        if (!ExerciseDetail.isValidExerciseDetail(trimmedWeight)) {
            throw new ParseException(ExerciseDetail.MESSAGE_CONSTRAINTS);
        }
        String[] weightSplit = trimmedWeight.split(" ");
        String unitString = weightSplit[weightSplit.length - 1];
        WeightUnit unit = parseWeightUnit(unitString);
        float magnitude = Float.parseFloat(weightSplit[0]);

        return new Weight(magnitude, unit);
    }

    public static WeightUnit parseWeightUnit(String weightUnit) throws ParseException {
        switch (weightUnit){
            case "g":
                return WeightUnit.GRAM;

            case "kg":
                return WeightUnit.KILOGRAM;

            case "lbs":
                return WeightUnit.POUND;

            default:
                throw new ParseException(ExerciseDetail.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String distance} into a {@code Distance}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be interpreted to form the magnitude and unit
     *
     * @throws ParseException if the given {@code distance} is invalid.
     */
    public static Distance parseDistance(String distance) throws ParseException {
        requireNonNull(distance);
        String trimmedDistance = distance.trim();
        if (!ExerciseDetail.isValidExerciseDetail(trimmedDistance)) {
            throw new ParseException(ExerciseDetail.MESSAGE_CONSTRAINTS);
        }
        String[] distanceSplit = trimmedDistance.split(" ");
        String unitString = distanceSplit[distanceSplit.length - 1];
        DistanceUnit unit = parseDistanceUnit(unitString);
        float magnitude = Float.parseFloat(distanceSplit[0]);

        return new Distance(magnitude, unit);
    }

    public static DistanceUnit parseDistanceUnit(String distanceUnit) throws ParseException {
        switch (distanceUnit){
            case "m":
                return DistanceUnit.METER;

            case "km":
                return DistanceUnit.KILOMETER;

            default:
                throw new ParseException(ExerciseDetail.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String reps} into a {@code Repetition}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code reps} is invalid.
     */
    public static Repetitions parseRepetitions(String reps) throws ParseException {
        requireNonNull(reps);
        String trimmedReps = reps.trim();
        if (!ExerciseDetail.isValidExerciseDetail(trimmedReps)) {
            throw new ParseException(ExerciseDetail.MESSAGE_CONSTRAINTS);
        }
        int intReps = Integer.parseInt(reps);
        return new Repetitions(intReps);
    }

    /**
     * Parses a {@code String sets} into a {@code Repetition}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sets} is invalid.
     */
    public static Sets parseSets(String sets) throws ParseException {
        requireNonNull(sets);
        String trimmedSet = sets.trim();
        if (!ExerciseDetail.isValidExerciseDetail(trimmedSet)) {
            throw new ParseException(ExerciseDetail.MESSAGE_CONSTRAINTS);
        }
        int intSets = Integer.parseInt(sets);
        return new Sets(intSets);
    }
}
