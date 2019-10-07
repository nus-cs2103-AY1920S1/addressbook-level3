package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Day;
import seedu.address.model.module.EndTime;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.LessonType;
import seedu.address.model.module.StartTime;
import seedu.address.model.module.Venue;
import seedu.address.model.module.Weeks;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";

    private String lessonNo;
    private String startTime;
    private String endTime;
    private String weeks;
    private String lessonType;
    private String day;
    private String venue;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("lessonNo") String lessonNo,
                               @JsonProperty("startTime") String startTime,
                               @JsonProperty("endTime") String endTime,
                               @JsonProperty("weeks") String weeks,
                               @JsonProperty("lessonType") String lessonType,
                               @JsonProperty("day") String day,
                               @JsonProperty("venue") String venue) {
        this.lessonNo = lessonNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weeks = weeks;
        this.lessonType = lessonType;
        this.day = day;
        this.venue = venue;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        lessonNo = source.getLessonNo().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        weeks = source.getWeeks().toString();
        lessonType = source.getLessonType().toString();
        day = source.getDay().toString();
        venue = source.getVenue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (lessonNo == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LessonNo.class.getSimpleName()));
        }
        final LessonNo modelLessonNo = new LessonNo(lessonNo);

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartTime.class.getSimpleName()));
        }
        final StartTime modelStartTime = new StartTime(startTime);

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndTime.class.getSimpleName()));
        }
        final EndTime modelEndTime = new EndTime(endTime);

        if (weeks == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Weeks.class.getSimpleName()));
        }
        final Weeks modelWeeks = new Weeks(weeks);

        if (lessonType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LessonType.class.getSimpleName()));
        }
        final LessonType modelLessonType = new LessonType(lessonType);

        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Day.class.getSimpleName()));
        }
        final Day modelDay = new Day(day);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Venue.class.getSimpleName()));
        }
        final Venue modelVenue = new Venue(venue);

        return new Lesson(modelLessonNo, modelStartTime, modelEndTime, modelWeeks,
                modelLessonType, modelDay, modelVenue);
    }
}
