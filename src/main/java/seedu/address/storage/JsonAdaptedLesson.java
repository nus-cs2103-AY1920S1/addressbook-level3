package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.lesson.ClassName;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;

public class JsonAdaptedLesson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";

    private final String className;
    private final String startTime;
    private final String endTime;
    private final String isRepeat;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("className") String className, @JsonProperty("startTime") String startTime,
                              @JsonProperty("endTime") String endTime, @JsonProperty("isRepeat") String isRepeat) {
        this.className = className;
        this.startTime = startTime;
        this.endTime = endTime;
        if (isRepeat != null) {
            this.isRepeat = isRepeat;
        } else {
            this.isRepeat = "Not repeated";
        }
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        className = source.getName().toString();
        startTime = source.getStartTime().getStringTime();
        endTime = source.getEndTime().getStringTime();
        isRepeat = source.getRepeatString();
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (className == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClassName.class.getSimpleName()));
        }
        if (!ClassName.isValidClassName(className)) {
            throw new IllegalValueException(ClassName.MESSAGE_CONSTRAINTS);
        }
        final ClassName modelClassName = new ClassName(className);

        if (startTime == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName())));
        }
        if (!Time.isValidTime(startTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelStartTime = ParserUtil.parseTime(startTime);

        if (endTime == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName())));
        }
        if (!Time.isValidTime(endTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelEndTime = ParserUtil.parseTime(endTime);

        Lesson lesson = new Lesson(modelStartTime, modelEndTime, modelClassName);

        if (isRepeat!= null) {
            lesson.setRepeat();
        }
        return lesson;
    }
}
