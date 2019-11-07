package seedu.revision.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.revision.commons.exceptions.IllegalValueException;
import seedu.revision.model.quiz.Statistics;

/**
 * Jackson-friendly version of {@link seedu.revision.model.quiz.Statistics}.
 */
public class JsonAdaptedStatistics {

    private final String totalScore;

    /**
     * Constructs a {@code JsonAdaptedStatistics} with the given statistics details.
     */
    @JsonCreator
    public JsonAdaptedStatistics(@JsonProperty("totalScore") String totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Converts a given {@code Statistics} into this class for Jackson use.
     */
    public JsonAdaptedStatistics(Statistics source) {
        totalScore = source.toString();
    }

    /**
     * Converts this Jackson-friendly adapted statistics object into the model's {@code Statistics} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted statistics.
     */
    public Statistics toModelType() throws IllegalValueException {
        if (!Statistics.isValidStatistics(totalScore)) {
            throw new IllegalValueException(Statistics.MESSAGE_CONSTRAINTS);
        }
        return new Statistics(totalScore);
    }
}
