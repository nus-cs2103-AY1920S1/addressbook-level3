package seedu.address.testutil;

import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.DueDate;
import seedu.address.model.deadline.Task;
import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building FlashCard objects.
 */
public class DeadlineBuilder {

    private static final String VALID_TASK_STR = "Complete Deadline Scheduler.";
    private static final String VALID_TASK_STR_2 = "Complete Calendar GUI.";
    private static final String VALID_DUEDATE_STR = "01/10/2019";
    private static final String VALID_DUEDATE_STR_2 = "11/10/2019";
    private static final Task VALID_TASK = new Task(VALID_TASK_STR);
    private static final DueDate VALID_DUEDATE = new DueDate(VALID_DUEDATE_STR);
    private static final Task VALID_TASK_2 = new Task(VALID_TASK_STR_2);
    private static final DueDate VALID_DUEDATE_2 = new DueDate(VALID_DUEDATE_STR_2);

    private Task task;
    private DueDate dueDate;

    public DeadlineBuilder() {
        task = new Task(VALID_TASK_STR);
        dueDate = new DueDate(VALID_DUEDATE_STR);
    }

    /**
     * Initializes the FlashCardBuilder with the data of {@code flashCardToCopy}.
     */
    public DeadlineBuilder(Deadline deadlineToCopy) {
        task = deadlineToCopy.getTask();
        dueDate = deadlineToCopy.getDueDate();
    }

    /**
     * Sets the {@code Question} of the {@code FlashCard} that we are building.
     */
    public DeadlineBuilder withTask(String name) {
        this.task = new Task(name);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code FlashCard} that we are building.
     */
    public DeadlineBuilder withDueDate(String date) {
        this.dueDate = new DueDate(date);
        return this;
    }


    public Deadline build() {
        return new Deadline(task, dueDate);
    }

}
