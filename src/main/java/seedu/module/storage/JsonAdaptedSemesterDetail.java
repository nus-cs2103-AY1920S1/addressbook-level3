package seedu.module.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.module.model.module.SemesterDetail;

/**
 * Jackson-friendly version of {@code SemesterDetail}
 */
public class JsonAdaptedSemesterDetail {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // format used by NUSmods

    private final int semester;
    private final String examDate; // ISO standard datetime format
    private final int examDuration; // duration in minutes

    /**
     * Constructs a {@code JsonAdaptedSemesterDetail} with the given parameters.
     */
    public JsonAdaptedSemesterDetail(@JsonProperty("semester") int semester,
        @JsonProperty("examDate") String examDate,
        @JsonProperty("examDuration") int examDuration) {
        this.semester = semester;
        this.examDate = examDate;
        this.examDuration = examDuration;
    }

    /**
     * Converts this Jackson-friendly semester detail object into the model's {@code SemesterDetail} object.
     */
    public SemesterDetail toModelType() throws DateTimeParseException {
        // null indicates that the semester has no exam
        if (examDate == null) {
            return new SemesterDetail(semester, null, examDuration);
        }

        return new SemesterDetail(semester, LocalDateTime.parse(examDate, format), examDuration);
    }
}
