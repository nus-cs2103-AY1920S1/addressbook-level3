package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.moolah.logic.parser.Prefix;

class AutofillSuggestionMenuTest2 extends ApplicationTest {

    private AutofillSuggestionMenu sutEmptyForAdding;

    private static final String command = "COMMAND";
    private static final List<Prefix> required = Collections.emptyList();
    private static final List<Prefix> optional = Collections.emptyList();

    private StyleClassedTextArea stubToAdd;

    private static void pushKey(KeyCode keyCode, EventTarget target) {
        Event.fireEvent(
                target,
                new KeyEvent(KeyEvent.KEY_PRESSED, null, null, keyCode,
                        false, false, false, false));
        Event.fireEvent(
                target,
                new KeyEvent(KeyEvent.KEY_RELEASED, null, null, keyCode,
                        false, false, false, false));
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        stubToAdd = new StyleClassedTextArea();
        stage.setScene(new Scene(new VBox(stubToAdd)));
        SimpleStringProperty commandPropertyStub = new SimpleStringProperty("");
        stubToAdd.setContextMenu(sutEmptyForAdding);
        sutEmptyForAdding = new AutofillSuggestionMenu(stubToAdd, commandPropertyStub);
        stage.show();
    }


    @AfterEach
    void cleanUp() {
        Platform.runLater(() -> stubToAdd.clear());
    }

    @Test
    void initiallyEmpty() {
        new FxRobot().write(command);
        Platform.runLater(() -> assertFalse(sutEmptyForAdding.isShowing()));
    }

    @Test
    void addCommand_showsWhenMatches() {
        sutEmptyForAdding.addCommand(command, required, optional);
        new FxRobot().write(command.substring(0,2));
        new FxRobot().interact(() -> {
            ActionEvent.fireEvent(sutEmptyForAdding.getItems().get(0), new ActionEvent());
            assertEquals(command, stubToAdd.getText());
        });
        Platform.runLater(() -> assertTrue(sutEmptyForAdding.isShowing()));
    }
}