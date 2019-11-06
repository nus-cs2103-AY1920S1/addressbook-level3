package seedu.address.model.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
/**
 * Represents a Training
 * Guarantees: date and training attendance for the particular training
 */
public class Training {

    private AthletickDate date;
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

    /**
     * Returns a list of Person who has attended this training
     */
    public List<Person> getAttended() {
        List<Person> listOfAttended = new ArrayList<>();
        for (Map.Entry<Person, Boolean> set: this.trainingAttendance.entrySet()) {
            Person name = set.getKey();
            Boolean hasAttended = set.getValue();
            if (hasAttended) {
                listOfAttended.add(name);
            }
        }
        return listOfAttended;
    }

    /**
     * Returns a list of Person who has attended this training
     */
    public List<Person> getAbsentees() {
        List<Person> listOfAbsentees = new ArrayList<>();
        for (Map.Entry<Person, Boolean> set: this.trainingAttendance.entrySet()) {
            Person name = set.getKey();
            Boolean hasAttended = set.getValue();
            if (!hasAttended) {
                listOfAbsentees.add(name);
            }
        }
        return listOfAbsentees;
    }
    @Override
    public String toString() {
        return "Training on " + date;
    }
    /**
     * Replaces person data in the training record. Called when a person is edited.
     */
    public void editPersonDetails(Person target, Person editedPerson) {
        assert(this.hasPerson(target)); // done in other calls

        boolean hasAttended = this.hasPersonAttended(target);
        this.trainingAttendance.remove(target);
        this.trainingAttendance.put(editedPerson, hasAttended);
    }
}
