package seedu.address.model.appstatus;

import seedu.address.logic.commands.preferences.EditPrefsFieldCommand.EditPrefsDescriptor;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand.EditTripDescriptor;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.trip.Trip;

public class PageStatus {
    private final PageType pageType;
    private final Trip trip;
    private final Day day;
    private final EditTripDescriptor editTripDescriptor;
    private final EditPrefsDescriptor editPrefsDescriptor;

    public PageStatus(PageType pageType, Trip trip, Day day,
            EditTripDescriptor editTripDescriptor, EditPrefsDescriptor editPrefsDescriptor) {
        this.pageType = pageType;
        this.trip = trip;
        this.day = day;
        this.editTripDescriptor = editTripDescriptor;
        this.editPrefsDescriptor = editPrefsDescriptor;
    }

    public PageStatus withNewPageType(PageType pageType) {
        return new PageStatus(pageType, getTrip(), getDay(), getEditTripDescriptor(), getEditPrefsDescriptor());
    }

    public PageStatus withNewTrip(Trip trip) {
        return new PageStatus(getPageType(), trip, getDay(), getEditTripDescriptor(), getEditPrefsDescriptor());
    }

    public PageStatus withNewDay(Day day) {
        return new PageStatus(getPageType(), getTrip(), day, getEditTripDescriptor(), getEditPrefsDescriptor());
    }

    public PageStatus withNewEditTripDescriptor(EditTripDescriptor editTripDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), editTripDescriptor, getEditPrefsDescriptor());
    }

    public PageStatus withNewEditPrefsDescriptor(EditPrefsDescriptor editPrefsDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEditTripDescriptor(), editPrefsDescriptor);
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

    public EditTripDescriptor getEditTripDescriptor() {
        return editTripDescriptor;
    }

    public EditPrefsDescriptor getEditPrefsDescriptor() {
        return editPrefsDescriptor;
    }
}
