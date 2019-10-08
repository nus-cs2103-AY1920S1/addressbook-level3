package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Semester;

/**
 * Jackson-friendly version of {@link Semester}.
 */
public class JsonAdaptedHolidays {
    private final List<String> holidayDates = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedHolidays}.
     */
    @JsonCreator
    public JsonAdaptedHolidays(@JsonProperty("holidayDates") List<String> holidayDates) {
        if (holidayDates != null) {
            this.holidayDates.addAll(holidayDates);
        }
    }

    /**
     * Converts a given {@code Holidays} into this class for Jackson use.
     */
    public JsonAdaptedHolidays(Holidays source) {
        holidayDates.addAll(source.getHolidayDates());
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Semester} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted semester.
     */
    public Holidays toModelType() throws IllegalValueException {
        final List<String> modelHolidays = new ArrayList<>();
        for (String date : holidayDates) {
            modelHolidays.add(date);
        }

        return new Holidays(modelHolidays);
    }
}
