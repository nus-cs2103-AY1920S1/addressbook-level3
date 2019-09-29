package seedu.address.model.pagestatus;

import seedu.address.model.itinerary.day.DayId;
import seedu.address.model.itinerary.trip.TripId;

public class PageStatus {
    private final PageType pageType;
    private final TripId tripId;
    private final DayId dayId;

    public PageStatus(PageType pageType, TripId tripId, DayId dayId) {
        this.pageType = pageType;
        this.tripId = tripId;
        this.dayId = dayId;
    }

    public PageType getPageType() {
        return pageType;
    }

    public TripId getTripId() {
        return tripId;
    }

    public DayId getDayId() {
        return dayId;
    }
}
