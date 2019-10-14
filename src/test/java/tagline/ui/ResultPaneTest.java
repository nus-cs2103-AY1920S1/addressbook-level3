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
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.testutil.CommandResultBuilder;
import tagline.testutil.LogicStub;

@ExtendWith(ApplicationExtension.class)
public class ResultPaneTest {

    private static final String COMMAND_TEST_STRING = "command12345";
    private static final String RESPONSE_STRING = "feedback123";

    private static final CommandResult CONTACT_COMMAND_RESULT = new CommandResultBuilder()
            .putName(RESPONSE_STRING)
            .putViewType(ViewType.CONTACT)
            .build();
    private static final CommandResult DUMMY_COMMAND_RESULT = new CommandResultBuilder()
            .putName(RESPONSE_STRING)
            .putViewType(ViewType.DUMMY)
            .build();
    private static final CommandResult NONE_COMMAND_RESULT = new CommandResultBuilder()
            .putName(RESPONSE_STRING)
            .build();

    @TempDir
    public Path testFolder;

    private MainWindow mainWindow;
    private LogicStub logic;

    @Start
    void setup(Stage stage) throws TimeoutException {
        logic = new LogicStub(testFolder);
        FxToolkit.setupStage(s -> {
            mainWindow = new MainWindow(s, logic);
            mainWindow.show();
            mainWindow.fillInnerParts();
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxToolkit.showStage();
    }

    @Stop
    void teardown() throws TimeoutException {
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

    void assertSingleResultView(FxRobot robot) {
        StackPane resultPanePlaceholder = robot.lookup("#resultPanePlaceholder").queryAs(StackPane.class);
        assertEquals(1, resultPanePlaceholder.getChildren().size());
    }

    void assertResultViewId(FxRobot robot, String id) {
        assertEquals(1, robot.lookup(id).queryAll().size());
    }

    /**
     * Sends a command which returns a specified {@code CommandResult}.
     */
    void sendCommandWithResult(FxRobot robot, CommandResult commandResult) throws TimeoutException {
        logic.setCommandResult(commandResult);
        robot.clickOn(".commandSendButton");
    }

    @Test
    void switchViewToDummyFromContact(FxRobot robot) throws TimeoutException {
        sendCommandWithResult(robot, DUMMY_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#dummyResultView");
    }

    @Test
    void switchViewToDummyFromDummy(FxRobot robot) throws TimeoutException {
        sendCommandWithResult(robot, DUMMY_COMMAND_RESULT);
        sendCommandWithResult(robot, DUMMY_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#dummyResultView");
    }

    @Test
    void switchViewToContactFromDummy(FxRobot robot) throws TimeoutException {
        sendCommandWithResult(robot, DUMMY_COMMAND_RESULT);
        sendCommandWithResult(robot, CONTACT_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#contactResultView");
    }

    @Test
    void switchViewToContactFromContact(FxRobot robot) throws TimeoutException {
        sendCommandWithResult(robot, CONTACT_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#contactResultView");
    }

    @Test
    void switchViewToNoneFromContact(FxRobot robot) throws TimeoutException {
        sendCommandWithResult(robot, NONE_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#contactResultView");
    }

    @Test
    void switchViewToNoneFromDummy(FxRobot robot) throws TimeoutException {
        sendCommandWithResult(robot, DUMMY_COMMAND_RESULT);
        sendCommandWithResult(robot, NONE_COMMAND_RESULT);

        assertSingleResultView(robot);
        assertResultViewId(robot, "#dummyResultView");
    }
}
