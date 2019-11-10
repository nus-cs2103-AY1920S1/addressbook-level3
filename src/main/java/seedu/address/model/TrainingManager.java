package seedu.address.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.model.training.AttendanceEntry;
import seedu.address.model.training.Training;

/**
 * Represents the TrainingManager of Athletick.
 */
public class TrainingManager {

    private final List<Training> trainings;

    public TrainingManager() {
        trainings = new ArrayList<>();
    }

    public TrainingManager(List<Training> trainings) {
        this.trainings = trainings;
    }

    /**
     * Resets all data in the TrainingManager.
     */
    public void resetTrainingManager() {
        this.trainings.clear();
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
                return AthletickDate.compareDate(firstDate, secondDate);
            }
        });
    }

    /**
     * Replaces all occurences of person at {@code target} with {@code editedPerson} in training records.
     */
    void editPersonTrainingRecords(Person target, Person editedPerson) {
        for (Training training: trainings) {
            if (training.hasPerson(target)) {
                training.editPersonDetails(target, editedPerson);
            }
        }
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
    public Training deleteTrainingOnDate(AthletickDate date) {
        assert(hasTrainingOnDate(date));
        int index = 0;
        while (index < trainings.size()) {
            Training training = trainings.get(index);
            if (training.getDate().equals(date)) {
                break;
            }
            index++;
        }
        Training trainingToBeDeleted = trainings.remove(index);
        return trainingToBeDeleted;
    }

    public List<Training> getTrainings() {
        return this.trainings;
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

    public List<AttendanceRateEntry> getAttendanceRateOfAll(List<Person> persons) {
        List<AttendanceRateEntry> attendanceRateEntries = new ArrayList<>();
        for (Person person : persons) {
            attendanceRateEntries.add(new AttendanceRateEntry(person, getPersonAttendanceRateString(person)));
        }
        return attendanceRateEntries;
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

    /**
     * Resets trainings in TrainingManager to the {@code newTrainingList}.
     */
    public void resetTrainingList(List<Training> newTrainingList) {
        this.resetTrainingManager();
        this.trainings.addAll(newTrainingList);
    }
}
