package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.ClassName;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;


/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_CLASS_NAME = "4C3 English";
    public static final String DEFAULT_LESSON_START_TIME = "31/12/2019 1200";
    public static final String DEFAULT_LESSON_END_TIME = "31/12/2019 1400";
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");

    private ClassName className;
    private Time lessonStartTime;
    private Time lessonEndTime;

    public LessonBuilder() {
        sdf.setLenient(false);
        className = new ClassName(DEFAULT_CLASS_NAME);
        try {
            lessonStartTime = parseTime(DEFAULT_LESSON_START_TIME);
            lessonEndTime = parseTime(DEFAULT_LESSON_END_TIME);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the Lesson with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        sdf.setLenient(false);
        className = lessonToCopy.getName();
        lessonStartTime = lessonToCopy.getStartTime();
        lessonEndTime = lessonToCopy.getEndTime();
    }

    /**
     * Parses time from string into Time.
     * @param time to parse into Time.
     * @return Time object
     * @throws ParseException if the format is wrong, which will not be in this case.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        sdf.setLenient(false);
        Date date;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(time);
            calendar.setTime(date);
        } catch (java.text.ParseException e) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(calendar);
    }


    /**
     * Sets the {@code ClassName} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withClassName(String className) {
        this.className = new ClassName(className);
        return this;
    }

    /**
     * Sets the {@code Time} and set it to the {@code lessonStartTime} that we are building.
     */
    public LessonBuilder withStartTime(String startTime) {
        try {
            this.lessonStartTime = parseTime(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Time} and set it to the {@code lessonEndTime} that we are building.
     */
    public LessonBuilder withEndTime(String endTime) {
        try {
            this.lessonEndTime = parseTime(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Lesson build() {
        return new Lesson(lessonStartTime, lessonEndTime, className);
    }

}
