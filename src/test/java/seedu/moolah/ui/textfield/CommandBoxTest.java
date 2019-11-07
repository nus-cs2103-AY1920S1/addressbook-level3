package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.ui.textfield.CommandTextField.COMMAND_WORD_STYLE;
import static seedu.moolah.ui.textfield.CommandTextField.SYNTAX_HIGHLIGHTING_CSS_FILE_PATH;

import java.util.Collection;
import java.util.Collections;

import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

class CommandBoxTest extends ApplicationTest {

    private static final String COMMAND = "command";
    private CommandBox commandBox;
    private FxRobot robot;
    private CommandBox.CommandExecutor commandExecutorStub;
    private Runnable resetSut;

    /**
     * Helper method to fire a press and release KeyEvent with a specified KeyCode at the Command Box's textfield.
     */
    void pushKey(KeyCode keyCode) {
        KeyEvent.fireEvent(commandBox.commandTextField,
                new KeyEvent(KeyEvent.KEY_PRESSED, null, null, keyCode, false, false, false, false));
        KeyEvent.fireEvent(commandBox.commandTextField,
                new KeyEvent(KeyEvent.KEY_RELEASED, null, null, keyCode, false, false, false, false));
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        commandExecutorStub = commandText -> null;
        commandBox = new CommandBox(commandExecutorStub);
        stage.setScene(new Scene(commandBox.getRoot()));
        robot = new FxRobot();
        resetSut = () -> {
            commandBox = new CommandBox(commandExecutorStub);
            stage.setScene(new Scene(commandBox.getRoot()));
            stage.getScene().getStylesheets().clear();
        };
        stage.show();
    }

    @AfterEach
    void tearDown() {
        Platform.runLater(() -> {
            resetSut.run();
        });
    }

    @Test
    void pressEnter_commandTextDoesNotCauseException_textIsStoredInHistory() {
        Platform.runLater(() -> {
            robot.write("some input");
            robot.interact(() -> {
                pushKey(KeyCode.ENTER);
                pushKey(KeyCode.UP);
                assertEquals("some input", commandBox.commandTextField.getText());
            });
        });
    }

    @Test
    void importSyntaxStyleSheet() {
        commandBox.importSyntaxStyleSheet(commandBox.getRoot().getScene());
        assertEquals(
                CommandBoxTest.class.getResource(SYNTAX_HIGHLIGHTING_CSS_FILE_PATH).toExternalForm(),
                commandBox.getRoot().getScene().getStylesheets().get(0));
    }

    @Test
    void enableSuggestionAndSyntaxHighlightingFor_inputMatches_highlightsAfterDelay() {
        String space = " ";
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.singleton(COMMAND_WORD_STYLE), COMMAND.length());
        expected.add(Collections.emptyList(), space.length());
        commandBox.getRoot().getScene().getStylesheets().add(
                CommandBoxTest.class.getResource(SYNTAX_HIGHLIGHTING_CSS_FILE_PATH).toExternalForm());
        commandBox.enableSyntaxHighlighting();
        commandBox.enableSuggestionAndSyntaxHighlightingFor(COMMAND, Collections.emptyList(), Collections.emptyList());

        robot.write(COMMAND + space);
        robot.sleep(400);

        robot.interact(() -> {
            assertEquals(expected.create(), commandBox.commandTextField.getStyleSpan());
        });
    }

    @Test
    void disableSuggestionsAndSyntaxHighlightingFor_inputWouldMatchIfEnabled_noHighlighting() {
        String space = " ";
        StyleSpansBuilder<Collection<String>> expected = new StyleSpansBuilder<>();
        expected.add(Collections.emptyList(), COMMAND.length() + space.length());
        commandBox.getRoot().getScene().getStylesheets().add(
                CommandBoxTest.class.getResource(SYNTAX_HIGHLIGHTING_CSS_FILE_PATH).toExternalForm());
        commandBox.enableSyntaxHighlighting();
        commandBox.enableSuggestionAndSyntaxHighlightingFor(COMMAND, Collections.emptyList(), Collections.emptyList());
        commandBox.disableSuggestionsAndSyntaxHighlightingFor(COMMAND);

        robot.write(COMMAND + space);
        robot.sleep(400);
        Platform.runLater(() -> assertEquals(expected.create(), commandBox.commandTextField.getStyleSpan()));
    }
}
