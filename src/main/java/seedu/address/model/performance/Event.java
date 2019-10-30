package seedu.address.model.performance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;

/**
 * Events are types of activities that can measure performance. Examples include '50m breaststroke' or '100m freestyle'.
 */
public class Event {

    public static final String MESSAGE_CONSTRAINTS = "%1$s event has not been created.\n"
            + "Please use the event command to create the event first.";

    public static final String INVALID_NAME_MESSAGE_CONSTRAINTS = "Event name should not begin with a space.\n";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String name;
    private HashMap<Person, List<Record>> records;

    /**
     * Creates a type of event that stores the members and their respective timings (performance) for this event.
     * @param name of this event.
     */
    public Event(String name) {
        this.name = name.toLowerCase();
        this.records = new HashMap<>();
    }

    /**
     * Creates a type of event with the performances initialised already.
     * @param name of this event.
     * @param records to be included in this event.
     */
    public Event(String name, HashMap<Person, List<Record>> records) {
        this.name = name.trim().toLowerCase();
        this.records = records;
    }

    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }
        return otherEvent != null && otherEvent.getName().equals(name);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getName() {
        return name;
    }

    public HashMap<Person, List<Record>> getRecords() {
        return records;
    }

    /**
     * Adds a player's performance to this event.
     */
    public void addPerformance(Person athlete, Record record) {
        if (!records.containsKey(athlete)) {
            ArrayList<Record> initialisedPerformanceEntries = new ArrayList<>();
            initialisedPerformanceEntries.add(record);
            records.put(athlete, initialisedPerformanceEntries);
        } else {
            ArrayList<Record> currentPerformanceEntries = new ArrayList<>();
            currentPerformanceEntries.addAll(records.get(athlete));
            currentPerformanceEntries.add(record);
            records.remove(athlete);
            records.put(athlete, currentPerformanceEntries);
        }
        sortAthleteRecords(athlete);
    }

    /**
     * Sorts records based on AthletickDate
     */
    private void sortAthleteRecords(Person athlete) {
        List<Record> athleteRecords = getAthleteRecords(athlete);
        athleteRecords.sort(new Comparator<Record>() {
            @Override
            public int compare(Record first, Record second) {
                AthletickDate firstDate = first.getDate();
                AthletickDate secondDate = second.getDate();

                if (!(firstDate.getYear() == secondDate.getYear())) {
                    return firstDate.getYear() - secondDate.getYear();
                } else if (!(firstDate.getMonth() == secondDate.getMonth())) {
                    return firstDate.getMonth() - secondDate.getMonth();
                } else {
                    return firstDate.getDay() - secondDate.getDay();
                }
            }
        });
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(name);
    }

    //// Analysis helper functions

    public List<Record> getAthleteRecords(Person athlete) {
        List<Record> athleteRecords = records.get(athlete);
        assert(!athleteRecords.isEmpty());
        return athleteRecords;
    }

    /**
     * Retrieves the athlete's fastest timing for this event.
     */
    public double getPersonalBest(Person athlete) {
        double personalBest = Double.MAX_VALUE;
        for (Record record : getAthleteRecords(athlete)) {
            double timing = record.getTiming().getValue();
            if (timing < personalBest) {
                personalBest = timing;
            }
        }
        assert(personalBest < Double.MAX_VALUE);
        return personalBest;
    }

    /**
     * Retrieves the athlete's latest timing for this event.
     * @return String array with the first index being the date and second index being the timing.
     */
    public String[] getLatestTiming(Person athlete) {
        List<Record> athleteRecords = getAthleteRecords(athlete);
        Record latestRecord = athleteRecords.get(athleteRecords.size() - 1);
        return new String[]{latestRecord.getDate().toString(), latestRecord.getTiming().toString()};
    }

    //// Calendar helper functions

    /**
     * Retrieves a list of Calendar-compatible records for the Calendar.
     * @param date of Calendar-compatible records.
     */
    public List<CalendarCompatibleRecord> getCalendarCompatibleRecords(AthletickDate date) {
        List<CalendarCompatibleRecord> ccrList = new ArrayList<>();
        records.forEach((person, recordList) -> {
            String timing = "";
            for (Record record : recordList) {
                if (record.getDate().equals(date)) {
                    timing = record.getTiming().toString();
                    CalendarCompatibleRecord ccr = new CalendarCompatibleRecord(person, timing);
                    ccrList.add(ccr);
                }
            }
        });
        return ccrList;
    }

    /**
     * Checks if this event has a recorded performance on the given date.
     */
    public boolean hasPerformanceOn(AthletickDate date) {
        AtomicBoolean answer = new AtomicBoolean(false);
        records.forEach((person, recordList) -> {
            for (Record record : recordList) {
                if (record.getDate().equals(date)) {
                    answer.set(true);
                    break;
                }
            }
        });
        return answer.get();
    }

}
