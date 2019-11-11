package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.UiManager;
import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.ui.stub.UiManagerStub;

class WeekViewCommandTest {

    @Test
    void build_fullCommand_success() {
        String calendarDate = "11/11/1111";
        assertDoesNotThrow(() -> {
            UiManagerStub uiManager = new UiManagerStub();

            Command command = WeekViewCommand.newBuilder(uiManager)
                    .acceptPhrase(calendarDate)
                    .build();

            command.execute();
        });
    }

    @Test
    void execute_emptyCommand_failure() {
        assertThrows(ArgumentException.class, () -> {
            UiManager uiManager = new UiManager();

            Command command = WeekViewCommand.newBuilder(uiManager)
                    .build();

            command.execute();
        });
    }
}
