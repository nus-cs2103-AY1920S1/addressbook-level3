package seedu.address.model.itinerary.trip;

import seedu.address.model.itinerary.Date;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;

public class Trip {
    private final Name name;
    private final Date from;
    private final Date to;
    private final Location destintaion;
    private final Expenditure TotalBudget;
    private final DayList dayList;

    public Trip(Name name, Date from, Date to, Location destintaion, Expenditure totalBudget, DayList dayList) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.destintaion = destintaion;
        TotalBudget = totalBudget;
        this.dayList = dayList;
    }

    public Name getName() {
        return name;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public Location getDestintaion() {
        return destintaion;
    }

    public Expenditure getTotalBudget() {
        return TotalBudget;
    }

    public DayList getDayList() {
        return dayList;
    }
}
