package seedu.address.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.model.training.AttendanceEntry;
import seedu.address.model.training.Training;

/**
 * Represents the AttendanceManager of Athletick.
 */
public class Attendance {

    private List<Training> trainings;

    public Attendance() {
        this.trainings = new ArrayList<>();
    }

    public Attendance(List<Training> trainings) {
        this.trainings = trainings;
    }

    /**
     * Resets all data in the Attendance.
     */
    public void resetAttendance() {
        this.trainings = new ArrayList<>();
    }

    /**
     * Add a training to the AttendanceManager.
     *
     * @param training Training to add.
     */
    public void addTraining(Training training) {
        if (this.hasTrainingOnDate(training.getDate())) {
            this.deleteTrainingOnDate(training.getDate());
        }
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

    /**
     * Checks if there has been a Training at input date.
     *
     * @param date Date used to check for training
     * @return boolean indicating whether there has been a Training at the date.
     */
    public boolean hasTrainingOnDate(AthletickDate date) {
        for (Training recordedTraining : trainings) {
            if (recordedTraining.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a training on the specified date.
     * @param date Training that occurred on this date will be removed.
     */
    public void deleteTrainingOnDate(AthletickDate date) {
        assert(hasTrainingOnDate(date));
        int index = 0;
        while (index < trainings.size()) {
            Training training = trainings.get(index);
            if (training.getDate().equals(date)) {
                break;
            }
            index++;
        }
        trainings.remove(index);
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public int getPersonAttendedTrainings(Person person) {
        int attended = 0;
        for (Training training : trainings) {
            if (training.hasPerson(person)) {
                if (training.hasPersonAttended(person)) {
                    attended++;
                }
            }
        }
        return attended;
    }

    public int getPersonAbsentTrainings(Person person) {
        int absent = 0;
        for (Training training : trainings) {
            if (training.hasPerson(person)) {
                if (!training.hasPersonAttended(person)) {
                    absent++;
                }
            }
        }
        return absent;
    }

    public int getPersonTotalTrainings(Person person) {
        int total = 0;
        for (Training training : trainings) {
            if (training.hasPerson(person)) {
                total++;
            }
        }
        return total;
    }

    public double getPersonAttendanceRate(Person person) {
        return ((double) getPersonAttendedTrainings(person) / getPersonTotalTrainings(person));
    }

    /**
     * Returns the attendance rate of a given person name
     */
    public String getPersonAttendanceRateString(Person person) {
        int attended = getPersonAttendedTrainings(person);
        int total = getPersonTotalTrainings(person);
        if (total == 0) { // Has not had the opportunity to go for any trainings yet
            return "No training records";
        } else {
            double result = getPersonAttendanceRate(person);
            return String.format("%d/%d (%.2f%%)", attended, total, result * 100);
        }
    }

    public List<AttendanceEntry> getTrainingAttendanceListOnDate(AthletickDate date) {
        assert(this.hasTrainingOnDate(date));
        for (Training training : trainings) {
            if (date.equals(training.getDate())) {
                return training.getTrainingAttendanceList();
            }
        }
        return null; // With assertion, code should not reach here.
    }
    public Training getTrainingOnDate(AthletickDate date) {
        assert (this.hasTrainingOnDate(date));
        for (Training training : trainings) {
            if (date.equals(training.getDate())) {
                return training;
            }
        }
        return null; // With assertion, code should not reach here.
    }
}
