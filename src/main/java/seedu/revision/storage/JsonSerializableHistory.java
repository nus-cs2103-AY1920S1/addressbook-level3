package seedu.revision.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.revision.commons.exceptions.IllegalValueException;
import seedu.revision.model.History;
import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.quiz.Statistics;

/**
 * An Immutable History that is serializable to JSON format.
 */
@JsonRootName(value = "history")
class JsonSerializableHistory {

    private final List<JsonAdaptedStatistics> statistics = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHistory} with the given statistics.
     */
    @JsonCreator
    public JsonSerializableHistory(@JsonProperty("statistics") List<JsonAdaptedStatistics> statistics) {
        this.statistics.addAll(statistics);
    }

    /**
     * Converts a given {@code ReadOnlyHistory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHistory}.
     */
    public JsonSerializableHistory(ReadOnlyHistory source) {
        statistics.addAll(source.getStatisticsList().stream()
                .map(JsonAdaptedStatistics::new).collect(Collectors.toList()));
    }

    /**
     * Converts this history into the model's {@code History} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public History toModelType() throws IllegalValueException {
        History history = new History();
        for (JsonAdaptedStatistics jsonAdaptedStatistics : statistics) {
            Statistics statistics = jsonAdaptedStatistics.toModelType();
            history.addStatistics(statistics);
        }
        return history;
    }
}
