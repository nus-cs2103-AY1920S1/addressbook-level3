package seedu.address.storage.accommodation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AccommodationManager;
import seedu.address.model.ReadOnlyAccommodation;
import seedu.address.model.itineraryitem.accommodation.Accommodation;

/**
 * An Immutable Accommodation that is serializable to JSON format.
 */
@JsonRootName(value = "accommodationManager")
public class JsonSerializableAccommodation {

    public static final String MESSAGE_DUPLICATE_ACCOMMODATION = "Accommodations list contains duplicate "
            + "accommodations(s).";

    private final List<JsonAdaptedAccommodation> accommodations = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAccommodation} with the given accommodations.
     */
    @JsonCreator
    public JsonSerializableAccommodation(
        @JsonProperty("accommodations") List<JsonAdaptedAccommodation> accommodations) {
        this.accommodations.addAll(accommodations);
    }

    /**
     * Converts a given {@code ReadOnlyAccommodation} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAccommodation}.
     */
    public JsonSerializableAccommodation(ReadOnlyAccommodation source) {
        accommodations.addAll(source.getAccommodationList().stream().map(JsonAdaptedAccommodation::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts these accommodation data into the model's {@code AccommodationManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AccommodationManager toModelType() throws IllegalValueException {
        AccommodationManager accommodation = new AccommodationManager();
        for (JsonAdaptedAccommodation jsonAdaptedAccommodation : accommodations) {
            Accommodation accom = jsonAdaptedAccommodation.toModelType();
            if (accommodation.hasAccommodation(accom)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOMMODATION);
            }
            accommodation.addAccommodation(accom);
        }
        return accommodation;
    }

}
