package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.trip.Trip;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Jackson friendly version of {@code Trip}.
 */
public class JsonAdaptedTrip {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Trip's %s field is missing!";

    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String destination;
    private final Double totalBudget;
    private final List<JsonAdaptedDay> dayList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTrip} with the given trip details.
     */
    @JsonCreator
    public JsonAdaptedTrip(
            @JsonProperty("name") String name,
            @JsonProperty("startDate")LocalDateTime startDate,
            @JsonProperty("endDate") LocalDateTime endDate,
            @JsonProperty("destination")String destination,
            @JsonProperty("totalBudget") Double totalBudget,
            @JsonProperty("dayList")List<JsonAdaptedDay> dayList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.totalBudget = totalBudget;
        if(dayList != null) {
            this.dayList.addAll(dayList);
        }
    }

    /**
     * Converts a given {@code Trip} into this class for Jackson use.
     */
    public JsonAdaptedTrip(Trip source) {
        this.name = source.getName().fullName;
        this.startDate = source.getStartDate();
        this.endDate = source.getEndDate();
        this.destination = source.getDestination().value;
        this.totalBudget = source.getBudget().value;
        this.dayList.addAll(source.getDayList()
                .asUnmodifiableObservableList()
                .stream().map(JsonAdaptedDay::new)
                .collect(Collectors.toList())
        );
    }

    /**
     * Converts this Jackson-friendly adapted trip object into the model's {@code Trip} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted trip.
     */
    public Trip toModelType() throws IllegalValueException {
        final List<Day> days = new ArrayList<>();
        for (JsonAdaptedDay day : dayList){
            days.add(day.toModelType());
        }

        if(name == null){
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }

        if(!Name.isValidName(name)){
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        if(startDate == null){
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start Date"));
        }

        final LocalDateTime modelStartDate = startDate;

        if (endDate == null){
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "End Date"));
        }

        final LocalDateTime modelEndDate = endDate;

        if(destination == null){
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }

        if(!Location.isValidLocation(destination)){
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }

        final Location modelDestination = new Location(destination);

        //No check for TotalBudget (defaults to 0)
        Expenditure modelTotalBudget = new Expenditure(totalBudget);


        DayList modelDayList = new DayList();
        modelDayList.set(days);

        return new Trip(modelName, modelStartDate, modelEndDate,
                modelDestination, modelTotalBudget ,modelDayList);
    }
}
