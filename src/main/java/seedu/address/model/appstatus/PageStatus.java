package seedu.address.model.appstatus;

import seedu.address.logic.commands.trips.edit.EditTripFieldCommand;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.trip.Trip;

public class PageStatus {
    private final PageType pageType;
    private final Trip trip;
    private final Day day;
    private final EditTripFieldCommand.EditTripDescriptor editTripDescriptor;

    public PageStatus(PageType pageType, Trip trip, Day day,
            EditTripFieldCommand.EditTripDescriptor editTripDescriptor) {
        this.pageType = pageType;
        this.trip = trip;
        this.day = day;
        this.editTripDescriptor = editTripDescriptor;
    }

    public PageStatus withNewPageType(PageType pageType) {
        return new PageStatus(pageType, getTrip(), getDay(), getEditTripDescriptor());
    }

    public PageStatus withNewTrip(Trip trip) {
        return new PageStatus(getPageType(), trip, getDay(), getEditTripDescriptor());
    }

    public PageStatus withNewDay(Day day) {
        return new PageStatus(getPageType(), getTrip(), day, getEditTripDescriptor());
    }

    public PageStatus withNewEditTripDescriptor(EditTripFieldCommand.EditTripDescriptor editTripDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), editTripDescriptor);
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

    public EditTripFieldCommand.EditTripDescriptor getEditTripDescriptor() {
        return editTripDescriptor;
    }
}
