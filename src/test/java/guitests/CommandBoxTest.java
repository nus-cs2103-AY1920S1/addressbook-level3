package guitests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.ui.CommandBox;

@ExtendWith(ApplicationExtension.class)
class CommandBoxTest {

    private static final String COMMAND_SUCCESS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_INVALID = "invalid command";
    private CommandBox commandBox;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
        commandBox = new CommandBox(commandText -> {
            if (commandText.equals(COMMAND_SUCCESS)) {
                return new CommandResultBuilder().withFeedBack("Command successful").build();
            }
            throw new CommandException("Command failed");
        }, true);


        stage.setScene(new Scene(new StackPane(commandBox.getAutoCompleteField()))); // arbitrary height
        stage.show();
    }

    /**
     * Checks the initial state of the command box.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void commandBox_noInput_shouldBeEmpty(FxRobot robot) {
        FxAssert.verifyThat(commandBox.getAutoCompleteField(), TextInputControlMatchers.hasText(""));
    }

    /**
     * Tests a valid input. Should be empty after "ENTER" is keyed in.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void commandBox_typeEnterAfterValidInput_shouldBeEmpty(FxRobot robot) {
        robot.clickOn(".commandTextField");
        robot.write(COMMAND_SUCCESS);
        robot.type(KeyCode.DOWN, KeyCode.ENTER, KeyCode.ENTER);
        FxAssert.verifyThat(commandBox.getAutoCompleteField(), TextInputControlMatchers.hasText(""));
    }

    /**
     * Tests an invalid input. Should be empty after "ENTER" is keyed in.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void commandBox_typeEnterAfterInValidInput_shouldNotAccept(FxRobot robot) {
        robot.clickOn(".commandTextField");
        robot.write(COMMAND_INVALID);
        robot.type(KeyCode.ENTER);
        FxAssert.verifyThat(commandBox.getAutoCompleteField(), TextInputControlMatchers.hasText(COMMAND_INVALID));
    }
}
