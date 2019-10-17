package tagline.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tagline.logic.Logic;
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

    private LogicStub logic;
    private MainWindow mainWindow;

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
        logic.setCommandResult(DEFAULT_COMMAND_RESULT);
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

    /**
     * Types a command in the command text field.
     */
    void typeCommand(FxRobot robot, String command) {
        TextField textField = robot.lookup(".commandTextField").queryAs(TextField.class);
        robot.clickOn(textField);
        robot.interact(() -> textField.setText(command));
    }

    /**
     * Enters a command into the command text field and sends it with the Enter key.
     */
    void enterCommand(FxRobot robot, String command) {
        typeCommand(robot, command);
        robot.type(KeyCode.ENTER);
    }

    /**
     * Enters a command into the command text field and sends it by pressing the button.
     */
    void buttonCommand(FxRobot robot, String command) {
        typeCommand(robot, command);
        Button button = robot.lookup(".commandSendButton").queryButton();
        robot.interact(() -> button.fire());
    }

    @Test
    void sendEmptyCommand_successful(FxRobot robot) {
        TextField textField = robot.lookup(".commandTextField").queryAs(TextField.class);
        robot.clickOn(textField);
        robot.press(KeyCode.ENTER);

        //should have no command dialog
        assertEquals(0, robot.lookup(".command-dialog").queryAll().size());
        FxAssert.verifyThat(".response-dialog", hasText(RESPONSE_STRING));
    }

    @Test
    void sendNonEmptyCommand_successful(FxRobot robot) {
        enterCommand(robot, COMMAND_TEST_STRING);

        FxAssert.verifyThat(".command-dialog", hasText(COMMAND_TEST_STRING));
        FxAssert.verifyThat(".response-dialog", hasText(RESPONSE_STRING));
        FxAssert.verifyThat(".commandTextField", hasText("")); //cleared
    }

    @Test
    void sendCommandWithButton_successful(FxRobot robot) {
        buttonCommand(robot, COMMAND_TEST_STRING);

        FxAssert.verifyThat(".command-dialog", hasText(COMMAND_TEST_STRING));
        FxAssert.verifyThat(".response-dialog", hasText(RESPONSE_STRING));
        FxAssert.verifyThat(".commandTextField", hasText("")); //cleared
    }

    @Test
    void sendNonEmptyCommand_exceptionThrown(FxRobot robot) {
        logic.setThrowException(EXCEPTION_STRING);

        enterCommand(robot, COMMAND_TEST_STRING);

        FxAssert.verifyThat(".command-dialog", hasText(COMMAND_TEST_STRING));
        FxAssert.verifyThat(".response-dialog", hasText(EXCEPTION_STRING));
        FxAssert.verifyThat(".commandTextField", hasText(COMMAND_TEST_STRING));
    }

    @Test
    void sendLongCommand_successful(FxRobot robot) {
        enterCommand(robot, COMMAND_TEST_STRING_LONG);

        FxAssert.verifyThat(".command-dialog", hasText(COMMAND_TEST_STRING_LONG));
        FxAssert.verifyThat(".response-dialog", hasText(RESPONSE_STRING));
    }

    @Test
    void sendMultipleCommands_successful(FxRobot robot) {
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
