package seedu.pluswork.model.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pluswork.model.settings.ClockFormat.DISPLAY_NAME_TWELVE;
import static seedu.pluswork.model.settings.ClockFormat.DISPLAY_NAME_TWENTY_FOUR;

import org.junit.jupiter.api.Test;

import seedu.pluswork.commons.util.DateTimeUtil;

/**
 * Since settings data is stored in the enums itself, ensure that the expected data is retrieved.
 */
class ClockFormatTest {

    @Test
    void getDisplayName_checksStoredData_success() {
        assertEquals(ClockFormat.TWELVE.getDisplayName(), DISPLAY_NAME_TWELVE);
        assertEquals(ClockFormat.TWENTY_FOUR.getDisplayName(), DISPLAY_NAME_TWENTY_FOUR);
    }

    @Test
    void getDisplayFormatter_checkStoredData_success() {
        assertEquals(ClockFormat.TWELVE.getDisplayFormatter(), DateTimeUtil.getDisplayFormatterTwelveHour());
        assertEquals(ClockFormat.TWENTY_FOUR.getDisplayFormatter(), DateTimeUtil.getDisplayFormatterTwentyFourHour());
    }
}
