package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.itinerary.Date;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.itinerary.trip.Trip;

public class JsonAdaptedTrip {
    private final String name;
    private final Date from;
    private final Date to;
    private final String destination;
    private final double totalBudget;
    private final DayList dayList;

    @JsonCreator
    public JsonAdaptedTrip(
            @JsonProperty("name") String name
            , @JsonProperty("from")Date from
            , @JsonFormat()("to")Date to
            , @JsonProperty("destination")String destination
            , @JsonProperty("totalBudget")double totalBudget
            , @JsonProperty("dayList")DayList dayList
    ) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.dayList = dayList;
    }

    public JsonAdaptedTrip(Trip source){
        this.name = source.getName().fullName;
        this.from = source.getFrom();
        this.to = source.getTo();
        this.destination = source.getDestintaion().value;
        this.totalBudget = source.getTotalBudget().value;
        this.dayList = source.getDayList();

    }

    public Trip toModelType(){
        return null;
    }
}
