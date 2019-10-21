package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.note.Note;
import seedu.address.model.task.exceptions.RedundantOperationException;

/**
 * Represents a NUStudy revision task. Its
 */
public abstract class Task {
    public static final String MESSAGE_DATE_CONSTRAINT = "Please follow Singapore local date format 'dd/MM/yyyy',"
            + "with 1 <= dd <= 31, 1 <= MM <= 12, -9999 < yyyy < 9999";
    public static final String MESSAGE_TIME_CONSTRAINT = "Please follow Singapore local time format 'HH/mm',"
            + "with 00 <= HH <= 23, 00 <= mm <= 59";
    public static final DateTimeFormatter FORMAT_FILE_DATE_STRING = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final DateTimeFormatter FORMAT_FILE_TIME_STRING = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter FORMAT_USER_INPUT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMAT_USER_INPUT_TIME = DateTimeFormatter.ofPattern("HHmm");


    private boolean isDone;
    private LocalDate date;
    private LocalTime time;

    Task(LocalDate date, LocalTime time) {
        requireNonNull(date);
        this.isDone = false;
        this.date = date;
        this.time = time;
    }

    public Task(LocalDate date, LocalTime time, boolean isDone) {
        requireNonNull(date);
        this.isDone = isDone;
        this.date = date;
        this.time = time;
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
    public abstract String toString();

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public abstract boolean equals(Object other);

    public abstract Note getNote();

    public abstract void setNote(Note note);

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
