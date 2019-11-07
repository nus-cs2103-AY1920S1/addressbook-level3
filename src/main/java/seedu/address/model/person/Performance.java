package seedu.address.model.person;

import seedu.address.model.person.exceptions.MeetingNotFoundException;
import seedu.address.model.person.exceptions.TaskNotFoundException;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Performance {
    private final HashMap<String, List<Meeting>> meetingsAttended;
    private final HashMap<String, List<Task>> taskAssignment;

    public Performance() {
        this.meetingsAttended = new HashMap<>();
        this.taskAssignment = new HashMap<>();
    }

    public Performance(HashMap<String, List<Meeting>> meetingsAttended, HashMap<String, List<Task>> taskAssignment) {
        this.meetingsAttended = meetingsAttended;
        this.taskAssignment = taskAssignment;
    }

    public Performance setMeetingsAttended(HashMap<String, List<Meeting>> updatedMeetingsAttended) {
        return new Performance(updatedMeetingsAttended, this.taskAssignment);
    }

    public Performance setTasksAssigned(HashMap<String, List<Task>> updatedTaskAssignment) {
        return new Performance(this.meetingsAttended, updatedTaskAssignment);
    }

    public HashMap<String, List<Meeting>> getMeetingsAttended() {
        return meetingsAttended;
    }

    public HashMap<String, List<Task>> getTaskAssignment() {
        return taskAssignment;
    }


    public int numOfTasksDone(Project project) {
        List<Task> tasksAssigned = taskAssignment.get(project.getTitle().title);

        int tasksDone = tasksAssigned.stream()
                .filter(task -> task.isDone())
                .collect(Collectors.toList())
                .size();
        return tasksDone;
    }

    public int numOfTaskAssigned(Project project) {
        return taskAssignment.get(project.getTitle().title).size();
    }

    public int numOfMeetingsAttended(Project project) {
        return meetingsAttended.get(project.getTitle().title).size();
    }

    public void setTask(Task taskToEdit, Task editedTask, String projectTitle) {
        List<Task> tasksAssigned = taskAssignment.get(projectTitle);

        if (!tasksAssigned.contains(taskToEdit)) {
            throw new TaskNotFoundException();
        }

        tasksAssigned.set(tasksAssigned.indexOf(taskToEdit), editedTask);
    }

    public void deleteTask(Task taskToDelete, String projectTitle) {
        List<Task> tasksAssigned = taskAssignment.get(projectTitle);
        if (!tasksAssigned.contains(taskToDelete)) {
            throw new TaskNotFoundException();
        }

        tasksAssigned.remove(taskToDelete);
    }

    public void deleteMeeting(Meeting meeting, String projectTitle) {
        List<Meeting> meetingsAttendedList = meetingsAttended.get(projectTitle);
        if (!meetingsAttendedList.contains(meeting)) {
            throw new MeetingNotFoundException();
        }

        meetingsAttendedList.remove(meeting);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Performance)) {
            return false;
        }

        Performance otherPerformance = (Performance) other;

        return otherPerformance.meetingsAttended.equals(this.meetingsAttended)
                && otherPerformance.taskAssignment.equals(this.taskAssignment);
    }
}
