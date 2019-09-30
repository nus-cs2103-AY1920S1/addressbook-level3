package seedu.address.model.pagestatus;

import seedu.address.model.itinerary.day.DayId;
import seedu.address.model.itinerary.trip.Trip;

public class PageStatus {
    private final PageType pageType;
    private final Trip trip;
    private final DayId dayId;

    public PageStatus(PageType pageType, Trip trip, DayId dayId) {
        this.pageType = pageType;
        this.trip = trip;
        this.dayId = dayId;
    }

    public PageStatus withNewPageType(PageType pageType) {
        return new PageStatus(pageType, getTrip(), getDayId());
    }

    public PageStatus withNewTrip(Trip trip) {
        return new PageStatus(getPageType(), trip, getDayId());
    }

    public PageStatus withNewDayId(DayId dayId) {
        return new PageStatus(getPageType(), getTrip(), dayId);
    }

    public PageType getPageType() {
        return pageType;
    }

    public Trip getTrip() {
        return trip;
    }

    public DayId getDayId() {
        return dayId;
    }
}
