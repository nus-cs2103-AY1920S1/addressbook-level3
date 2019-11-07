package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    private static final String PARTIAL_COMMAND = COMMAND.substring(0, 2);

    private static final List<Prefix> REQUIRED = List.of(
            new Prefix("a/", "a"),
            new Prefix("b/", "b")
    );

    private static final List<Prefix> OPTIONAL = List.of(
            new Prefix("c/", "c")
    );

    private CommandTextField styleClassedTextArea;

    private SimpleStringProperty commandPropertyStub;

    private static void pushKey(KeyCode keyCode, EventTarget target) {
        KeyEvent.fireEvent(
                target,
                new KeyEvent(KeyEvent.KEY_PRESSED, null, null, keyCode,
                        false, false, false, false));
        KeyEvent.fireEvent(
                target,
                new KeyEvent(KeyEvent.KEY_RELEASED, null, null, keyCode,
                        false, false, false, false));
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        styleClassedTextArea = new CommandTextField(string -> {});
        stage.setScene(new Scene(new VBox(styleClassedTextArea)));
        commandPropertyStub = new SimpleStringProperty("");
        sutWithOneCommand = new AutofillSuggestionMenu(styleClassedTextArea, commandPropertyStub);
        sutWithOneCommand.addCommand(COMMAND, REQUIRED, OPTIONAL);
        styleClassedTextArea.setContextMenu(sutWithOneCommand);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        if (sutWithOneCommand.enabledProperty().get()) {
            sutWithOneCommand.toggle();
        }
    }

    @AfterEach
    void tearDown() {
        Platform.runLater(() -> {
            styleClassedTextArea.clear();
            commandPropertyStub.setValue("");
        });

        if (!sutWithOneCommand.enabledProperty().get()) {
            sutWithOneCommand.toggle();
        }
    }

    @Test
    void withCommand_enabledAndMatchesCommandWord_shows() {
        sutWithOneCommand.toggle();
        new FxRobot().write(COMMAND);
        Platform.runLater(() -> assertTrue(sutWithOneCommand.isShowing()));
    }

    @Test
    void withCommand_enabledAndMatchingPrefix_shows() {
        sutWithOneCommand.toggle();
        new FxRobot().write(COMMAND);
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(" a");
        Platform.runLater(() -> assertTrue(sutWithOneCommand.isShowing()));
    }

    @Test
    void leftArrowKeyHidesMenu() {
        sutWithOneCommand.toggle();
        new FxRobot().write(COMMAND);
        new FxRobot().interact(() -> {
            pushKey(KeyCode.LEFT, sutWithOneCommand);
            assertFalse(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void rightArrowKeyHidesMenu() {
        sutWithOneCommand.toggle();
        new FxRobot().write(COMMAND);
        new FxRobot().interact(() -> {
            pushKey(KeyCode.RIGHT, sutWithOneCommand);
            assertFalse(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void show_noTextHasCommand_show() {
        sutWithOneCommand.toggle();
        Platform.runLater(() -> {
            sutWithOneCommand.show(styleClassedTextArea, Side.BOTTOM, 0,0);
            assertTrue(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void show_noTextHasCommand_show1() {
        sutWithOneCommand.toggle();
        Platform.runLater(() -> {
            sutWithOneCommand.show(styleClassedTextArea, 0,0);
            assertTrue(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void show_noTextHasCommand_show2() {
        sutWithOneCommand.toggle();

        Platform.runLater(() -> {
            sutWithOneCommand.show(window(styleClassedTextArea), 0,0);
            assertTrue(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void show_noTextHasCommand_show3() {
        sutWithOneCommand.toggle();
        Platform.runLater(() -> {
            sutWithOneCommand.show(window(styleClassedTextArea));
            assertTrue(sutWithOneCommand.isShowing());
        });
    }

    @Test
    void show_partialMatch_populatesListAndShows0() {
        sutWithOneCommand.toggle();

        new FxRobot().write(PARTIAL_COMMAND);
        Platform.runLater(() -> {
            sutWithOneCommand.show(styleClassedTextArea, 0, 0);
            assertTrue(sutWithOneCommand.isShowing());
            assertTrue(sutWithOneCommand.getItems().size() > 0);
        });
    }

    @Test
    void show_partialMatch_populatesListAndShows1() {
        sutWithOneCommand.toggle();

        new FxRobot().write(PARTIAL_COMMAND);
        Platform.runLater(() -> {
            sutWithOneCommand.show(styleClassedTextArea, Side.BOTTOM, 0, 0);
            assertTrue(sutWithOneCommand.isShowing());
            assertTrue(sutWithOneCommand.getItems().size() > 0);
        });
    }

    @Test
    void show_partialMatch_populatesListAndShows2() {
        sutWithOneCommand.toggle();

        new FxRobot().write(PARTIAL_COMMAND);
        Platform.runLater(() -> {
            sutWithOneCommand.show(window(styleClassedTextArea), 0,0);
            assertTrue(sutWithOneCommand.isShowing());
            assertTrue(sutWithOneCommand.getItems().size() > 0);
        });
    }

    @Test
    void show_partialMatch_populatesListAndShows3() {
        sutWithOneCommand.toggle();

        new FxRobot().write(PARTIAL_COMMAND);
        Platform.runLater(() -> {
            sutWithOneCommand.show(window(styleClassedTextArea));
            assertTrue(sutWithOneCommand.isShowing());
            assertTrue(sutWithOneCommand.getItems().size() > 0);
        });
    }

    @Test
    void actionEvent_requiredPrefix1_appendsChoice() {
        sutWithOneCommand.toggle();

        new FxRobot().write(COMMAND);
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(" ");
        new FxRobot().interact(() -> {
            ActionEvent.fireEvent(
                    sutWithOneCommand.getItems().get(0),
                    new ActionEvent());
        });
        Platform.runLater(() -> assertEquals(COMMAND + " a/", styleClassedTextArea.getText()));
    }

    @Test
    void actionEvent_requiredPrefix2_appendsChoice() {
        sutWithOneCommand.toggle();

        new FxRobot().write(COMMAND);
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(" ");
        new FxRobot().interact(() -> {
            ActionEvent.fireEvent(
                    sutWithOneCommand.getItems().get(1),
                    new ActionEvent());
        });
        Platform.runLater(() -> assertEquals(COMMAND + " b/", styleClassedTextArea.getText()));
    }

    @Test
    void actionEvent_allMissing_appendsChoice() {
        sutWithOneCommand.toggle();
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(COMMAND + " ");
        new FxRobot().interact(() -> {
            ActionEvent.fireEvent(
                    sutWithOneCommand.getItems().get(REQUIRED.size()),
                    new ActionEvent());
            assertEquals(COMMAND + " a/ b/", styleClassedTextArea.getText());
        });

    }

    @Test
    void actionEvent_optionalPrefix1_appendsChoice() {
        sutWithOneCommand.toggle();
        commandPropertyStub.setValue(COMMAND);
        new FxRobot().write(COMMAND);
        new FxRobot().write(" ");
        new FxRobot().interact(() -> {
            ActionEvent.fireEvent(
                    sutWithOneCommand.getItems().get(4),
                    new ActionEvent());
        });
        Platform.runLater(() -> assertEquals(COMMAND + " c/", styleClassedTextArea.getText()));
    }
}