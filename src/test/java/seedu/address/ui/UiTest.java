package seedu.address.ui;

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
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import seedu.address.MainApp;

@ExtendWith(ApplicationExtension.class)
public class UiTest extends ApplicationTest {

    @BeforeAll
    public static void setUpClass() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp.class);
    }

    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
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
    public void commandBoxTest(FxRobot robot) {
        robot.clickOn("#commandTextField").write("lol").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#resultDisplay").queryTextInputControl()).hasText("Unknown command");
        robot.type(KeyCode.UP);
        Assertions.assertThat(lookup("#commandTextField").queryTextInputControl().getCaretPosition()).isEqualTo(3);
        robot.type(KeyCode.LEFT, KeyCode.BACK_SPACE);
        Assertions.assertThat(lookup("#commandTextField").queryTextInputControl().getCaretPosition()).isEqualTo(1);
        robot.type(KeyCode.RIGHT).eraseText(2);

        robot.write("enqueue 001A").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#queueListView").queryListView()).hasExactlyNumItems(1);
        robot.write("dequeue 1").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#queueListView").queryListView()).hasExactlyNumItems(0);
    }
}
