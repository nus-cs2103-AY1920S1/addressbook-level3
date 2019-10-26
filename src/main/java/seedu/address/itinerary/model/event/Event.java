package seedu.address.itinerary.model.event;

import java.util.Comparator;

/**
 * The events which makes up the itinerary list.
 */
public class Event {
    private Title title;
    private Location location;
    private Description desc;
    private Date date;
    private Time time;
    private boolean isDone;

    public Event(Title title, Date date, Location location, Description desc, Time time) {
        this.title = title;
        this.location = location;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.isDone = false;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDesc() {
        return desc;
    }

    public Date getDate() {
        return date;
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


    /**
     * Check whether two events have similar attribute value.
     * @param otherEvent another event in comparison to the current event.
     * @return boolean result whether the two event have the same attribute value.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && (otherEvent.getTitle().equals(getTitle())
                || otherEvent.getDate().equals(getDate())
                || otherEvent.getTime().equals(getTime()));
    }

    /**
     * Comparator to sort events in the events list in order of Title.
     */
    public static Comparator<Event> titleComparator = Comparator.comparing((Event e) -> e.getTitle().toString())
            .thenComparing(Object::toString);

    /**
     * Comparator to sort events in the events list in order of Location.
     */
    public static Comparator<Event> locationComparator = Comparator.comparing((Event e) -> e.getLocation().toString())
            .thenComparing(Object::toString);

    /**
     * Comparator to sort events in the events list in order of date.
     */
    public static Comparator<Event> dateComparator = new Comparator<Event>() {

        @Override
        public int compare(Event e1, Event e2) {
            int d1 = formatTime(e1.getDate().toString(), e1.getTime().toString());
            int d2 = formatTime(e2.getDate().toString(), e2.getTime().toString());

            return d1 - d2;
        }

        private int formatTime(String date, String time) {
            String result = date.substring(6) + date.substring(3, 5) + date.substring(0, 2);
            String zone = time.substring(6);
            if (zone.equals("a.m.")) {
                String hour = time.substring(0, 2);
                if (hour.equals("12")) {
                    result = result + "00" + time.substring(3, 5);
                } else {
                    result = result + time.substring(0, 2) + time.substring(3, 5);
                }
            } else {
                int changeTime = Integer.parseInt(time.substring(0, 2)) + 12;
                result = result + changeTime + time.substring(3, 5);
            }
            return Integer.parseInt(result);
        }
    }.thenComparing(Object::toString);


    /**
     * Comparator to sort events in the events list in order of Completion.
     */
    public static Comparator<Event> completionComparator = new Comparator<Event>() {

        @Override
        public int compare(Event e1, Event e2) {
            boolean doneE1 = e1.getIsDone();
            boolean doneE2 = e2.getIsDone();

            if (doneE1) {
                return -1;
            } else if (doneE2) {
                return 1;
            } else {
                return 0;
            }
        }
    }.thenComparing(Object::toString);
}
