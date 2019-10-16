package seedu.address;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class MainAppTest extends ApplicationTest {

    @BeforeEach
    public void beforeEach() throws Exception {
        launch(MainApp.class);
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @AfterEach
    public void afterEach() throws TimeoutException {
        FxToolkit.hideStage();
        // release all keys
        release(new KeyCode[0]);
        // release all mouse buttons
        release(new MouseButton[0]);
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void traverseTabBarTest(FxRobot robot) {
        var patientsTabStyleClass = robot.lookup("#patientsTab").query().getStyleClass();
        var doctorsTabStyleClass = robot.lookup("#doctorsTab").query().getStyleClass();

        //robot.clickOn("#doctorsTab");
        //Assertions.assertThat(patientsTabStyleClass).containsOnly("unselected-tab");
        //Assertions.assertThat(doctorsTabStyleClass).containsOnly("selected-tab");

        robot.type(KeyCode.DOWN);
        Assertions.assertThat(patientsTabStyleClass).containsOnly("selected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("unselected-tab");

        //robot.type(KeyCode.UP);
        //Assertions.assertThat(patientsTabStyleClass).containsOnly("unselected-tab");
        //Assertions.assertThat(doctorsTabStyleClass).containsOnly("selected-tab");
    }

    @Test
    public void autoCompleterTest(FxRobot robot) {
        var aco = robot.lookup("#autoCompleteOverlay").queryListView();

        robot.clickOn("#commandTextField");
        robot.write('a').type(KeyCode.UP);
        Assertions.assertThat(aco).isVisible();
        Assertions.assertThat(aco.getSelectionModel().getSelectedIndex()).isEqualTo(3);

        robot.type(KeyCode.DOWN);
        Assertions.assertThat(aco.getSelectionModel().getSelectedIndex()).isEqualTo(0);
        robot.eraseText(1);
    }

    @Test
    public void traverseAndUnknownCommandTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        //robot.clickOn(commandBox).write("lol").type(KeyCode.ENTER);
        //Assertions.assertThat(resultDisplay).hasText("Unknown command");

        //robot.type(KeyCode.UP);
        //Assertions.assertThat(commandBox.getCaretPosition()).isEqualTo(3);

        //robot.type(KeyCode.LEFT, KeyCode.BACK_SPACE);
        //Assertions.assertThat(commandBox.getCaretPosition()).isEqualTo(1);
        //robot.type(KeyCode.RIGHT).eraseText(2);
    }

    /*
    @Test
    public void enqueueAndDequeueTest(FxRobot robot) {
        robot.clickOn("#commandTextField").write("enqueue 001A").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#queueListView").queryListView()).hasExactlyNumItems(1);
        robot.write("dequeue 1").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#queueListView").queryListView()).hasExactlyNumItems(0);
    }
    */

    @Test
    public void invalidCommandFormatTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        //robot.clickOn(commandBox).write("addappt").type(KeyCode.ENTER);
        //Assertions.assertThat(resultDisplay.getText()).startsWith("Invalid command format!");
        //
        //robot.eraseText(7).write("ackappt").type(KeyCode.ENTER);
        //Assertions.assertThat(resultDisplay.getText()).startsWith("Invalid command format!");
        //
        //robot.eraseText(7).write("appointments").type(KeyCode.ENTER);
        //Assertions.assertThat(resultDisplay.getText()).startsWith("Invalid command format!");
        //robot.eraseText(12);
    }

    @Test
    public void helpCommandTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        //robot.clickOn(commandBox).write("help").type(KeyCode.ENTER);
        //Assertions.assertThat(resultDisplay.getText()).startsWith("Opened help window.");
        //Assertions.assertThat(robot.window("Help")).isShowing();
        //Assertions.assertThat(robot.listWindows().size()).isEqualTo(2);
    }
}
