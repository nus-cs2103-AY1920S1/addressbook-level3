package seedu.address.model.pagestatus;

import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.trip.Trip;

public class PageStatus {
    private final PageType pageType;
    private final Trip trip;
    private final Day day;

    public PageStatus(PageType pageType, Trip trip, Day day ) {
        this.pageType = pageType;
        this.trip = trip;
        this.day = day;
    }

    public PageStatus withNewPageType(PageType pageType) {
        return new PageStatus(pageType, getTrip(), getDay());
    }

    public PageStatus withNewTrip(Trip trip) {
        return new PageStatus(getPageType(), trip, getDay());
    }

    public PageStatus withNewDayId(Day dayId) {
        return new PageStatus(getPageType(), getTrip(), dayId);
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
}
