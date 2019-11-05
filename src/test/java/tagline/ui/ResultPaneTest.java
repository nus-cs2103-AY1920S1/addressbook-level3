//@@author tanlk99
package tagline.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tagline.ui.GuiTestUtil.enterCommand;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.testutil.CommandResultBuilder;
import tagline.testutil.LogicStub;

@ExtendWith(ApplicationExtension.class)
public class ResultPaneTest {

    private static final String RESPONSE_STRING = "feedback123";

    private static final CommandResult CONTACT_COMMAND_RESULT = new CommandResultBuilder()
        .putName(RESPONSE_STRING)
        .putViewType(ViewType.CONTACT_LIST)
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

    private GuiTestController controller = GuiTestController.getInstance();
    private LogicStub logic;

    @Start
    private void setUp(Stage stage) throws TimeoutException {
        logic = new LogicStub(testFolder.resolve("addressbook.json"), testFolder.resolve("notebook.json"),
                testFolder.resolve("groupbook.json"), testFolder.resolve("tagbook.json"));

        controller.initStageStyle(stage);
        controller.initMainWindow(stage, logic);
    }

    @Stop
    private void tearDown() throws TimeoutException {
        controller.doTearDown();
    }

    @AfterEach
    private void pause(FxRobot robot) {
        controller.pause(robot);
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
        enterCommand(robot, "");
    }

    @Test
    public void switchViewToContact(FxRobot robot) {
        sendCommandWithResult(robot, CONTACT_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#contactListResultView");
    }

    @Test
    public void switchViewToContactFromContact(FxRobot robot) {
        sendCommandWithResult(robot, CONTACT_COMMAND_RESULT);
        sendCommandWithResult(robot, CONTACT_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#contactListResultView");
    }

    @Test
    public void switchViewToNoneFromContact(FxRobot robot) {
        sendCommandWithResult(robot, CONTACT_COMMAND_RESULT);
        sendCommandWithResult(robot, NONE_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#contactListResultView");
    }

    @Test
    public void switchViewToNoteFromContact(FxRobot robot) {
        sendCommandWithResult(robot, CONTACT_COMMAND_RESULT);
        sendCommandWithResult(robot, NOTE_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#noteListResultView");
    }
}
