package seedu.address.logic.commands.event;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.event.EventScreenshotCommand.MESSAGE_SCREENSHOT_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResultType;
import seedu.address.testutil.model.ModelStub;

/**
 * Event screenshot command test
 */
public class EventScreenshotCommandTest {
    /**
     * Test valid screenshot command
     */
    @Test
    public void execute_validScreenShot_returnsScheduleScreenshotCommandResult() {
        ModelStubWithEventSchedulePrefString modelStub = new ModelStubWithEventSchedulePrefString();

        EventScreenshotCommand eventScreenshotCommand = new EventScreenshotCommand();

        assertCommandSuccess(eventScreenshotCommand, modelStub, MESSAGE_SCREENSHOT_SUCCESS,
                modelStub, CommandResultType.SCHEDULE_SCREENSHOT);
    }

    /**
     * A Model stub that will return the EventSchedulePrefString
     */
    private class ModelStubWithEventSchedulePrefString extends ModelStub {
        public static final String STUBBED_PREF_STRING = "stubbedPrefString";
        @Override
        public String getEventSchedulePrefString() {
            return STUBBED_PREF_STRING;
        }

    }
}
