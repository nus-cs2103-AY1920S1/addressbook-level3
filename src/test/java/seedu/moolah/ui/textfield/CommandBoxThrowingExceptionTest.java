package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.ui.textfield.CommandTextField.ERROR_STYLE_CLASS;
import static seedu.moolah.ui.textfield.CommandTextField.SYNTAX_HIGHLIGHTING_CSS_FILE_PATH;

import java.util.Collection;
import java.util.Collections;

import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.ui.panel.PanelName;
import seedu.moolah.ui.panel.exceptions.UnmappedPanelException;

/**
 * Test for CommandBox to ensure that CommandBox with correctly sets StyleSpans to style which indicates error
 * when an exception is thrown.
 */
class CommandBoxThrowingExceptionTest extends ApplicationTest {

    private CommandBox commandBoxThrowingCommandExceptionOnEnter;
    private CommandBox commandBoxThrowingParseExceptionOnEnter;
    private CommandBox commandBoxThrowingUnmappedPanelExceptionOnEnter;
    private FxRobot robot;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        commandBoxThrowingCommandExceptionOnEnter = new CommandBox(ignored -> {
            throw new CommandException("");
        });
        commandBoxThrowingParseExceptionOnEnter = new CommandBox(ignored -> {
            throw new ParseException("");
        });
        commandBoxThrowingUnmappedPanelExceptionOnEnter = new CommandBox(ignored -> {
            throw new UnmappedPanelException(new PanelName("ignored"));
        });
        stage.setScene(new Scene(new VBox(
                commandBoxThrowingCommandExceptionOnEnter.getRoot(),
                commandBoxThrowingParseExceptionOnEnter.getRoot(),
                commandBoxThrowingUnmappedPanelExceptionOnEnter.getRoot())));
        stage.getScene().getStylesheets().add(
                CommandBoxThrowingExceptionTest.class
                        .getResource(SYNTAX_HIGHLIGHTING_CSS_FILE_PATH).toExternalForm());

        stage.show();

        robot = new FxRobot();
    }


    @Test
    void pressEnter_inputCausesCommandException_styleSetToErrorStyle() {
        String input = "commandException";
        StyleSpansBuilder<Collection<String>> expectedStyleSpan = new StyleSpansBuilder<>();
        expectedStyleSpan.add(Collections.singleton(ERROR_STYLE_CLASS), input.length());

        robot.write(input);
        robot.interact(() -> {
            KeyEvent.fireEvent(commandBoxThrowingCommandExceptionOnEnter.commandTextField,
                    new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.ENTER,
                            false, false, false, false));
            KeyEvent.fireEvent(commandBoxThrowingCommandExceptionOnEnter.commandTextField,
                    new KeyEvent(KeyEvent.KEY_RELEASED, null, null, KeyCode.ENTER,
                            false, false, false, false));
            assertEquals(
                    expectedStyleSpan.create(),
                    commandBoxThrowingCommandExceptionOnEnter.commandTextField.getStyleSpan());
        });
    }

    @Test
    void pressEnter_inputCausesParseException_styleSetToErrorStyle() {
        String input = "input";
        StyleSpansBuilder<Collection<String>> expectedStyleSpan = new StyleSpansBuilder<>();
        expectedStyleSpan.add(Collections.singleton(ERROR_STYLE_CLASS), input.length());

        robot.interact(() -> {
            commandBoxThrowingParseExceptionOnEnter.commandTextField.replaceText(input);
            KeyEvent.fireEvent(commandBoxThrowingParseExceptionOnEnter.commandTextField,
                    new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.ENTER,
                            false, false, false, false));
            KeyEvent.fireEvent(commandBoxThrowingParseExceptionOnEnter.commandTextField,
                    new KeyEvent(KeyEvent.KEY_RELEASED, null, null, KeyCode.ENTER,
                            false, false, false, false));
            assertEquals(
                    expectedStyleSpan.create(),
                    commandBoxThrowingParseExceptionOnEnter.commandTextField.getStyleSpan());
        });
    }

    @Test
    void pressEnter_inputCausesUnmappedPanelException_styleSetToErrorStyle() {
        String input = "input";
        StyleSpansBuilder<Collection<String>> expectedStyleSpans = new StyleSpansBuilder<>();
        expectedStyleSpans.add(Collections.singleton(ERROR_STYLE_CLASS), input.length());

        robot.interact(() -> {
            commandBoxThrowingUnmappedPanelExceptionOnEnter.commandTextField.replaceText(input);
            KeyEvent.fireEvent(commandBoxThrowingUnmappedPanelExceptionOnEnter.commandTextField,
                    new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.ENTER,
                            false, false, false, false));
            KeyEvent.fireEvent(commandBoxThrowingUnmappedPanelExceptionOnEnter.commandTextField,
                    new KeyEvent(KeyEvent.KEY_RELEASED, null, null, KeyCode.ENTER,
                            false, false, false, false));
            assertEquals(
                    expectedStyleSpans.create(),
                    commandBoxThrowingUnmappedPanelExceptionOnEnter.commandTextField.getStyleSpan());
        });
    }
}
