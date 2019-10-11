package seedu.address.model.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.person.Person;
import seedu.address.model.training.Training;

public class Attendance {
    private static List<Training> trainings = new ArrayList<>();
    
    public Attendance(Training training) {
        trainings.add(training);
    }
    
    public List<Training> getTrainings() {
        return trainings;
    }
    
    //returns the attendance rate of the person
    public String viewPersonAttendance(Person name) {
        int attended = 0;
        int missed = 0;
        double result;
        for (Training training: trainings) {
            if (training.getPersonAttendance(name)) {
                attended ++;
            } else {
                missed ++;
            }
        }
        
        result = ((double) attended / (attended + missed));
        return result * 100 + "%";
    }
    
    //prints the attendance of all players on this date
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
    
    
    //get the list of person who has attended the training for the date
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
