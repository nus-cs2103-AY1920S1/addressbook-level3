//@@author tanlk99
package tagline.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.util.NodeQueryUtils.hasText;
import static tagline.ui.GuiTestUtil.buttonCommand;
import static tagline.ui.GuiTestUtil.enterCommand;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import tagline.logic.commands.CommandResult;
import tagline.testutil.CommandResultBuilder;
import tagline.testutil.LogicStub;

@ExtendWith(ApplicationExtension.class)
public class ChatPaneTest {

    private static final String COMMAND_TEST_STRING = "command12345";
    private static final String COMMAND_TEST_STRING_LONG = "c".repeat(1000);
    private static final String RESPONSE_STRING = "feedback123";
    private static final String EXCEPTION_STRING = "exception";

    private static final CommandResult DEFAULT_COMMAND_RESULT = new CommandResultBuilder()
        .putName(RESPONSE_STRING)
        .build();

    @TempDir
    public Path testFolder;

    private GuiTestController controller = GuiTestController.getInstance();
    private LogicStub logic;
    private MainWindow mainWindow;

    @Start
    private void setUp(Stage stage) throws TimeoutException {
        logic = new LogicStub(testFolder.resolve("addressbook.json"), testFolder.resolve("notebook.json"),
            testFolder.resolve("groupbook.json"), testFolder.resolve("tagbook.json"));
        logic.setCommandResult(DEFAULT_COMMAND_RESULT);

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

    @Test
    public void sendEmptyCommand_successful(FxRobot robot) {
        TextField textField = robot.lookup(".commandTextField").queryAs(TextField.class);
        robot.clickOn(textField);
        robot.type(KeyCode.ENTER);

        //should have no command dialog
        assertEquals(0, robot.lookup(".command-dialog").queryAll().size());
        FxAssert.verifyThat(".response-dialog", hasText(RESPONSE_STRING));
    }

    @Test
    public void sendNonEmptyCommand_successful(FxRobot robot) {
        enterCommand(robot, COMMAND_TEST_STRING);

        FxAssert.verifyThat(".command-dialog", hasText(COMMAND_TEST_STRING));
        FxAssert.verifyThat(".response-dialog", hasText(RESPONSE_STRING));
        FxAssert.verifyThat(".commandTextField", hasText("")); //cleared
    }

    @Test
    public void sendCommandWithButton_successful(FxRobot robot) {
        buttonCommand(robot, COMMAND_TEST_STRING);

        FxAssert.verifyThat(".command-dialog", hasText(COMMAND_TEST_STRING));
        FxAssert.verifyThat(".response-dialog", hasText(RESPONSE_STRING));
        FxAssert.verifyThat(".commandTextField", hasText("")); //cleared
    }

    @Test
    public void sendNonEmptyCommand_exceptionThrown(FxRobot robot) {
        logic.setThrowException(EXCEPTION_STRING);

        enterCommand(robot, COMMAND_TEST_STRING);

        FxAssert.verifyThat(".command-dialog", hasText(COMMAND_TEST_STRING));
        FxAssert.verifyThat(".response-dialog", hasText(EXCEPTION_STRING));
        FxAssert.verifyThat(".commandTextField", hasText(COMMAND_TEST_STRING));
    }

    @Test
    public void sendLongCommand_successful(FxRobot robot) {
        enterCommand(robot, COMMAND_TEST_STRING_LONG);

        FxAssert.verifyThat(".command-dialog", hasText(COMMAND_TEST_STRING_LONG));
        FxAssert.verifyThat(".response-dialog", hasText(RESPONSE_STRING));
    }

    @Test
    public void sendMultipleCommands_successful(FxRobot robot) {
        for (int i = 1; i <= 5; i++) {
            enterCommand(robot, COMMAND_TEST_STRING);
        }

        assertEquals(5, robot.lookup(".command-dialog").queryAll().size());
        assertEquals(5, robot.lookup(".response-dialog").queryAll().size());
        assertTrue(robot.lookup(".command-dialog").queryAll()
            .stream().allMatch(dialog -> hasText(COMMAND_TEST_STRING).test(dialog)));
        assertTrue(robot.lookup(".response-dialog").queryAll()
            .stream().allMatch(dialog -> hasText(RESPONSE_STRING).test(dialog)));
    }
}
