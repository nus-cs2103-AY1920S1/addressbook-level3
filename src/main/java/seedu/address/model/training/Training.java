package seedu.address.model.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.person.Person;

public class Training {
    private String date;
    private HashMap<Person, Boolean> trainingAttendance;
    public String getDate() {
        return this.date;
    }
    public HashMap<Person, Boolean> getTrainingAttendance() {
        return this.trainingAttendance;
    }
    public boolean getPersonAttendance(Person name) {
        return this.trainingAttendance.get(name);
    }
    public Training(String date, HashMap<Person, Boolean> trainingAttendance) {
        this.date = date;
        this.trainingAttendance = trainingAttendance;
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
