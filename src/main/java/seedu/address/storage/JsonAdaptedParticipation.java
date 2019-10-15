package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.participation.Participation;

/**
 * Jackson-friendly version of {@link Participation}.
 */
public class JsonAdaptedParticipation implements JsonAdaptedData<Participation> {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Participation's %s field is missing!";

    private final String personName;
    private final String competitionName;

    /**
     * Constructs a {@code JsonAdaptedParticipation} with the given participation details.
     */
    @JsonCreator
    public JsonAdaptedParticipation(@JsonProperty("person") String personName,
                                    @JsonProperty("competition") String competitionName) {
        this.personName = personName;
        this.competitionName = competitionName;
    }

    /**
     * Converts a given {@code Participation} into this class for Jackson use.
     */
    public JsonAdaptedParticipation(Participation source) {
        personName = source.getPerson().getName().toString();
        competitionName = source.getCompetition().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Participation} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted participation.
     */
    public Participation toModelType() throws IllegalValueException {
        return null;
    }

}
