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
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;

/**
 * An Immutable Person Data that is serializable to JSON format.
 */
@JsonRootName(value = "system")
public class JsonSerializableParticipationData implements JsonSerializableData {

    public static final String MESSAGE_DUPLICATE_PARTICIPATION =
        "Participations list contains duplicate participation(s).";

    private final List<JsonAdaptedParticipation> participations = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableParticipationData} with the given participations.
     */
    @JsonCreator
    public JsonSerializableParticipationData(
        @JsonProperty("participations") List<JsonAdaptedParticipation> participations
    ) {
        this.participations.addAll(participations);
    }

    /**
     * Converts a given {@code ReadOnlyData} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableParticipationData}.
     */
    public JsonSerializableParticipationData(ReadOnlyData<Participation> source) {
        participations.addAll(source.getListOfElements().stream().map(JsonAdaptedParticipation::new)
            .collect(Collectors.toList()));
    }

    @Override
    public Data toModelType() throws IllegalValueException {
        Data<Participation> participations = new Data();
        return participations;
    }

    /**
     * Converts the stored data into the model's {@code Data} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Data<Participation> toModelType(
        ReadOnlyData<Person> personReadOnlyData,
        ReadOnlyData<Competition> competitionReadOnlyData
    ) throws IllegalValueException {
        Data<Participation> participations = new Data();
        for (JsonAdaptedParticipation jsonAdaptedParticipation : this.participations) {
            Participation participation =
                jsonAdaptedParticipation.toModelType(personReadOnlyData, competitionReadOnlyData);
            if (participations.hasUniqueElement(participation)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PARTICIPATION);
            }
            participations.addUniqueElement(participation);
        }
        return participations;
    }
}
