package organice.model.person;

import java.util.ArrayList;

/**
 * Represents the checklist for donor and patient in ORGANice.
 * When a donor and patient is matched, they will be processed and a TaskList will be generated containing all the
 * necessary tasks both patient and donor need to do to prepare for a cross-matching
 */
public class TaskList {

    protected ArrayList<Task> listOfTask;

    public TaskList() {
        this.listOfTask = new ArrayList<Task>();
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

    public TaskList defaultList() {
        TaskList processingTodoList = new TaskList();
        processingTodoList.add(new Task("Contact donor"));
        processingTodoList.add(new Task("Contact patient's family"));
        processingTodoList.add(new Task("Contact doctor in charge of patient"));
        processingTodoList.add(new Task("Schedule for cross-matching"));
        processingTodoList.add(new Task("Schedule for organ transplant surgery"));
        return processingTodoList;
    }

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
