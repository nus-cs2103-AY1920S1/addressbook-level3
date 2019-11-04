package seedu.address.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

class CommandTextFieldTest extends ApplicationTest {

    private FxRobot robot;
    private CommandTextField textField;
    private Runnable resetTextfield;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        this.textField = new CommandTextField(ignored -> {});
        stage.setScene(new Scene(textField));
        robot = new FxRobot();
        stage.show();
        resetTextfield = () -> {
            textField = new CommandTextField(ignored -> {});
            stage.setScene(new Scene(textField));
            stage.show();
        };
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
            count[0] = count[0] + 1;
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

    @Test
    void paste() {

    }
}