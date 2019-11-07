package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import seedu.address.ui.stub.UiManagerStub;

class LogViewCommandTest {

    @Test
    void build_fullCommand_success() {
        assertDoesNotThrow(() -> {
            UiManagerStub uiManager = new UiManagerStub();
            Command command = LogViewCommand.newBuilder(uiManager)
                    .build();

            command.execute();
        });

    }
}
