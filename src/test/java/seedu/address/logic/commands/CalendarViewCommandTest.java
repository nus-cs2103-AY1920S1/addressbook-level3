package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.commands.CalendarViewCommandBuilder.OPTION_CALENDAR_SCREEN_DATE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.stub.UiManagerStub;

class CalendarViewCommandTest {
    @Test
    void build_fullCommand_success() {
        String calendarDate = "11/1111";
        assertDoesNotThrow(() -> {
            UiManagerStub uiManager = new UiManagerStub();
            Command command = CalendarViewCommand.newBuilder(uiManager)
                    .acceptSentence(OPTION_CALENDAR_SCREEN_DATE)
                    .acceptSentence(calendarDate)
                    .build();

            command.execute();
        });

    }

    @Test
    void build_emptyCommand_success() {
        assertDoesNotThrow(() -> {
            UiManagerStub uiManager = new UiManagerStub();
            Command command = CalendarViewCommand.newBuilder(uiManager)
                    .build();

            command.execute();
        });
    }
}
