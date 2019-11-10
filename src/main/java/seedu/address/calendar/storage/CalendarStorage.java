package seedu.address.calendar.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.calendar.model.ReadOnlyCalendar;
import seedu.address.commons.exceptions.DataConversionException;

public interface CalendarStorage {

    Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException;

    void saveCalendar(ReadOnlyCalendar calendar) throws IOException;
}
