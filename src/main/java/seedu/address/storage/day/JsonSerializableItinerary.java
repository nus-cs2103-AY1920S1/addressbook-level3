package seedu.address.storage.day;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Itinerary;
import seedu.address.model.ReadOnlyItinerary;
import seedu.address.model.day.Day;
import seedu.address.model.field.Name;

/**
 * An Immutable Itinerary that is serializable to JSON format.
 */
@JsonRootName(value = "itinerary")
class JsonSerializableItinerary {

    public static final String MESSAGE_DUPLICATE_DAY = "Days list contains duplicate days(s).";

    private final List<JsonAdaptedDay> days = new ArrayList<>();
    private String name;
    private String startDate;

    /**
     * Constructs a {@code JsonSerializableItinerary} with the given days.
     */
    @JsonCreator
    public JsonSerializableItinerary(@JsonProperty("days") List<JsonAdaptedDay> days,
                                     @JsonProperty("name") String name, @JsonProperty("startDate") String startDate) {
        this.days.addAll(days);
        this.name = name;
        this.startDate = startDate;
    }

    /**
     * Converts a given {@code ReadOnlyItinerary} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableItinerary}.
     */
    public JsonSerializableItinerary(ReadOnlyItinerary source) {
        days.addAll(source.getItinerary().stream().map(JsonAdaptedDay::new).collect(Collectors.toList()));
        name = source.getName().toString();
        startDate = source.getStartDate().toString();
    }

    /**
     * Converts this address book into the model's {@code Itinerary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Itinerary toModelType() throws IllegalValueException {
        Itinerary itinerary = new Itinerary();
        for (JsonAdaptedDay jsonAdaptedDay : days) {
            Day day = jsonAdaptedDay.toModelType();
            if (itinerary.hasDay(day)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DAY);
            }
            itinerary.addDay(day);
        }
        String[] sdArray = startDate.split("-");
        assert(sdArray.length == 3);
        int sdYear = Integer.parseInt(sdArray[0]);
        int sdMonth = Integer.parseInt(sdArray[1]);
        int sdDay = Integer.parseInt(sdArray[2]);
        itinerary.setName(new Name(name));
        itinerary.setStartDate(LocalDate.of(sdYear, sdMonth, sdDay));
        return itinerary;
    }

}
