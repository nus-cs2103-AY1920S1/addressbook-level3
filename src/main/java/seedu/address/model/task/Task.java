package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import seedu.address.model.note.Note;

/**
 * Represents a NUStudy revision task. Its
 */
public abstract class Task {
    public static final String MESSAGE_DATE_CONSTRAINT = "Please follow Singapore local date format 'dd/MM/yyyy'," +
            "with 1 <= dd <= 31, 1 <= MM <= 12, -9999 < yyyy < 9999";
    public static final String MESSAGE_TIME_CONSTRAINT = "Please follow Singapore local time format 'HH/mm'," +
            "with 00 <= HH <= 23, 00 <= mm <= 59";
    static final DateTimeFormatter FORMAT_FILE_DATE_STRING = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    static final DateTimeFormatter FORMAT_FILE_TIME_STRING = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FORMAT_USER_INPUT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMAT_USER_INPUT_TIME = DateTimeFormatter.ofPattern("HHmm");


    private boolean isDone;
    private LocalDate date;
    private LocalTime time;



    Task(LocalDate date, LocalTime time) {
        requireNonNull(date);
        this.isDone = false;
//        this.date = LocalDate.parse(date, FORMAT_USER_INPUT_DATE);
//        this.time = LocalTime.parse(time, FORMAT_USER_INPUT_TIME);
        this.date = date;
        this.time = time;
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
}
