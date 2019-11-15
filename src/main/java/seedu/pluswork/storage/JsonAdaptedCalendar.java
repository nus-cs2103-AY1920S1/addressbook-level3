package seedu.pluswork.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.fortuna.ical4j.model.Calendar;
import seedu.pluswork.commons.exceptions.IllegalValueException;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.member.MemberName;

/**
 * Jackson-friendly version of {@link Calendar}.
 */
class JsonAdaptedCalendar {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Calendar's %s field is missing!";

    private final String memberName;
    private final String calendarStorageFormat;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedCalendar(@JsonProperty("memberName") String memberName,
                               @JsonProperty("calendarString") String calendarStorageFormat) {
        this.memberName = memberName;
        this.calendarStorageFormat = calendarStorageFormat;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedCalendar(CalendarWrapper source) {
        memberName = source.getMemberName().fullName;
        calendarStorageFormat = source.getCalendarStorageFormat();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public CalendarWrapper toModelType() throws IllegalValueException {
        if (memberName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MemberName.class.getSimpleName()));
        }
        if (calendarStorageFormat == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Calendar.class.getSimpleName()));
        }
        if (!MemberName.isValidMemberName(memberName)) {
            throw new IllegalValueException(MemberName.MESSAGE_CONSTRAINTS);
        }
        final MemberName modelMemberName = new MemberName(memberName);
        try {
            final Calendar modelCalendar = ParserUtil.parseCalendar(calendarStorageFormat);
            final CalendarWrapper modelCalendarWrapper =
                    new CalendarWrapper(modelMemberName, modelCalendar, calendarStorageFormat);
            return modelCalendarWrapper;
        } catch (ParseException e) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Calendar.class.getSimpleName()));
        }
    }

}
