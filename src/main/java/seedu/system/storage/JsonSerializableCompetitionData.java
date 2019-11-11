package seedu.system.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.system.commons.exceptions.IllegalValueException;
import seedu.system.model.Data;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.competition.Competition;

/**
 * An Immutable Competition Data that is serializable to JSON format.
 */
@JsonRootName(value = "system")
public class JsonSerializableCompetitionData implements JsonSerializableData {

    public static final String MESSAGE_DUPLICATE_COMPETITION = "Competitions list contains duplicate competition(s).";

    private final List<JsonAdaptedCompetition> competitions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCompetitionData} with the given competitions.
     */
    @JsonCreator
    public JsonSerializableCompetitionData(@JsonProperty("competitions") List<JsonAdaptedCompetition> competitions) {
        this.competitions.addAll(competitions);
    }

    /**
     * Converts a given {@code ReadOnlyData} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCompetitionData}.
     */
    public JsonSerializableCompetitionData(ReadOnlyData<Competition> source) {
        competitions.addAll(source.getListOfElements().stream().map(JsonAdaptedCompetition::new)
            .collect(Collectors.toList()));
    }

    @Override
    public Data toModelType() throws IllegalValueException {
        Data<Competition> competitions = new Data();
        for (JsonAdaptedCompetition jsonAdaptedCompetition : this.competitions) {
            Competition competition = jsonAdaptedCompetition.toModelType();
            if (competitions.hasUniqueElement(competition)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_COMPETITION);
            }
            competitions.addUniqueElement(competition);
        }
        return competitions;
    }
}
