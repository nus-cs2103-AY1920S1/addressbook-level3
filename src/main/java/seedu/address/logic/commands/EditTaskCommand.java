package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Description;
import seedu.address.model.EventTime;
import seedu.address.model.Model;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Edits the details of an existing task in the task list.
 */
public class EditTaskCommand extends Command {

    public static final String COMMAND_WORD = "editT";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Edits the details of a task. "
            + "Existing values will be overwritten by the input values. "
            + "Indicate only the parameters you want to change.\n"
            + "Parameters: "
            + "[TASK ID] "
            + "[" + PREFIX_GOODS + "DESCRIPTION] "
            + "[" + PREFIX_CUSTOMER + "CUSTOMER ID] "
            + "[" + PREFIX_DATETIME + "DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + "2 "
            + PREFIX_GOODS + "10 ice boxes of red groupers "
            + PREFIX_DATETIME + "10/12/2019\n";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_NOTHING_TO_EDIT = "At least one field need to be different to edit.";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_CANNOT_BE_EDITED = "Completed Task cannot be edited.";
    public static final String MESSAGE_DATE_IS_BEFORE = "Updated date of delivery cannot be before today.";

    private final int id;
    private final EditTaskDescriptor editTaskDescriptor;
    private final Clock clock;


    public EditTaskCommand(int id, EditTaskDescriptor editTaskDescriptor) {
        this.id = id;
        this.editTaskDescriptor = editTaskDescriptor;
        this.clock = Clock.systemDefaultZone();
    }

    /**
     * Creates an EditTaskCommand to edit the specified {@code Task} for unit testing.
     * Makes use of dependency injection for current time.
     *
     * @param fixedClock clock that is fixed and will always return the same instant.
     */
    public EditTaskCommand(int id, EditTaskDescriptor editTaskDescriptor, Clock fixedClock) {
        this.id = id;
        this.editTaskDescriptor = editTaskDescriptor;
        this.clock = fixedClock;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTask(id)) {
            throw new CommandException(Task.MESSAGE_INVALID_ID);
        }

        Task taskToEdit = model.getTask(id);

        //check task is completed, then it cannot be edited.
        if (taskToEdit.getStatus() == TaskStatus.COMPLETED) {
            throw new CommandException(MESSAGE_CANNOT_BE_EDITED);
        }

        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor, model);

        //if the updated date is not same as original and if date is not today onwards
        if (!taskToEdit.getDate().equals(editedTask.getDate()) && editedTask.getDate().isBefore(LocalDate.now(clock))) {
            throw new CommandException(MESSAGE_DATE_IS_BEFORE);
        }

        if (taskToEdit.isSameTask(editedTask)) {
            throw new CommandException(MESSAGE_NOTHING_TO_EDIT);
        }

        //if date is changed, the schedule of the driver will be free too.
        //task will become incomplete
        freeDriverIfDateIsChanged(taskToEdit, editedTask);

        model.setTask(taskToEdit, editedTask);

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Empty Driver's schedule if the date of delivery has been changed.
     *
     * @param taskToEdit original task.
     * @param editedTask updated task.
     */
    private static void freeDriverIfDateIsChanged(Task taskToEdit, Task editedTask) {
        if (taskToEdit.getDate() != editedTask.getDate() && taskToEdit.getDriver().isPresent()) {
            Driver driver = taskToEdit.getDriver().get();
            boolean isDeleteSuccess = driver.deleteFromSchedule(taskToEdit.getEventTime().get());
            assert isDeleteSuccess;
            editedTask.setStatus(TaskStatus.INCOMPLETE);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor,
                                         Model model) throws CommandException {
        assert taskToEdit != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDescription());

        //check if customer id provided is valid
        Optional<Integer> customerId = editTaskDescriptor.getCustomer();
        if (customerId.isPresent() && !model.hasCustomer(customerId.get())) {
            throw new CommandException(Customer.MESSAGE_INVALID_ID);
        }

        Customer updatedCustomer;
        if (customerId.isPresent()) {
            //get the new customer to be assigned from customer list.
            int updatedCustomerId = customerId.get();
            updatedCustomer = model.getCustomer(updatedCustomerId);
        } else {
            //get the original customer that is assigned to the task.
            updatedCustomer = taskToEdit.getCustomer();
        }

        LocalDate updatedDate = editTaskDescriptor.getDate().orElse(taskToEdit.getDate());

        Task editedTask = new Task(taskToEdit.getId(), updatedDescription, updatedDate);
        editedTask.setCustomer(updatedCustomer);

        //use the original driver and eventTime, no changes made to them.
        editedTask.setDriver(taskToEdit.getDriver());
        editedTask.setEventTime(taskToEdit.getEventTime());

        editedTask.setStatus(taskToEdit.getStatus());

        return editedTask;
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private Description description;
        private LocalDate date;
        private Integer driver;
        private Integer customer;
        private EventTime eventTime;


        public EditTaskDescriptor() {

        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setDescription(toCopy.description);
            setDate(toCopy.date);
            setDriver(toCopy.driver);
            setCustomer(toCopy.customer);
            setEventTime(toCopy.eventTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, date, driver, customer, eventTime);
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<Integer> getDriver() {
            return Optional.ofNullable(driver);
        }

        public void setDriver(int driver) {
            this.driver = driver;
        }

        public Optional<Integer> getCustomer() {
            return Optional.ofNullable(customer);
        }

        public void setCustomer(int customer) {
            this.customer = customer;
        }

        public Optional<EventTime> getEventTime() {
            return Optional.ofNullable(eventTime);
        }

        public void setEventTime(EventTime eventTime) {
            this.eventTime = eventTime;
        }

    }
}
