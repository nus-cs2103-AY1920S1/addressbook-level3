package seedu.address.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.model.training.Training;
/**
 * Represents the attendance of a person
 * Guarantees: a static list of trainings
 */
public class Attendance {

    private List<Training> trainings;

    public Attendance() {
        this.trainings = new ArrayList<>();
    }

    public Attendance(List<Training> trainings) {
        this.trainings = trainings;
    }

    public boolean hasTraining(Training training) {
        AthletickDate date = training.getDate();
        return hasTraining(date);
    }

    public boolean hasTraining(AthletickDate date) {
        for (Training recordedTraining: trainings) {
            if (recordedTraining.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    public void addTraining(Training training) {
        this.trainings.add(training);
        this.trainings.sort(new Comparator<Training>() {
            @Override
            public int compare(Training first, Training second) {
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

    public List<Training> getTrainings() {
        return trainings;
    }

    /**
     * Returns the attendance rate of a given person name
     */
    public String getPersonAttendance(Person person) {
        int attended = 0;
        int total = 0;
        double result;
        for (Training training: trainings) {
            if (!training.hasPerson(person)) {
                continue;
            }
            if (training.hasPersonAttended(person)) {
                attended++;
            }
            total++;
        }
        if (total == 0) { // Has not had the opportunity to go for any trainings yet
            return "No training records";
        } else {
            result = ((double) attended / total);
            return String.format("%d/%d (%.2f%%)", attended, total, result * 100);
        }
    }
    /**
     * Prints out the list of athlete with their attendance in the given date
     */
    public void selectTraining(String date) {
        for (Training training: trainings) {
            if (date.equals(training.getDate())) {
                HashMap<Person, Boolean> attendanceOfTraining = training.getTrainingAttendance();
                for (Map.Entry<Person, Boolean> set : attendanceOfTraining.entrySet()) {
                    Person name = set.getKey();
                    Boolean hasAttended = set.getValue();
                    System.out.println(name + " : " + hasAttended);
                }
                break;
            }
        }
    }
    /**
     * Returns a list of person who have attended the training at the given date
     */
    public List<Person> getAttended(String date) {
        List<Person> listOfAttended = new ArrayList<>();
        for (Training training: trainings) {
            if (date.equals(training.getDate())) {
                listOfAttended = training.getAttended();
                break;
            }
        }
        return listOfAttended;
    }
}
