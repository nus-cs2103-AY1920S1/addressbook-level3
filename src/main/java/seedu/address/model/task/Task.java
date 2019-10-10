package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a NUStudy revision task. Its
 */
public abstract class Task {
    private boolean isDone;
    protected LocalDate date;
    protected LocalTime time;

    private static final DateTimeFormatter FORMAT_USER_INPUT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected static final DateTimeFormatter FORMAT_FILE_DATE_STRING = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    private static final DateTimeFormatter FORMAT_USER_INPUT_TIME = DateTimeFormatter.ofPattern("HHmm");
    protected static final DateTimeFormatter FORMAT_FILE_TIME_STRING = DateTimeFormatter.ofPattern("HH:mm");

    public Task(String date, String time) {
        requireAllNonNull(date);
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
}
