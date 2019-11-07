package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.moolah.logic.parser.Prefix;

/**
 * AutofillSuggestionMenuTest for AutofillSuggestionMenu with no supported command but has one added later.
 */
class AutofillSuggestionMenuTest2 extends ApplicationTest {

    private static final String COMMAND = "COMMAND";
    private static final List<Prefix> REQUIRED = Collections.emptyList();
    private static final List<Prefix> OPTIONAL = Collections.emptyList();

    private AutofillSuggestionMenu menuForAddingCommand;
    private CommandTextField stubToAdd;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        stubToAdd = new CommandTextField(string -> {
        });
        stage.setScene(new Scene(new VBox(stubToAdd)));
        SimpleStringProperty commandPropertyStub = new SimpleStringProperty("");
        stubToAdd.setContextMenu(menuForAddingCommand);
        menuForAddingCommand = new AutofillSuggestionMenu(stubToAdd, commandPropertyStub);
        stage.show();
    }


    @BeforeEach
    void setUp() {
        if (!menuForAddingCommand.enabledProperty().get()) {
            menuForAddingCommand.toggle();
        }
    }

    @AfterEach
    void cleanUp() {
        Platform.runLater(() -> stubToAdd.clear());
    }

    @Test
    void addCommand_showsWhenMatches() {
        assertFalse(menuForAddingCommand.isShowing());
        menuForAddingCommand.addCommand(COMMAND, REQUIRED, OPTIONAL);
        new FxRobot().write(COMMAND.substring(0, 2));
        new FxRobot().interact(() -> {
            assertTrue(menuForAddingCommand.isShowing());
            ActionEvent.fireEvent(menuForAddingCommand.getItems().get(0), new ActionEvent());
            assertEquals(COMMAND, stubToAdd.getText());
        });
    }
}
