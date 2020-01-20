package seedu.address.model.performanceoverview;

import seedu.address.model.project.Task;

import java.util.ArrayList;
import java.util.List;

public class RateOfTaskCompletion {
    private final List<Task> taskList = new ArrayList<>();

    public RateOfTaskCompletion(List<Task> taskList) {
        this.taskList.addAll(taskList);
    }

    private double getCompletionRate() {
        if (taskList.isEmpty()) {
            //Rate of completion is 0 if no task is done or assigned
            return 0;
        }

        double numOfTasksAssigned = taskList.size();
        long numOfCompletedTasks = taskList.stream().filter(task -> task.isDone).count();

        double completionRate = ((double) numOfCompletedTasks / numOfTasksAssigned) * 100;
        return completionRate;
    }

    public double getRate() {
        return getCompletionRate();
    }

    public String getRateAsString() {
        return String.format("%.1f", getRate());
    }
}
