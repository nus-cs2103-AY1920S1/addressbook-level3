package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.task.exceptions.RedundantOperationException;

/**
 * Represents a NUStudy revision task.
 */
public class Task {
    public static final String MESSAGE_DATE_CONSTRAINT = "Please follow Singapore local date format 'dd/MM/yyyy',"
            + "with 1 <= dd <= 31, 1 <= MM <= 12, -9999 < yyyy < 9999";
    public static final String MESSAGE_TIME_CONSTRAINT = "Please follow Singapore local time format 'HH/mm',"
            + "with 00 <= HH <= 23, 00 <= mm <= 59";
    public static final DateTimeFormatter FORMAT_FILE_DATE_STRING = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final DateTimeFormatter FORMAT_FILE_TIME_STRING = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter FORMAT_USER_INPUT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMAT_USER_INPUT_TIME = DateTimeFormatter.ofPattern("HHmm");

    protected Heading heading;
    private boolean isDone;
    private LocalDate date;
    private LocalTime time;

    Task(LocalDate date, LocalTime time) {
        requireAllNonNull(date, time);
        this.isDone = false;
        this.date = date;
        this.time = time;
    }

    public Task(Heading heading, LocalDate date, LocalTime time, boolean isDone) {
        requireNonNull(date);
        this.heading = heading;
        this.date = date;
        this.time = time;
        this.isDone = isDone;
    }

    public static boolean isValidStatusIcon(String status) {
        return status.equals("[Y]") || status.equals("[N]");
    }

    /**
     * Gets status icon.
     *
     * @return "Y" if done. "N" if not done.
     */
    public String getStatusIcon() {
        return (isDone ? "[" + "Y" + "]" : "[" + "N" + "]");
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + heading.toString() + " by: "
                + getDate().format(FORMAT_FILE_DATE_STRING) + " "
                + getTime().format(FORMAT_FILE_TIME_STRING);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Heading getHeading() {
        return heading;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return heading.equals(otherTask.getHeading())
                && getDate().equals(otherTask.getDate())
                && getTime().equals(otherTask.getTime())
                && getStatusIcon().equals(otherTask.getStatusIcon());
    }

    /**
     * Marks the task as done.
     *
     * @return The done task object.
     */
    public Task markAsDone() {
        if (this.isDone) {
            throw new RedundantOperationException("The task has already been marked done");
        }
        this.isDone = true;
        return this;
    }
}
