package tagline.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tagline.logic.Logic;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.testutil.CommandResultBuilder;
import tagline.testutil.LogicStub;

@ExtendWith(ApplicationExtension.class)
public class ResultPaneTest {

    private static final String RESPONSE_STRING = "feedback123";

    private static final CommandResult CONTACT_COMMAND_RESULT = new CommandResultBuilder()
            .putName(RESPONSE_STRING)
            .putViewType(ViewType.CONTACT)
            .build();
    private static final CommandResult NOTE_COMMAND_RESULT = new CommandResultBuilder()
            .putName(RESPONSE_STRING)
            .putViewType(ViewType.NOTE)
            .build();
    private static final CommandResult NONE_COMMAND_RESULT = new CommandResultBuilder()
            .putName(RESPONSE_STRING)
            .build();

    @TempDir
    public Path testFolder;

    private MainWindow mainWindow;
    private LogicStub logic;

    /**
     * Set up the main window.
     */
    private void initMainWindow(Stage stage, Logic logic) throws TimeoutException {
        if (stage.getStyle() != StageStyle.DECORATED) {
            stage.initStyle(StageStyle.DECORATED);
        }

        FxToolkit.setupStage(s -> {
            mainWindow = new MainWindow(s, logic);
            mainWindow.show();
            mainWindow.fillInnerParts();
        });
        FxToolkit.showStage();
    }

    @Start
    void setUp(Stage stage) throws TimeoutException {
        logic = new LogicStub(testFolder.resolve("addressbook.json"), testFolder.resolve("notebook.json"));
        initMainWindow(stage, logic);
    }

    @Stop
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @AfterEach
    void pause(FxRobot robot) {
        String headlessPropertyValue = System.getProperty("testfx.headless");
        if (headlessPropertyValue != null && headlessPropertyValue.equals("true")) {
            return;
        }

        robot.sleep(500);
    }

    private void assertSingleResultView(FxRobot robot) {
        StackPane resultPanePlaceholder = robot.lookup("#resultPanePlaceholder").queryAs(StackPane.class);
        assertEquals(1, resultPanePlaceholder.getChildren().size());
    }

    private void assertResultViewId(FxRobot robot, String id) {
        assertEquals(1, robot.lookup(id).queryAll().size());
    }

    /**
     * Sends a command which returns a specified {@code CommandResult}.
     */
    private void sendCommandWithResult(FxRobot robot, CommandResult commandResult) {
        logic.setCommandResult(commandResult);
        robot.clickOn(".commandTextField");

        TextField textField = robot.lookup(".commandTextField").queryAs(TextField.class);
        robot.interact(() -> textField.fireEvent(new ActionEvent()));
    }

    @Test
    void switchViewToNoteFromContact(FxRobot robot) {
        sendCommandWithResult(robot, NOTE_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#noteResultView");
    }

    @Test
    void switchViewToNoteFromNote(FxRobot robot) {
        sendCommandWithResult(robot, NOTE_COMMAND_RESULT);
        sendCommandWithResult(robot, NOTE_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#noteResultView");
    }

    @Test
    void switchViewToContactFromNote(FxRobot robot) {
        sendCommandWithResult(robot, NOTE_COMMAND_RESULT);
        sendCommandWithResult(robot, CONTACT_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#contactResultView");
    }

    @Test
    void switchViewToContactFromContact(FxRobot robot) {
        sendCommandWithResult(robot, CONTACT_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#contactResultView");
    }

    @Test
    void switchViewToNoneFromContact(FxRobot robot) {
        sendCommandWithResult(robot, NONE_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#contactResultView");
    }

    @Test
    void switchViewToNoneFromNote(FxRobot robot) {
        sendCommandWithResult(robot, NOTE_COMMAND_RESULT);
        sendCommandWithResult(robot, NONE_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#noteResultView");
    }
}
