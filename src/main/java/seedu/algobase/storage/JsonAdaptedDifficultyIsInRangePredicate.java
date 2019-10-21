package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;

public class JsonAdaptedDifficultyIsInRangePredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT =
        "DifficultyIsInRangePredicate's %s field is missing!";

    private final double lowerBound;
    private final double upperBound;

    @JsonCreator
    public JsonAdaptedDifficultyIsInRangePredicate(@JsonProperty("lowerBound") double lowerBound,
                                                   @JsonProperty("upperBound") double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public JsonAdaptedDifficultyIsInRangePredicate(DifficultyIsInRangePredicate predicate) {
        this.lowerBound = predicate.getLowerBound();
        this.upperBound = predicate.getUpperBound();
    }

    public DifficultyIsInRangePredicate toModelType() {
        return new DifficultyIsInRangePredicate(lowerBound, upperBound);
    }

}
