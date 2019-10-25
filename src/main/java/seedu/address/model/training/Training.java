package seedu.address.model.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.person.Person;
/**
 * Represents a Training
 * Guarantees: date and training attendance for the particular training
 */
public class Training {
    private String date;
    private HashMap<Person, Boolean> trainingAttendance;
    public Training(String date, HashMap<Person, Boolean> trainingAttendance) {
        this.date = date;
        this.trainingAttendance = trainingAttendance;
    }
    public String getDate() {
        return this.date;
    }
    public HashMap<Person, Boolean> getTrainingAttendance() {
        return this.trainingAttendance;
    }
    public boolean getPersonAttendance(Person name) {
        return this.trainingAttendance.get(name);
    }
    /**
     * Returns a list of person who has attended this training
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
}
