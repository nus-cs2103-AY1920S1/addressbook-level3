package seedu.address.model.performance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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

    public static final String MESSAGE_NO_SUCH_EVENT = "%1$s event has not been created.\n"
            + "Please use the event command to create the event first.";
    public static final String MESSAGE_CONSTRAINTS = "Event name should not begin with a space.\n";
    public static final String MESSAGE_RECORD_EXISTS = "%1$s already has a record on %2$s for %3$s event.";

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
        requireNonNull(name);
        this.name = name.toLowerCase();
        this.records = new HashMap<>();
    }

    /**
     * Creates a type of event with the performances initialised already.
     * @param name of this event.
     * @param records to be included in this event.
     */
    public Event(String name, HashMap<Person, List<Record>> records) {
        requireAllNonNull(name, records);
        this.name = name.trim().toLowerCase();
        this.records = records;
    }

    /**
     * Returns true if the event has the same name as this event.
     */
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
     * Checks if the athlete already has a record on the given day for this event.
     * This prevents an athlete from having 2 records on the same day, under the same event.
     */
    public boolean doesAthleteHavePerformanceOn(AthletickDate athletickDate, Person athlete) {
        requireAllNonNull(athletickDate, athlete);
        List<Record> athleteRecords = getAthleteRecords(athlete);
        if (athleteRecords == null) {
            return false;
        }
        for (Record record : athleteRecords) {
            if (record.getDate().equals(athletickDate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the athlete has any record under this event.
     */
    public boolean hasPerson(Person athlete) {
        requireNonNull(athlete);
        AtomicBoolean hasPerson = new AtomicBoolean(false);
        records.forEach((person, recordList) -> {
            if (person.equals(athlete)) {
                hasPerson.set(true);
            }
        });
        return hasPerson.get();
    }

    /**
     * Sorts records based on AthletickDate.
     */
    private void sortAthleteRecords(Person athlete) {
        requireNonNull(athlete);
        List<Record> athleteRecords = getAthleteRecords(athlete);
        athleteRecords.sort(new Comparator<Record>() {
            @Override
            public int compare(Record first, Record second) {
                return AthletickDate.compareDate(first.getDate(), second.getDate());
            }
        });
    }

    /**
     * Returns true if both events have the same names.
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

    @Override
    public String toString() {
        return name;
    }

    //// Adding record functions

    /**
     * Adds a player's record to this event.
     */
    public void addRecord(Person athlete, Record record) {
        requireAllNonNull(athlete, record);
        if (!records.containsKey(athlete)) {
            addRecordNew(athlete, record);
        } else {
            addRecordExisting(athlete, record);
        }
        sortAthleteRecords(athlete);
    }

    /**
     * Adds a player's record to this event when the player has no existing records.
     */
    private void addRecordNew(Person athlete, Record record) {
        requireAllNonNull(athlete, record);
        ArrayList<Record> initialisedPerformanceEntries = new ArrayList<>();
        initialisedPerformanceEntries.add(record);
        records.put(athlete, initialisedPerformanceEntries);
    }

    /**
     * Adds a player's record to this event when the player already has existing records.
     */
    private void addRecordExisting(Person athlete, Record record) {
        requireAllNonNull(athlete, record);
        ArrayList<Record> currentPerformanceEntries = new ArrayList<>();
        currentPerformanceEntries.addAll(records.get(athlete));
        currentPerformanceEntries.add(record);
        records.remove(athlete);
        records.put(athlete, currentPerformanceEntries);
    }

    //// Deleting record functions

    /**
     * Removes a player's record from this event.
     * Since there can only be one record per day, only the date needs to be specified.
     */
    public void deleteRecord(Person athlete, AthletickDate date) {
        requireAllNonNull(athlete, date);
        assert(doesAthleteHavePerformanceOn(date, athlete));
        List<Record> athleteRecords = getAthleteRecords(athlete);
        int i = getIndexToDelete(athleteRecords, date);
        athleteRecords.remove(i);
        // delete athlete from HashMap if they have no records
        if (athleteRecords.isEmpty()) {
            records.remove(athlete);
        }
    }

    /**
     * Find the index of the record to be deleted.
     */
    public int getIndexToDelete(List<Record> athleteRecords, AthletickDate date) {
        requireAllNonNull(athleteRecords, date);
        int i = 0;
        for (Record record : athleteRecords) {
            if (record.getDate().equals(date)) {
                break;
            }
            i++;
        }
        return i;
    }

    //// Editing person functions

    /**
     * Updates the person details for this event with the edited person details.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        assert(hasPerson(target));
        List<Record> recordsCopy = copyAthleteRecords(target);
        records.remove(target);
        records.put(editedPerson, recordsCopy);
    }

    /**
     * Makes a copy of the athlete's records.
     */
    private List<Record> copyAthleteRecords(Person target) {
        List<Record> athleteRecordsToCopy = getAthleteRecords(target);
        List<Record> athleteRecordsCopy = new ArrayList<>();
        for (Record record : athleteRecordsToCopy) {
            Record recordCopy = new Record(record.getDate(), record.getTiming());
            athleteRecordsCopy.add(recordCopy);
        }
        assert(athleteRecordsCopy.size() == athleteRecordsToCopy.size());
        return athleteRecordsCopy;
    }

    //// Analysis helper functions

    public List<Record> getAthleteRecords(Person athlete) {
        requireNonNull(athlete);
        List<Record> athleteRecords = records.get(athlete);
        assert(!athleteRecords.isEmpty());
        return athleteRecords;
    }

    /**
     * Retrieves the athlete's fastest timing for this event.
     */
    public String[] getPersonalBest(Person athlete) {
        requireNonNull(athlete);
        double personalBest = Double.MAX_VALUE;
        String personalBestDate = "";
        for (Record record : getAthleteRecords(athlete)) {
            double timing = record.getTiming().getValue();
            if (timing < personalBest) {
                personalBest = timing;
                personalBestDate = record.getDate().toString();
            }
        }
        assert(personalBest < Double.MAX_VALUE);
        return new String[]{personalBest + " seconds", personalBestDate};
    }

    /**
     * Retrieves the athlete's latest timing for this event.
     * @return String array with the first index being the date and second index being the timing.
     */
    public String[] getLatestTiming(Person athlete) {
        requireNonNull(athlete);
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
        requireNonNull(date);
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
        requireNonNull(date);
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
