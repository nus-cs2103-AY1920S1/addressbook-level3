package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a NUStudy revision task. Its
 */
public abstract class Task {

    static final DateTimeFormatter FORMAT_FILE_DATE_STRING = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    static final DateTimeFormatter FORMAT_FILE_TIME_STRING = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FORMAT_USER_INPUT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMAT_USER_INPUT_TIME = DateTimeFormatter.ofPattern("HHmm");


    private boolean isDone;
    private LocalDate date;
    private LocalTime time;



    Task(String date, String time) {
        requireNonNull(date);
        this.isDone = false;
        this.date = LocalDate.parse(date, FORMAT_USER_INPUT_DATE);
        this.time = LocalTime.parse(time, FORMAT_USER_INPUT_TIME);
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
}
