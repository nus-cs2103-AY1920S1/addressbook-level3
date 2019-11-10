package seedu.revision.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.logic.commands.main.ListCommand;

@ExtendWith(ApplicationExtension.class)
class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_SUCCESS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_INVALID = "invalid command";
    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;
    private CommandBox commandBox;

    private CommandBoxHandle commandBoxHandle;

    @BeforeAll
    public static void runHeadless() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
    }

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

        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_CLASS));

        uiPartExtension.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBox.getAutoCompleteField().getStyleClass());
        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Checks the initial state of the command box.
     */
    @Test
    public void commandBox_noInput_shouldBeEmpty() {
        FxAssert.verifyThat(commandBox.getAutoCompleteField(), TextInputControlMatchers.hasText(""));
    }

    /**
     * Tests a valid input. Should be empty after "ENTER" is keyed in.
     */
    @Test
    public void commandBox_typeEnterAfterValidInput_shouldBeEmpty() {
        assertBehaviorForSuccessfulCommand();
    }

    /**
     * Tests an invalid input. Should be empty after "ENTER" is keyed in.
     */
    @Test
    public void commandBox_typeEnterAfterInValidInput_shouldNotAccept() {
        assertBehaviorForFailedCommand();
    }

    /**
     * Tests an invalid input after a valid input. Should not be empty and should show error style class.
     */
    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    /**
     * Tests an that style class returns to default after entering an invalid input and typing again afterwards.
     */
    @Test
    public void commandBox_handleKeyPress() {
        assertBehaviorForFailedCommand();
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        guiRobot.clickOn(".commandTextField");
        guiRobot.write(COMMAND_INVALID);
        guiRobot.type(KeyCode.ENTER);
        assertEquals(COMMAND_INVALID, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        guiRobot.clickOn(".commandTextField");
        guiRobot.write(COMMAND_SUCCESS);
        guiRobot.type(KeyCode.DOWN, KeyCode.ENTER, KeyCode.ENTER);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }
}
