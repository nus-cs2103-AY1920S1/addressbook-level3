package seedu.address;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class MainAppTest extends ApplicationTest {

    @BeforeAll
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp.class);
    }

    @AfterEach
    public void afterEach() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Override
    public void start(Stage stage) {
        stage.show();
    }

    @Test
    public void traverseTabBarTest(FxRobot robot) {
        robot.clickOn("#doctorsTab");
        Assertions.assertThat(lookup("#patientsTab").query().getStyleClass()).containsOnly("unselected-tab");
        Assertions.assertThat(lookup("#doctorsTab").query().getStyleClass()).containsOnly("selected-tab");

        robot.type(KeyCode.DOWN);
        Assertions.assertThat(lookup("#patientsTab").query().getStyleClass()).containsOnly("selected-tab");
        Assertions.assertThat(lookup("#doctorsTab").query().getStyleClass()).containsOnly("unselected-tab");

        robot.type(KeyCode.UP);
        Assertions.assertThat(lookup("#patientsTab").query().getStyleClass()).containsOnly("unselected-tab");
        Assertions.assertThat(lookup("#doctorsTab").query().getStyleClass()).containsOnly("selected-tab");
    }

    @Test
    public void autoCompleterTest(FxRobot robot) {
        robot.clickOn("#commandTextField");
        robot.write('a').type(KeyCode.UP);
        Assertions.assertThat(lookup("#autoCompleteOverlay").queryListView().isVisible()).isTrue();
        Assertions.assertThat(lookup("#autoCompleteOverlay").queryListView().getSelectionModel().getSelectedIndex())
            .isEqualTo(2);
        robot.type(KeyCode.DOWN);
        Assertions.assertThat(lookup("#autoCompleteOverlay").queryListView().getSelectionModel().getSelectedIndex())
            .isEqualTo(0);
        robot.eraseText(1);
    }

    @Test
    public void traverseAndUnknownCommandTest(FxRobot robot) {
        robot.clickOn("#commandTextField").write("lol").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#resultDisplay").queryTextInputControl()).hasText("Unknown command");
        robot.type(KeyCode.UP);
        Assertions.assertThat(lookup("#commandTextField").queryTextInputControl().getCaretPosition()).isEqualTo(3);
        robot.type(KeyCode.LEFT, KeyCode.BACK_SPACE);
        Assertions.assertThat(lookup("#commandTextField").queryTextInputControl().getCaretPosition()).isEqualTo(1);
        robot.type(KeyCode.RIGHT).eraseText(2);
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
        robot.clickOn("#commandTextField").write("addappt").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#resultDisplay").queryTextInputControl().getText())
            .startsWith("Invalid command format!");
        robot.eraseText(7).write("ackappt").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#resultDisplay").queryTextInputControl().getText())
            .startsWith("Invalid command format!");
        robot.eraseText(7).write("appointments").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#resultDisplay").queryTextInputControl().getText())
            .startsWith("Invalid command format!");
        robot.eraseText(12);
    }

    @Test
    public void helpCommandTest(FxRobot robot) {
        robot.clickOn("#commandTextField").write("help").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#resultDisplay").queryTextInputControl().getText())
            .startsWith("Opened help window.");
        Assertions.assertThat(window("Help")).isShowing();
        Assertions.assertThat(listWindows().size()).isEqualTo(2);
    }
}
