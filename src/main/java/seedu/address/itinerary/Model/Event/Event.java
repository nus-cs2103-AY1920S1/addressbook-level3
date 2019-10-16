package seedu.address.itinerary.model.Event;

/**
 * The events which makes up the itinerary list.
 */
public class Event {
    private Title title;
    private Location location;
    private Description desc;
    private Time time;
    private boolean isDone;

    public Event(Title title, Location location, Description desc, Time time) {
        this.title = title;
        this.location = location;
        this.desc = desc;
        this.time = time;
        this.isDone = false;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDesc() {
        return desc;
    }

    public Location getLocation() {
        return location;
    }

    public Time getTime() {
        return time;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void markIsDone() {
        isDone = true;
    }
}
