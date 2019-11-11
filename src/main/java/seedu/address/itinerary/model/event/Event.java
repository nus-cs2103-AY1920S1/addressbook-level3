package seedu.address.itinerary.model.event;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The events which makes up the itinerary list.
 */
public class Event {
    /**
     * Comparator to sort events in the events list in order of Title.
     */
    private static Comparator<Event> titleComparator = Comparator.comparing((Event e) -> e.getTitle().toString())
            .thenComparing(Object::toString);

    /**
     * Comparator to sort events in the events list in order of Location.
     */
    private static Comparator<Event> locationComparator = Comparator.comparing((Event e) -> e.getLocation().toString())
            .thenComparing(Object::toString);

    /**
     * Comparator to sort events in the events list in order of date.
     */
    private static Comparator<Event> dateComparator = new Comparator<Event>() {

        @Override
        public int compare(Event e1, Event e2) {
            long d1 = formatTime(e1.getDate().toString(), e1.getTime().toString());
            long d2 = formatTime(e2.getDate().toString(), e2.getTime().toString());

            long result = d1 - d2;

            if (result > 0) {
                return 1;
            } else {
                return -1;
            }
        }

        private long formatTime(String date, String time) {
            String result = date.substring(6) + date.substring(3, 5) + date.substring(0, 2);
            String zone = time.substring(5);
            if (zone.equals(" a.m.")) {
                if (time.length() == 9) {
                    time = "0" + time;
                }
                String hour = time.substring(0, 2);
                if (hour.equals("12")) {
                    result = result + "00" + time.substring(3, 5);
                } else {
                    result = result + time.substring(0, 2) + time.substring(3, 5);
                }
            } else {
                if (time.length() == 9) {
                    time = "0" + time;
                }
                int changeTime = Integer.parseInt(time.substring(0, 2)) + 12;
                result = result + changeTime + time.substring(3, 5);
            }
            return Long.parseLong(result);
        }
    }.thenComparing(Object::toString);


    /**
     * Comparator to sort events in the events list in order of Completion.
     */
    private static Comparator<Event> completionComparator = new Comparator<Event>() {

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

    /**
     * Comparator to sort events in the events list in order of Completion.
     */
    private static Comparator<Event> priorityComparator = new Comparator<Event>() {

        @Override
        public int compare(Event e1, Event e2) {
            ArrayList<String> comparisonTag = new ArrayList<>();
            comparisonTag.add("Critical");
            comparisonTag.add("High");
            comparisonTag.add("Medium");
            comparisonTag.add("Low");
            comparisonTag.add("None");

            int index1 = comparisonTag.indexOf(e1.getTag().toString().split(" ")[1]);
            int index2 = comparisonTag.indexOf(e2.getTag().toString().split(" ")[1]);

            return index1 - index2;
        }
    }.thenComparing(Object::toString);


    private Title title;
    private Location location;
    private Description desc;
    private Date date;
    private Time time;
    private Tag tag;
    private boolean isDone;


    public Event(Title title, Date date, Location location, Description desc, Time time, Tag tag) {
        this.title = title;
        this.location = location;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.tag = tag;
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

    public String getMonthAndYear() {
        String dateString = date.toString().replace('/', '-');
        return dateString.substring(3);
    }

    public Location getLocation() {
        return location;
    }

    public Time getTime() {
        return time;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
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
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getTime().equals(getTime());
    }


    public static Comparator<Event> getTitleComparator() {
        return titleComparator;
    }

    public static Comparator<Event> getLocationComparator() {
        return locationComparator;
    }

    public static Comparator<Event> getDateComparator() {
        return dateComparator;
    }

    public static Comparator<Event> getCompletionComparator() {
        return completionComparator;
    }

    public static Comparator<Event> getPriorityComparator() {
        return priorityComparator;
    }


}
