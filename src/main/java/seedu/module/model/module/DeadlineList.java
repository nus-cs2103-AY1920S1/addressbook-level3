package seedu.module.model.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the DeadlineList of a TrackedModule.
 */
public class DeadlineList {
    private List<Deadline> deadlineList = new ArrayList<>();

    /**
     * Adds the deadline object to deadline list.
     * @param deadline
     */
    public void addDeadline (Deadline deadline) {
        this.deadlineList.add(deadline);
    }

    /**
     * Returns the deadline list.
     * @return deadline list.
     */
    public List<Deadline> getDeadlineList() {
        return deadlineList;
    }

    /**
     * Deletes the specified deadline task from list.
     */
    public void deleteDeadlineTask(int taskListNum) {
        this.deadlineList.remove(taskListNum);
    }

    public void markDeadlineTaskAsDone(int taskListNum) {
        deadlineList.get(taskListNum).markAsDone();
    }

    public void markDeadlineTaskAsInProgress(int taskListNum) {
        deadlineList.get(taskListNum).markAsInProgress();
    }

    /**
     * Returns the string representation of deadline list.
     * @return string deadline list as String.
     */
    public String toString() {
        String deadlineString = "Deadline: \n";
        for (int i = 0; i < deadlineList.size(); i++) {
            deadlineString += ((i + 1) + ". " + deadlineList.get(i)) + "\n";
        }
        return deadlineString;
    }
}
