package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.moolah.logic.parser.Prefix;

class AutofillSuggestionMenuTest1 extends ApplicationTest {

    private AutofillSuggestionMenu sutWithOneCommand;

    private static final String COMMAND = "COMMAND";

    private static final List<Prefix> REQUIRED = List.of(
            new Prefix("a/", "a"),
            new Prefix("b/", "b")
    );

    private static final List<Prefix> OPTIONAL = List.of(
            new Prefix("c/", "c")
    );

    private StyleClassedTextArea stubToShow;

    private SimpleStringProperty commandPropertyStub;

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
        stubToShow = new StyleClassedTextArea();
        stage.setScene(new Scene(new VBox(stubToShow)));
        commandPropertyStub = new SimpleStringProperty("");
        sutWithOneCommand = new AutofillSuggestionMenu(stubToShow, commandPropertyStub);
        sutWithOneCommand.addCommand(COMMAND, REQUIRED, OPTIONAL);
        stubToShow.setContextMenu(sutWithOneCommand);
        stage.show();
    }

    @AfterEach
    void clear() {
        Platform.runLater(() -> {
            stubToShow.clear();
            commandPropertyStub.setValue("");
        });
    }

    @Test
    void withCommand_showsWhenMatchesCommandWord() {
        new FxRobot().write(COMMAND);
        Platform.runLater(() -> assertTrue(sutWithOneCommand.isShowing()));
    }

    @Test
    void withCommand_showsWhenMatches() {
        new FxRobot().write(COMMAND);
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(" a");
        Platform.runLater(() -> assertTrue(sutWithOneCommand.isShowing()));
    }

    @Test
    void leftArrowKeyHidesMenu() {
        new FxRobot().write(COMMAND);
        new FxRobot().interact(() -> {
            pushKey(KeyCode.LEFT, sutWithOneCommand);
            assertFalse(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void rightArrowKeyHidesMenu() {
        new FxRobot().write(COMMAND);
        new FxRobot().interact(() -> {
            pushKey(KeyCode.RIGHT, sutWithOneCommand);
            assertFalse(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void upArrowKeyHidesMenu() {
        new FxRobot().write(COMMAND);
        new FxRobot().interact(() -> {
            pushKey(KeyCode.UP, sutWithOneCommand);
            assertFalse(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void downArrowKeyHidesMenu() {
        new FxRobot().write(COMMAND);
        new FxRobot().interact(() -> {
            pushKey(KeyCode.DOWN, sutWithOneCommand);
            assertFalse(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void show_noText_showDoesNotShow0() {
        sutWithOneCommand.show(stubToShow, Side.BOTTOM, 0,0);
        Platform.runLater(() -> assertFalse(sutWithOneCommand.isShowing()));
    }

    @Test
    void show_noText_showDoesNotShow1() {
        sutWithOneCommand.show(stubToShow, 0,0);
        Platform.runLater(() -> assertFalse(sutWithOneCommand.isShowing()));
    }

    @Test
    void show_noText_showDoesNotShow2() {
        Platform.runLater(() -> {
            sutWithOneCommand.show(window(stubToShow), 0,0);
            assertFalse(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void show_noText_showDoesNotShow3() {
        sutWithOneCommand.show(window(stubToShow));
        Platform.runLater(() -> assertFalse(sutWithOneCommand.isShowing()));
    }

    @Test
    void show_partialMatch_populatesListAndShows0() {
        new FxRobot().write(COMMAND.substring(0,2));
        Platform.runLater(() -> {
            sutWithOneCommand.show(stubToShow, 0, 0);
            assertTrue(sutWithOneCommand.isShowing());
            assertTrue(sutWithOneCommand.getItems().size() > 0);
        });
    }

    @Test
    void show_partialMatch_populatesListAndShows1() {
        new FxRobot().write(COMMAND.substring(0,2));
        Platform.runLater(() -> {
            sutWithOneCommand.show(stubToShow, Side.BOTTOM, 0, 0);
            assertTrue(sutWithOneCommand.isShowing());
            assertTrue(sutWithOneCommand.getItems().size() > 0);
        });
    }

    @Test
    void show_partialMatch_populatesListAndShows2() {
        new FxRobot().write(COMMAND.substring(0,2));
        Platform.runLater(() -> {
            sutWithOneCommand.show(window(stubToShow), 0,0);
            assertTrue(sutWithOneCommand.isShowing());
            assertTrue(sutWithOneCommand.getItems().size() > 0);
        });
    }

    @Test
    void show_partialMatch_populatesListAndShows3() {
        new FxRobot().write(COMMAND.substring(0,2));
        Platform.runLater(() -> {
            sutWithOneCommand.show(window(stubToShow));
            assertTrue(sutWithOneCommand.isShowing());
            assertTrue(sutWithOneCommand.getItems().size() > 0);
        });
    }

        @Test
    void actionEvent_requiredPrefix1_appendsChoice() {
        new FxRobot().write(COMMAND);
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(" ");
        new FxRobot().interact(() -> {
            ActionEvent.fireEvent(
                    sutWithOneCommand.getItems().get(0),
                    new ActionEvent());
        });
        Platform.runLater(() -> assertEquals(COMMAND + "  a/", stubToShow.getText()));
    }

    @Test
    void actionEvent_requiredPrefix2_appendsChoice() {
        new FxRobot().write(COMMAND);
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(" ");
        new FxRobot().interact(() -> {
            ActionEvent.fireEvent(
                    sutWithOneCommand.getItems().get(1),
                    new ActionEvent());
        });
        Platform.runLater(() -> assertEquals(COMMAND + "  b/", stubToShow.getText()));
    }

    @Test
    void actionEvent_allMissing_appendsChoice() {
        new FxRobot().write(COMMAND);
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(" ");
        new FxRobot().interact(() -> {
            ActionEvent.fireEvent(
                    sutWithOneCommand.getItems().get(2),
                    new ActionEvent());
        });
        Platform.runLater(() -> assertEquals(COMMAND + " a/ b/", stubToShow.getText()));
    }

    @Test
    void actionEvent_optionalPrefix1_appendsChoice() {
        new FxRobot().write(COMMAND);
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(" ");
        new FxRobot().interact(() -> {
            ActionEvent.fireEvent(
                    sutWithOneCommand.getItems().get(4),
                    new ActionEvent());
        });
        Platform.runLater(() -> assertEquals(COMMAND + "  c/", stubToShow.getText()));
    }
}