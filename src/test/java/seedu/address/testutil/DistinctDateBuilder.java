/*
@@author shihaoyap
 */

package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;

/**
 *  A utility class to help with building DistinctDate objects.
 */

public class DistinctDateBuilder {

    public static final String DEFAULT_EVENT_DATE = "01/01/2019";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private List<Event> listOfEvents;
    private EventDate date;


    public DistinctDateBuilder() {
        date = new EventDate(LocalDate.parse(DEFAULT_EVENT_DATE, FORMATTER));
        listOfEvents = new ArrayList<>();
    }

    /**
     * Initializes the DistinctDateBuilder with the data of {@code dateToCopy}.
     */
    public DistinctDateBuilder(DistinctDate dateToCopy) {
        date = dateToCopy.getDate();
        listOfEvents = dateToCopy.getListOfEvents();
    }

    /**
     * Sets the {@code EventDate} of the {@code DistinctDate} that we are building.
     */
    public DistinctDateBuilder withDate(LocalDate date) {
        this.date = new EventDate(date);
        return this;
    }

    /**
     * Sets the {@code ListOfEvent} of the {@code DistinctDate} that we are building.
     */
    public DistinctDateBuilder withList(List<Event> list) {
        this.listOfEvents = list;
        return this;
    }

    /**
     * creates an draft Event Objects and returns it
     * @return Event Object
     */
    public DistinctDate build() {
        return new DistinctDate(date, listOfEvents);
    }
}
