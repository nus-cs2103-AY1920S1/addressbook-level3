package seedu.address.storage;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.competition.Competition;
import seedu.address.model.person.Name;

/**
 * Jackson-friendly version of {@link Competition}.
 */
class JsonAdaptedCompetition implements JsonAdaptedData<Competition> {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Competition's %s field is missing!";

    private final String name;
    private final Date startDate;
    private final Date endDate;

    /**
     * Constructs a {@code JsonAdaptedCompetition} with the given competition details.
     */
    @JsonCreator
    public JsonAdaptedCompetition(@JsonProperty("name") String name,
                                  @JsonProperty("startDate") Date startDate,
                                  @JsonProperty("endDate") Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Converts a given {@code Competition} into this class for Jackson use.
     */
    public JsonAdaptedCompetition(Competition source) {
        name = source.getName().toString();
        startDate = source.getStartDate();
        endDate = source.getEndDate();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Competition} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted competition.
     */
    public Competition toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Date modelStartDate = startDate;
        final Date modelEndDate = endDate;

        return new Competition(modelName, modelStartDate, modelEndDate);
    }

}
