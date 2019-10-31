package organice.model.person;

import java.util.ArrayList;

/**
 * Represents the checklist for donor and patient in ORGANice.
 * When a donor and patient is matched, they will be processed and a TaskList will be generated containing all the
 * necessary tasks both patient and donor need to do to prepare for a cross-matching
 */
public class TaskList {

    public final String value;

    protected ArrayList<Task> listOfTask;
    protected Patient patient;

    public TaskList(String tasklist) {
        this.listOfTask = new ArrayList<Task>();
        value = listOfTask.toString();
    }

    public void add(Task task) {
        listOfTask.add(task);
    }

    public int size() {
        return listOfTask.size();
    }

    public Task get(int index) {
        return listOfTask.get(index);
    }

    public void remove(int index) {
        listOfTask.remove(index);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient newPatient) {
        this.patient = newPatient;
    }

    /**
     * Generate a default list with all the default tasks necessary for the cross-matching to occur
     * @return TaskList of the default list
     */
    public TaskList defaultList() {
        TaskList processingTodoList = new TaskList("");
        processingTodoList.add(new Task("Contact donor"));
        processingTodoList.add(new Task("Contact patient's family"));
        processingTodoList.add(new Task("Contact doctor in charge of patient"));
        processingTodoList.add(new Task("Schedule for cross-matching"));
        processingTodoList.add(new Task("Schedule for organ transplant surgery"));
        return processingTodoList;
    }

    /**
     * Generate a string of all the tasks in the task list with the task number and title
     * @return a string of tasks
     */
    public String display() {
        int count = 1;
        String tasks = "";
        for (int i = 0; i < listOfTask.size(); i++) {
            Task t = listOfTask.get(i);
            tasks = tasks + count + "." + t.toString() + "\n";
            count++;
        }
        return ("Here are the tasks in for the patient and donor:\n" + tasks);
    }

    @Override
    public String toString() {
        String tasks = "";
        for (int i = 0; i < listOfTask.size(); i++) {
            Task t = listOfTask.get(i);
            tasks = tasks + t.toString() + "\n";
        }
        return tasks;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && listOfTask.equals(((TaskList) other).listOfTask)); // state check
    }

    @Override
    public int hashCode() {
        return listOfTask.hashCode();
    }
}
