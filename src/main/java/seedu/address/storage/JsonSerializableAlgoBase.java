package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AlgoBase;
import seedu.address.model.ReadOnlyAlgoBase;
import seedu.address.model.Problem.Problem;

/**
 * An Immutable AlgoBase that is serializable to JSON format.
 */
@JsonRootName(value = "algobase")
class JsonSerializableAlgoBase {

    public static final String MESSAGE_DUPLICATE_PERSON = "Problems list contains duplicate Problem(s).";

    private final List<JsonAdaptedProblem> problems = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAlgoBase} with the given problems.
     */
    @JsonCreator
    public JsonSerializableAlgoBase(@JsonProperty("problems") List<JsonAdaptedProblem> problems) {
        this.problems.addAll(problems);
    }

    /**
     * Converts a given {@code ReadOnlyAlgoBase} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAlgoBase}.
     */
    public JsonSerializableAlgoBase(ReadOnlyAlgoBase source) {
        problems.addAll(source.getProblemList().stream().map(JsonAdaptedProblem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AlgoBase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AlgoBase toModelType() throws IllegalValueException {
        AlgoBase algoBase = new AlgoBase();
        for (JsonAdaptedProblem jsonAdaptedProblem : problems) {
            Problem problem = jsonAdaptedProblem.toModelType();
            if (algoBase.hasProblem(problem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            algoBase.addProblem(problem);
        }
        return algoBase;
    }

}
