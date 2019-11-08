package seedu.address.logic.optimizer;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.GlobalClock;
import seedu.address.model.Model;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

/**
 * An optimizer to suggest the best driver and best time for a task, based on rules and their priorities.
 * The optimizer is guaranteed not modifying the model, and is not designed to persist.
 * <p>
 * It exposes a convenient {@code ScheduleOptimizer#start} method to apply the rules and return the result.
 */
public class ScheduleOptimizer {
    private Model model;
    private Task task;
    private Duration duration;

    /**
     * Constructs the optimizer object with the needed information.
     *
     * @param model    the model which contains the state of the app
     * @param task     the task to assign
     * @param duration the proposed duration of the task
     */
    public ScheduleOptimizer(Model model, Task task, Duration duration) {
        this.model = model;
        this.task = task;
        this.duration = duration;
    }

    /**
     * Finds the global optimum Driver-EventTime pair, based on the rules and priorities.
     *
     * @return an Optional of the pair if a suitable driver can be found; otherwise, an empty Optional
     */
    public Optional<Candidate> start() {
        return this
                // preferred
                .prioritizeSameCustomer()
                // fall back
                .or(this::driverEarliestFit)
                // in case other methods didn't check for this
                .filter(candidate -> candidate.getValue().isPresent());
    }

    /**
     * Finds the driver who has an earliest time slot to fit the proposed task, and the time slot as well.
     *
     * @return an Optional of the pair if a suitable driver can be found; otherwise, an empty Optional
     */
    private Optional<Candidate> driverEarliestFit() {
        List<Driver> driverList = model.getDriverManager().getDriverList();
        return driverList.stream()
                // for every driver, find whether he is able to block this duration
                .map(driver -> new Candidate(driver, driver.suggestTime(duration, GlobalClock.timeNow())))

                // filter out the candidate who has no available time
                .filter(candidate -> candidate.getValue().isPresent())

                // find the earliest driver-time pair
                .min(Candidate.comparator());
    }

    /**
     * Finds the driver who is already delivering to the same customer as the incoming task, and checks whether
     * he can take up the task.
     *
     * @return an Optional of the pair if a suitable driver can be found; otherwise, an empty Optional
     */
    private Optional<Candidate> prioritizeSameCustomer() {
        List<Task> taskList = model.getTaskManager().getList();

        return taskList.stream()
                // find all the task whose customer is the same as the requested task
                .filter(t -> t.getCustomer().equals(task.getCustomer()))

                // get a distinct stream of drivers of the above tasks
                .map(Task::getDriver)
                .flatMap(Optional::stream) // get the list of drivers
                .distinct()

                // same as driverEarliestFit
                .map(driver -> new Candidate(driver, driver.suggestTime(duration, GlobalClock.timeNow())))
                .filter(candidate -> candidate.getValue().isPresent())
                .min(Candidate.comparator());
    }

}
