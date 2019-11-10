package seedu.address.testutil;

import java.util.Calendar;

import seedu.address.logic.parser.ParserUtil;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.ClassName;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;


/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {
    public static final String DEFAULT_CLASSNAME = "Chemistry 2E6";

    private ClassName className;
    private Time startTime;
    private Time endTime;

    public LessonBuilder() {
        className = new ClassName(DEFAULT_CLASSNAME);
        Calendar start = Calendar.getInstance();
        start.set(2020, 1, 12, 12, 0);
        startTime = new Time(start);
        Calendar end = Calendar.getInstance();
        end.set(2020, 1, 12, 13, 0);
        endTime = new Time(end);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        className = lessonToCopy.getName();
        startTime = lessonToCopy.getStartTime();
        endTime = lessonToCopy.getEndTime();
    }

    /**
     * Sets the {@code ClassName} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withClassName(String name) {
        this.className = new ClassName(name);
        return this;
    }


    /**
     * Sets the {@code StartTime} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withStartTime(String time) {
        try {
            this.startTime = ParserUtil.parseTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * Sets the {@code EndTime} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withEndTime(String time) {
        try {
            this.endTime = ParserUtil.parseTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }


    public Lesson build() {
        return new Lesson(startTime, endTime, className);
    }

}
