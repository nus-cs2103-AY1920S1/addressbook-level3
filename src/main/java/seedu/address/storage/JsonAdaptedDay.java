package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Photo;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.event.Event;
import seedu.address.model.itinerary.event.EventList;

/**
 * Jackson friendly version of {@code Day}.
 */
public class JsonAdaptedDay {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Day's %s field is missing!";

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Optional<String> description;
    private final String destination;
    private final Optional<Double> totalBudget;
    private final Optional<JsonAdaptedPhoto> photo;
    private final List<JsonAdaptedEvent> eventList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDay} with the given Day details.
     */
    @JsonCreator
    public JsonAdaptedDay(@JsonProperty("startTime") LocalDateTime from,
                          @JsonProperty("endTime") LocalDateTime to,
                          @JsonProperty("destination") String destination,
                          @JsonProperty("description") Optional<String> description,
                          @JsonProperty("totalBudget") Optional<Double> totalBudget,
                          @JsonProperty("eventList") List<JsonAdaptedEvent> eventList,
                          @JsonProperty("photo") Optional<JsonAdaptedPhoto> photo
    ) {
        this.startTime = from;
        this.endTime = to;
        this.description = description;
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.photo = photo;
        if (eventList != null) {
            this.eventList.addAll(eventList);
        }
    }

    /**
     * Converts a given {@code Day} into this class for Jackson use.
     */
    public JsonAdaptedDay(Day source) {
        this.startTime = source.getStartDate();
        this.endTime = source.getEndDate();
        this.destination = source.getDestination().value;

        if (source.getDescription().isPresent()) {
            this.description = Optional.of(source.getDescription().get().description);
        } else {
            this.description = Optional.empty();
        }
        if (source.getTotalBudget().isPresent()) {
            this.totalBudget = Optional.of(source.getTotalBudget().get().getValue());
        } else {
            this.totalBudget = Optional.empty();
        }
        if (source.getPhoto().isPresent()) {
            this.photo = Optional.of(new JsonAdaptedPhoto(source.getPhoto().get()));
        } else {
            this.photo = Optional.empty();
        }
        this.eventList.addAll(source.getEventList()
                .asUnmodifiableObservableList()
                .stream().map(JsonAdaptedEvent::new)
                .collect(Collectors.toList())
        );
    }

    /**
     * Converts this Jackson-friendly adapted day object into the model's {@code Day} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted day.
     */
    public Day toModelType() throws IllegalValueException {
        final List<Event> events = new ArrayList<>();
        for (JsonAdaptedEvent event : eventList) {
            events.add(event.toModelType());
        }

        if (startTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }

        // Assumes validation done upon creation

        final LocalDateTime modelStartTime = startTime;

        if (endTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }

        // Assumes validation done upon creation

        final LocalDateTime modelEndTime = endTime;

        if (destination == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }

        if (!Location.isValidLocation(destination)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }

        final Location modelDestination = new Location(destination);

        final Optional<Description> modelDescription;

        if (description.isPresent()) {
            if (!Description.isValidDescription(description.get())) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
            }
            modelDescription = Optional.of(new Description(description.get()));
        } else {
            modelDescription = Optional.empty();
        }

        Optional<Photo> modelPhoto;
        if (photo.isPresent()) {
            modelPhoto = Optional.of(photo.get().toModelType());
        } else {
            modelPhoto = Optional.empty();
        }

        //No check for TotalBudget (defaults endTime 0)
        final Optional<Budget> modelTotalBudget;

        modelTotalBudget = totalBudget.map(Budget::new);

        EventList modelEventList = new EventList(startTime);
        modelEventList.set(events);

        return new Day(modelStartTime, modelEndTime, modelDescription,
                modelDestination, modelTotalBudget, modelEventList, modelPhoto);
    }
}
