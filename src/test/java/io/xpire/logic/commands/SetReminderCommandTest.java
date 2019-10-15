package io.xpire.logic.commands;

import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;

import org.junit.jupiter.api.BeforeEach;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SetReminderCommand}.
 * TODO: Implement Tests
 */
public class SetReminderCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());
    }

}
