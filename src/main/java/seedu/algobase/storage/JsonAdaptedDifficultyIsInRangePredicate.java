package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;

/**
 * Jackson-friendly version of {@link DifficultyIsInRangePredicate}.
 */
public class JsonAdaptedDifficultyIsInRangePredicate {

    private final double lowerBound;
    private final double upperBound;

    /**
     * Constructs a {@code JsonAdaptedDifficultyIsInRangePredicate} with the given
     * DifficultyIsInRangePredicate details.
     */
    @JsonCreator
    public JsonAdaptedDifficultyIsInRangePredicate(@JsonProperty("lowerBound") double lowerBound,
                                                   @JsonProperty("upperBound") double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * Converts a given {@code DifficultyIsInRangePredicate} into this class for Jackson use.
     */
    public JsonAdaptedDifficultyIsInRangePredicate(DifficultyIsInRangePredicate predicate) {
        this.lowerBound = predicate.getLowerBound();
        this.upperBound = predicate.getUpperBound();
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's
     * {@code DifficultyIsInRangePredicate} object.
     */
    public DifficultyIsInRangePredicate toModelType() throws IllegalValueException {
        if (!DifficultyIsInRangePredicate.isValidDifficultyRange(lowerBound, upperBound)) {
            throw new IllegalValueException(DifficultyIsInRangePredicate.MESSAGE_CONSTRAINTS);
        }
        return new DifficultyIsInRangePredicate(lowerBound, upperBound);
    }

}
