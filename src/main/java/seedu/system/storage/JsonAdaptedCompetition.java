package seedu.system.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.system.commons.exceptions.IllegalValueException;
import seedu.system.model.competition.Competition;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Name;

/**
 * Jackson-friendly version of {@link Competition}.
 */
class JsonAdaptedCompetition implements JsonAdaptedData<Competition> {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Competition's %s field is missing!";

    private final String name;
    private final String startDate;
    private final String endDate;

    /**
     * Constructs a {@code JsonAdaptedCompetition} with the given competition details.
     */
    @JsonCreator
    public JsonAdaptedCompetition(@JsonProperty("name") String name,
                                  @JsonProperty("startDate") String startDate,
                                  @JsonProperty("endDate") String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Converts a given {@code Competition} into this class for Jackson use.
     */
    public JsonAdaptedCompetition(Competition source) {
        name = source.getName().toString();
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
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

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                CustomDate.class.getSimpleName()));
        }
        if (!CustomDate.isValidDate(startDate)) {
            throw new IllegalValueException(CustomDate.MESSAGE_CONSTRAINTS);
        }
        final CustomDate modelStartDate = new CustomDate(startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                CustomDate.class.getSimpleName()));
        }
        if (!CustomDate.isValidDate(endDate)) {
            throw new IllegalValueException(CustomDate.MESSAGE_CONSTRAINTS);
        }
        final CustomDate modelEndDate = new CustomDate(endDate);

        return new Competition(modelName, modelStartDate, modelEndDate);
    }

}
