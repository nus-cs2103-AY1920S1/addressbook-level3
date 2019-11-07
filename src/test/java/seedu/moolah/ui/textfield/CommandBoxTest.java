package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.ui.textfield.CommandTextField.ERROR_STYLE_CLASS;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.ui.panel.PanelName;
import seedu.moolah.ui.panel.exceptions.UnmappedPanelException;

class CommandBoxThrowingExceptionTest extends ApplicationTest {

    Consumer<Stage> setCommandBoxToThrowCommandException;
    Consumer<Stage> setCommandBoxToThrowParseException;
    Consumer<Stage> setCommandBoxToThrowUnmappedPanelException;

    CommandBox commandBox;
    FxRobot robot;
    Stage stage;

    public void start(Stage stage) throws Exception {
        super.start(stage);
        this.stage = stage;
        setCommandBoxToThrowCommandException = stage1 -> {
            commandBox = new CommandBox(ignored -> {
                throw new CommandException("");
            });
            stage1.setScene(new Scene(commandBox.getRoot()));
            commandBox.importSyntaxStyleSheet(stage1.getScene());
        };

        setCommandBoxToThrowParseException = stage1 -> {
            commandBox = new CommandBox(ignored -> {
                throw new ParseException("");
            });
            stage1.setScene(new Scene(commandBox.getRoot()));
            commandBox.importSyntaxStyleSheet(stage1.getScene());
        };

        setCommandBoxToThrowUnmappedPanelException = stage1 -> {
            commandBox = new CommandBox(ignored -> {
                throw new UnmappedPanelException(new PanelName(""));
            });
            stage1.setScene(new Scene(commandBox.getRoot()));
            commandBox.importSyntaxStyleSheet(stage1.getScene());

        };

        robot = new FxRobot();
    }


    @Test
    void tmep() {
        StyleSpansBuilder<Collection<String>> styleSpansBuilder = new StyleSpansBuilder<>();
        styleSpansBuilder.add(Collections.singleton(ERROR_STYLE_CLASS), 3);

        Platform.runLater(() -> {
            setCommandBoxToThrowParseException.accept(stage);
            stage.show();
            robot.write("asd");
            robot.interact(() -> {
                KeyEvent.fireEvent(commandBox.getRoot(),
                        new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.ENTER, false, false, false, false));
                KeyEvent.fireEvent(commandBox.getRoot(),
                        new KeyEvent(KeyEvent.KEY_RELEASED, null, null, KeyCode.ENTER, false, false, false, false));
                assertEquals(
                        styleSpansBuilder.create(),
                        commandBox.commandTextField.getStyleSpan());
            });
        });


    }
}