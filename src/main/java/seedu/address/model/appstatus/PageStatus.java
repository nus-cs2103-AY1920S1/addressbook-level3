package seedu.address.model.appstatus;

import seedu.address.logic.commands.itinerary.days.edit.EditDayFieldCommand;
import seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand;
import seedu.address.logic.commands.preferences.EditPrefsFieldCommand.EditPrefsDescriptor;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand.EditTripDescriptor;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.event.Event;
import seedu.address.model.trip.Trip;

public class PageStatus {
    private final PageType pageType;
    private final Trip trip;
    private final Day day;
    private final Event event;
    private final EditTripDescriptor editTripDescriptor;
    private final EditPrefsDescriptor editPrefsDescriptor;
    private final EditDayFieldCommand.EditDayDescriptor editDayDescriptor;
    private final EditEventFieldCommand.EditEventDescriptor editEventDescriptor;

    public PageStatus(PageType pageType, Trip trip, Day day,
                      Event event, EditTripDescriptor editTripDescriptor, EditPrefsDescriptor editPrefsDescriptor
            , EditDayFieldCommand.EditDayDescriptor editDayDescriptor, EditEventFieldCommand.EditEventDescriptor editEventDescriptor) {
        this.pageType = pageType;
        this.trip = trip;
        this.day = day;
        this.event = event;
        this.editTripDescriptor = editTripDescriptor;
        this.editPrefsDescriptor = editPrefsDescriptor;
        this.editDayDescriptor = editDayDescriptor;
        this.editEventDescriptor = editEventDescriptor;
    }

    public PageStatus withNewPageType(PageType pageType) {
        return new PageStatus(pageType, getTrip(), getDay(), getEvent(), getEditTripDescriptor(), getEditPrefsDescriptor(), getEditDayDescriptor(), getEditEventDescriptor());
    }

    // Keeps reference to new parameters
    public PageStatus withNewTrip(Trip trip) {
        return new PageStatus(getPageType(), trip, getDay(), getEvent(), getEditTripDescriptor(), getEditPrefsDescriptor(), getEditDayDescriptor(), getEditEventDescriptor());
    }

    public PageStatus withNewDay(Day day) {
        return new PageStatus(getPageType(), getTrip(), day, getEvent(), getEditTripDescriptor(), getEditPrefsDescriptor(), getEditDayDescriptor(), getEditEventDescriptor());
    }

    public PageStatus withNewEvent(Event event) {
        return new PageStatus(getPageType(), getTrip(), getDay(), event, getEditTripDescriptor(), getEditPrefsDescriptor(), getEditDayDescriptor(), getEditEventDescriptor());
    }
    public PageStatus withResetTrip() {
        return withNewTrip(null);
    }

    public PageStatus withResetDay() {
        return withNewDay(null);
    }

    public PageStatus withResetEvent() {
        return withNewEvent(null);
    }

    public PageStatus withNewEditTripDescriptor(EditTripDescriptor editTripDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), editTripDescriptor, getEditPrefsDescriptor(), getEditDayDescriptor(), getEditEventDescriptor());
    }

    public PageStatus withNewEditPrefsDescriptor(EditPrefsDescriptor editPrefsDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getEditTripDescriptor(), editPrefsDescriptor, getEditDayDescriptor(), getEditEventDescriptor());
    }

    public PageStatus withNewEditDayDescriptor(EditDayFieldCommand.EditDayDescriptor editDayDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getEditTripDescriptor(), getEditPrefsDescriptor(), editDayDescriptor, getEditEventDescriptor());
    }

    public PageStatus withNewEditEventDescriptor(EditEventFieldCommand.EditEventDescriptor editEventDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getEditTripDescriptor(), getEditPrefsDescriptor(), getEditDayDescriptor(), editEventDescriptor);
    }

    public PageStatus withResetEditTripDescriptor() {
        return withNewEditTripDescriptor(null);
    }

    public PageStatus withResetEditPrefsDescriptor() {
        return withNewEditPrefsDescriptor(null);
    }

    public PageStatus withResetEditDayDescriptor() {
        return withNewEditDayDescriptor(null);
    }

    public PageStatus withResetEditEventDescriptor() {
        return withNewEditEventDescriptor(null);
    }

    public PageType getPageType() {
        return pageType;
    }

    public Trip getTrip() {
        return trip;
    }

    public Day getDay() {
        return day;
    }

    public Event getEvent() {
        return event;
    }

    public EditTripDescriptor getEditTripDescriptor() {
        return editTripDescriptor;
    }

    public EditDayFieldCommand.EditDayDescriptor getEditDayDescriptor() {
        return editDayDescriptor;
    }

    public EditEventFieldCommand.EditEventDescriptor getEditEventDescriptor() {
        return editEventDescriptor;
    }

    public EditPrefsDescriptor getEditPrefsDescriptor() {
        return editPrefsDescriptor;
    }
}
