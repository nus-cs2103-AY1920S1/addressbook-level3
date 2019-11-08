package seedu.address.model.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
/**
 * Represents a Training session in Athletick.
 */
public class Training {

    /**
     * Represents the date of Training.
     */
    private AthletickDate date;

    /**
     * Represents the attendance record of the training session. Each person is a key and a boolean value indicates
     * whether they attended or not.
     */
    private HashMap<Person, Boolean> trainingAttendance;

    public Training(AthletickDate date, HashMap<Person, Boolean> trainingAttendance) {
        this.date = date;
        this.trainingAttendance = trainingAttendance;
    }

    public AthletickDate getDate() {
        return this.date;
    }

    public HashMap<Person, Boolean> getTrainingAttendance() {
        return this.trainingAttendance;
    }

    /**
     * Returns a list of {@code AttendanceEntry} to show attendance of a team in that training. Used in the calendar
     * function.
     */
    public List<AttendanceEntry> getTrainingAttendanceList() {
        List<AttendanceEntry> trainingAttendanceList = new ArrayList<>();
        trainingAttendance.forEach((person, isPresent) -> {
            trainingAttendanceList.add(new AttendanceEntry(person, isPresent));
        });
        trainingAttendanceList.sort((firstEntry, secondEntry) -> {
            return firstEntry.getPerson().getName().toString().compareTo(secondEntry.getPerson().getName().toString());
        });
        return trainingAttendanceList;
    }

    public boolean hasPerson(Person person) {
        return this.trainingAttendance.containsKey(person);
    }

    public boolean hasPersonAttended(Person person) {
        return this.trainingAttendance.get(person);
    }

    @Override
    public String toString() {
        return "Training on " + date;
    }

    /**
     * Replaces person data in the training record. Called when a person's details are edited.
     */
    public void editPersonDetails(Person target, Person editedPerson) {
        assert(this.hasPerson(target)); // done in other calls

        boolean hasAttended = this.hasPersonAttended(target);
        this.trainingAttendance.remove(target);
        this.trainingAttendance.put(editedPerson, hasAttended);
    }
}
