package seedu.revision.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.revision.logic.commands.main.CommandResultBuilder;

@ExtendWith(ApplicationExtension.class)
class CommandBoxTest {

    private CommandBox commandBox;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        commandBox = new CommandBox((unused) -> new CommandResultBuilder().build(), false);
        stage.setScene(new Scene(new StackPane(commandBox.getCommandTextField()))); // arbitrary height
        stage.show();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void emptyInput_emptyResult(FxRobot robot) {
        FxAssert.verifyThat(commandBox.getCommandTextField(), TextInputControlMatchers.hasText(""));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void handleCommandEntered_validInput_shouldShowText(FxRobot robot) {
        robot.write("list");
        FxAssert.verifyThat(commandBox.getCommandTextField(), TextInputControlMatchers.hasText("list"));
    }
}
