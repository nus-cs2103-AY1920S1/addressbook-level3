//@@author CarbonGrid
package seedu.address;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Init;

import javafx.scene.input.KeyCode;

import seedu.address.ui.autocomplete.AutoCompleter;


@ExtendWith(ApplicationExtension.class)
public class MainAppTest extends ApplicationTest {

    /**
     * Initialises main class
     */
    @Init
    public void init() throws Exception {
        ApplicationTest.launch(MainApp.class);
    }

    @Test
    public void traverseTabBarTest(FxRobot robot) {
        var patientsTabStyleClass = robot.lookup("#patientsTab").query().getStyleClass();
        var appointmentsTabStyleClass = robot.lookup("#appointmentsTab").query().getStyleClass();
        var doctorsTabStyleClass = robot.lookup("#doctorsTab").query().getStyleClass();
        var dutyShiftTabStyleClass = robot.lookup("#dutyShiftTab").query().getStyleClass();
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var tabBar = robot.lookup("#tabBar").query();

        robot.clickOn("#doctorsTab");
        Assertions.assertThat(patientsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("selected-tab");
        Assertions.assertThat(dutyShiftTabStyleClass).containsOnly("unselected-tab");

        var omniPanelListView = lookup("#omniPanelListView").queryListView();
        var omniPanelSelectionModel = omniPanelListView.getSelectionModel();

        robot.type(KeyCode.RIGHT);
        Assertions.assertThat(omniPanelSelectionModel.getSelectedIndex()).isEqualTo(0);
        robot.type(KeyCode.UP);
        Assertions.assertThat(omniPanelSelectionModel.getSelectedIndex())
                .isEqualTo(omniPanelListView.getItems().size() - 1);
        robot.type(KeyCode.DOWN);
        Assertions.assertThat(omniPanelSelectionModel.getSelectedIndex()).isEqualTo(0);

        robot.type(KeyCode.LEFT, KeyCode.UP);
        Assertions.assertThat(patientsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("selected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(dutyShiftTabStyleClass).containsOnly("unselected-tab");

        robot.type(KeyCode.UP);
        Assertions.assertThat(patientsTabStyleClass).containsOnly("selected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(dutyShiftTabStyleClass).containsOnly("unselected-tab");

        robot.type(KeyCode.UP);
        Assertions.assertThat(patientsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(dutyShiftTabStyleClass).containsOnly("selected-tab");

        robot.type(KeyCode.DOWN);
        Assertions.assertThat(patientsTabStyleClass).containsOnly("selected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(dutyShiftTabStyleClass).containsOnly("unselected-tab");

        robot.type(KeyCode.TAB);
        Assertions.assertThat(tabBar).isNotFocused();
        Assertions.assertThat(commandBox).isFocused();

        robot.type(KeyCode.TAB);
        Assertions.assertThat(tabBar).isFocused();
        Assertions.assertThat(commandBox).isNotFocused();
    }

    @Test
    public void autoCompleterTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var aco = robot.lookup("#autoCompleteOverlay").queryListView();

        int expectedSearchResultSize = new AutoCompleter().update("n").getSuggestions().size();

        robot.clickOn("#commandTextField").write('n');
        Assertions.assertThat(aco).isVisible();
        Assertions.assertThat(aco.getSelectionModel().getSelectedIndex()).isEqualTo(0);

        robot.type(KeyCode.UP);
        Assertions.assertThat(aco.getSelectionModel().getSelectedIndex()).isEqualTo(expectedSearchResultSize - 1);

        robot.type(KeyCode.DOWN);
        Assertions.assertThat(aco.getSelectionModel().getSelectedIndex()).isEqualTo(0);

        robot.type(KeyCode.ENTER);
        int currLen = commandBox.getText().length();
        Assertions.assertThat(currLen).isNotEqualTo(1);

        robot.eraseText(currLen);
        Assertions.assertThat(aco.isVisible()).isFalse();
    }

    @Test
    public void traverseAndUnknownCommandTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        robot.clickOn(commandBox).write("lol").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay).hasText("Unknown command");

        robot.type(KeyCode.UP);
        Assertions.assertThat(commandBox.getCaretPosition()).isEqualTo(3);

        robot.type(KeyCode.LEFT, KeyCode.BACK_SPACE);
        Assertions.assertThat(commandBox.getCaretPosition()).isEqualTo(1);
        robot.type(KeyCode.RIGHT).eraseText(2);
    }

    @Test
    public void queueAndRoomTest(FxRobot robot) {
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();
        var queueListView = robot.lookup("#queueListView").queryListView();
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();

        robot.clickOn(commandBox).write("onduty 1").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).endsWith("is now on duty.");

        robot.write("newpatient -id E0000001A -name John Doe -phone 98765432"
                        + " -email johnd@example.com -address 311, Clementi Ave 2, #02-25")
                .type(KeyCode.ENTER)
                .write("enqueue E0000001A")
                .type(KeyCode.ENTER);

        Assertions.assertThat(resultDisplay.getText()).startsWith("New patient added to the queue:");
        Assertions.assertThat(queueListView).hasExactlyNumItems(1);

        robot.write("break 1").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).endsWith("is on break");

        robot.write("next 1").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).isEqualTo("Doctor is currently on break");
        Assertions.assertThat(queueListView).hasExactlyNumItems(1);

        robot.write("resume 1").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).endsWith("resumes his/her duty");
        Assertions.assertThat(queueListView).hasExactlyNumItems(1);

        robot.write("next 1").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Next patient has been referred to");
        Assertions.assertThat(queueListView).hasExactlyNumItems(0);

        robot.write("offduty 1").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).endsWith("is off-duty");

        robot.write("enqueue E0000001A").type(KeyCode.ENTER);
        Assertions.assertThat(queueListView).hasExactlyNumItems(1);

        robot.write("dequeue 1").type(KeyCode.ENTER);
        Assertions.assertThat(queueListView).hasExactlyNumItems(0);
    }

    @Test
    public void invalidCommandFormatTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        robot.clickOn(commandBox).write("newappt").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Invalid command format!");

        robot.eraseText(7);
    }

    @Test
    public void editdoctorTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        robot.clickOn(commandBox).write("newdoctor -id E0000001W -name bee").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("New staff added");

        robot.clickOn(commandBox).write("doctor E0000001W").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("1 person(s) listed!");

        robot.clickOn(commandBox).write("editdoctor -entry 1 -name Bae").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Edited Staff details: E0000001W Name: Bae");

        robot.write("undo").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Undo successful!");

        robot.write("undo").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Undo successful!");
    }

    @Test
    public void newdoctorUndoRedoTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        robot.clickOn(commandBox).write("newdoctor -id E0000001W -name bee").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("New staff added");

        robot.write("undo").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Undo successful!");

        robot.write("redo").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Redo successful!");

        robot.write("undo").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Undo successful!");
    }

    @Test
    public void exitCommandTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();

        robot.clickOn(commandBox).write("exit").type(KeyCode.ENTER);
        Assertions.assertThat(robot.listWindows()).hasSize(0);
    }

    @Test
    public void commandBoxHistoryTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        String[] testStrings = {"blabla", "   jUmPs. "};

        robot.clickOn(commandBox).write(testStrings[0]).type(KeyCode.ENTER).type(KeyCode.UP);
        Assertions.assertThat(commandBox.getText()).isEqualTo(testStrings[0]);
        robot.eraseText(testStrings[0].length()).write(testStrings[1]).type(KeyCode.ENTER).type(KeyCode.UP);
        Assertions.assertThat(commandBox.getText()).isEqualTo(testStrings[1]);
        robot.type(KeyCode.UP, 2);
        Assertions.assertThat(commandBox.getText()).isEqualTo(testStrings[0]);
        robot.type(KeyCode.DOWN);
        Assertions.assertThat(commandBox.getText()).isEqualTo(testStrings[1]);
        robot.type(KeyCode.DOWN, 3);
        Assertions.assertThat(commandBox.getText()).isBlank();
    }
}
