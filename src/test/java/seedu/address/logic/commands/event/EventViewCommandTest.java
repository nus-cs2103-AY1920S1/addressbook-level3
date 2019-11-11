package seedu.address.logic.commands.event;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.event.EventViewCommand.MESSAGE_VIEW_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.EventScheduleViewMode;

/**
 * Event view test. Integration testing between model manager and EventViewCommand.
 */
public class EventViewCommandTest {
    private static final EventScheduleViewMode VALID_EVENT_SCHEDULE_VIEW_MODE = EventScheduleViewMode.DAILY;
    private static final LocalDateTime VALID_TARGET_VIEW_DATE_TIME = LocalDateTime.parse("2019-01-01T03:00");

    private Model model = new ModelManager();

    @Test
    public void execute_validViewWithTargetDateAndViewMode_returnSuccessMessage() {
        Model expectedModel = new ModelManager();
        expectedModel.setEventScheduleTargetDateTime(VALID_TARGET_VIEW_DATE_TIME);
        expectedModel.setEventScheduleViewMode(VALID_EVENT_SCHEDULE_VIEW_MODE);

        EventViewCommand eventViewCommand = new EventViewCommand();
        eventViewCommand.setDesiredViewMode(VALID_EVENT_SCHEDULE_VIEW_MODE);
        eventViewCommand.setTargetViewDate(VALID_TARGET_VIEW_DATE_TIME);

        String expectedResult = String.format(MESSAGE_VIEW_SUCCESS,
                VALID_EVENT_SCHEDULE_VIEW_MODE.name().toLowerCase(),
                VALID_TARGET_VIEW_DATE_TIME.toLocalDate().toString());

        assertCommandSuccess(eventViewCommand, model,
                new CommandResult(expectedResult, CommandResultType.SHOW_SCHEDULE), expectedModel);
    }

    @Test
    public void setTargetViewDate_nullTargetViewDateTime_throwsNullPointerException() {
        EventViewCommand eventViewCommand = new EventViewCommand();
        assertThrows(NullPointerException.class, () -> eventViewCommand.setTargetViewDate(null));
    }

    @Test
    public void setDesiredViewMode_nullEventScheduleViewMode_throwsNullPointerException() {
        EventViewCommand eventViewCommand = new EventViewCommand();
        assertThrows(NullPointerException.class, () -> eventViewCommand.setDesiredViewMode(null));
    }
}
