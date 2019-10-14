package io.xpire.logic.commands;

import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;

import org.junit.jupiter.api.BeforeEach;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code TagCommand}.
 * TODO: Implement Tests
 */
public class TagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());
    }

}
