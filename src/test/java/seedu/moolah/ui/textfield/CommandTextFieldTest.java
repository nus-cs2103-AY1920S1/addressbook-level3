package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.ui.textfield.CommandTextField.ARGUMENT_STYLE_PREFIX;
import static seedu.moolah.ui.textfield.CommandTextField.COMMAND_WORD_STYLE;
import static seedu.moolah.ui.textfield.CommandTextField.PREFIX_STYLE_PREFIX;
import static seedu.moolah.ui.textfield.CommandTextField.STRING_STYLE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.moolah.logic.parser.Prefix;

class CommandTextFieldTest extends ApplicationTest {

    private FxRobot robot;
    private CommandTextField textField;
    private Runnable resetTextfield;
    private static final String COMMAND = "command1";
    private static final Prefix PREFIX_1 = new Prefix("a/", "bb");
    private static final Prefix PREFIX_2 = new Prefix("ww/", "bb");
    private static final String ARGUMENT_1 = "sometext";
    private static final List<Prefix> REQUIRED = List.of(PREFIX_1, PREFIX_2);
    private static final List<Prefix> OPTIONAL = Collections.emptyList();
    private static final String SUPPORTED_INPUT = COMMAND + " " + PREFIX_1 + ARGUMENT_1 + " " + PREFIX_2;
    private static final String UNSUPPORTED_INPUT = "somgibbaweiahs";

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        this.textField = new CommandTextField(ignored -> {});
        stage.setScene(new Scene(new VBox(textField)));
        robot = new FxRobot();
        stage.show();
        resetTextfield = () -> {
            textField = new CommandTextField(ignored -> {});
            stage.setScene(new Scene(new VBox(textField)));
            stage.show();
            stage.getScene().getStylesheets().clear();
        };
    }

    static void pushKeyNoType(EventTarget target, KeyCode keyCode) {
        KeyEvent.fireEvent(
                target,
                new KeyEvent(KeyEvent.KEY_PRESSED, null, null, keyCode, false, false, false, false));
        KeyEvent.fireEvent(
                target,
                new KeyEvent(KeyEvent.KEY_RELEASED, null, null, keyCode, false, false, false, false));
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        Platform.runLater(resetTextfield);
    }

    @Test
    void clear() {
        assertEquals("", textField.getText());
        robot.write("something");
        Platform.runLater(() -> {
            assertEquals("something", textField.getText());
            textField.clear();
            assertEquals("", textField.getText());
        });
    }

    @Test
    void commitAndFlush() {
        robot.write("something");
        Platform.runLater(() -> {
            textField.commitAndFlush();
            assertEquals("", textField.getText());
            assertEquals("something", textField.inputHistory.getPreviousInput());
        });
    }

    @Test
    void replaceWithPreviousInput() {
        robot.write("something");
        Platform.runLater(() -> {
            assertEquals("something", textField.getText());
            textField.commitAndFlush();
            assertEquals("", textField.getText());
            textField.replaceWithPreviousInput();
            assertEquals("something", textField.getText());
        });
    }

    @Test
    void replaceWithNextInput() {
        robot.write("something1");
        Platform.runLater(() -> {
            textField.commitAndFlush();
        });
        robot.write("something2");

        Platform.runLater(() -> {
            textField.commitAndFlush();
            textField.replaceWithPreviousInput(); // sm2
            assertEquals("something2", textField.getText());
            textField.replaceWithPreviousInput(); // sm1
            assertEquals("something1", textField.getText());
            textField.replaceWithNextInput(); // sm1
            assertEquals("something1", textField.getText());
            textField.replaceWithNextInput(); // sm2
            assertEquals("something2", textField.getText());
        });
    }

    @Test
    void uiTest_arrowKeysReplaceInputWithHistory_behaviourAsExpected() {
        textField.inputHistory.push("something2");
        textField.inputHistory.push("something1");
        robot.interact(() -> {
            // goes to previous
            pushKeyNoType(textField.textField, KeyCode.UP);
            assertEquals("something1", textField.getText());

            // goes to previous
            pushKeyNoType(textField.textField, KeyCode.UP);
            assertEquals("something2", textField.getText());

            // tries to go to previous, nosuchelement exception ignored here
            pushKeyNoType(textField.textField, KeyCode.UP);
            assertEquals("something2", textField.getText());

            // tries to go to next
            pushKeyNoType(textField.textField, KeyCode.DOWN);
            assertEquals("something2", textField.getText());

            // tries to go to next
            pushKeyNoType(textField.textField, KeyCode.DOWN);
            assertEquals("something1", textField.getText());

            // tries to go to next, noSuchElement;
            pushKeyNoType(textField.textField, KeyCode.DOWN);
            assertEquals("something1", textField.getText());

        });
    }

    @Test
    void getText() {
        robot.write("something here");
        Platform.runLater(() -> assertEquals("something here", textField.getText()));
        robot.write(" more things here");
        Platform.runLater(() -> assertEquals("something here more things here", textField.getText()));
    }

    @Test
    void textProperty() {
        int[] count = new int[]{0};
        ChangeListener<String> listener = (obs, old, curr) -> {
            count[0] = 1 + count[0];
        };
        textField.textProperty().addListener(listener);
        robot.write("1234567890");
        assertEquals(10, count[0]);
    }

    @Test
    void importStyleSheet() {
        assertEquals(0, textField.getScene().getStylesheets().size());
        textField.importStyleSheet(textField.getScene());
        assertEquals(1, textField.getScene().getStylesheets().size());
    }

    @Test
    void computeHighlighting_commandIsSupported_setsExpectedSpan() {
        textField.importStyleSheet(textField.getScene());
        textField.addSupportFor(COMMAND, REQUIRED, OPTIONAL);
        textField.enableSyntaxHighlighting();
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(COMMAND_WORD_STYLE), COMMAND.length());
        spansBuilder.add(Collections.singleton(PREFIX_STYLE_PREFIX + "0"), PREFIX_1.getPrefix().length() + 1);
        spansBuilder.add(Collections.singleton(ARGUMENT_STYLE_PREFIX + "0"), ARGUMENT_1.length());
        spansBuilder.add(Collections.singleton(PREFIX_STYLE_PREFIX + "1"), PREFIX_2.getPrefix().length() + 1);
        new FxRobot().write(SUPPORTED_INPUT);
        new FxRobot().sleep(400);
        new FxRobot().interact(() -> {
            StyleSpans actual = textField.getStyleSpan();
            assertEquals(
                    spansBuilder.create(),
                    actual);
        });
    }

    @Test
    void computeHighlighting_commandIsNotSupported_setsExpectedSpan() {
        textField.importStyleSheet(textField.getScene());
        textField.addSupportFor(COMMAND, REQUIRED, OPTIONAL);
        textField.enableSyntaxHighlighting();
        new FxRobot().write(UNSUPPORTED_INPUT);
        new FxRobot().sleep(400);
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.emptyList(), UNSUPPORTED_INPUT.length());
        new FxRobot().interact(() -> assertEquals(
                spansBuilder.create(),
                textField.getStyleSpan()));
    }

    @Test
    void computeHighlighting_commandIsSupportedButNoValidPrefix_setsExpectedSpan() {
        textField.importStyleSheet(textField.getScene());
        textField.addSupportFor(COMMAND, REQUIRED, OPTIONAL);
        textField.enableSyntaxHighlighting();
        int whiteSpaceCount = 3;
        String input = COMMAND + " ".repeat(whiteSpaceCount) + UNSUPPORTED_INPUT;
        new FxRobot().write(input);
        new FxRobot().sleep(400);
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(COMMAND_WORD_STYLE), COMMAND.length());
        spansBuilder.add(Collections.emptyList(), whiteSpaceCount);
        spansBuilder.add(Collections.singleton(STRING_STYLE), UNSUPPORTED_INPUT.length());

        new FxRobot().interact(() -> assertEquals(
                spansBuilder.create(),
                textField.getStyleSpan()));
    }

    @Test
    void addSupportFor() {
        textField.addSupportFor("add", List.of(PREFIX_PRICE, PREFIX_DESCRIPTION), Collections.emptyList());
        textField.enableSyntaxHighlighting();
        robot.write("ad");
        Platform.runLater(() -> assertEquals(1, textField.autofillMenu.getItems().size()));
    }

    @Test
    void removeSupportFor() {
        textField.addSupportFor("add", Collections.emptyList(), Collections.emptyList());
        textField.removeSupportFor("add");
        textField.enableSyntaxHighlighting();
        robot.write("ad");
        Platform.runLater(() -> {
            assertEquals(0, textField.autofillMenu.getItems().size());
        });
    }
}