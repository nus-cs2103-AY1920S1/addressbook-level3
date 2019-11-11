package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTutorAid.getTypicalTutorAid;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.CommandWord;



/**
 * Contains integration tests (interaction with the Model) for {@code NewCommand}.
 */
public class NewCommandTest {

    private static final String VALID_COMMAND_WORD = "plus";
    private static final String VALID_COMMAND_ACTION = "add";

    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTutorAid(), new UserPrefs());


    @Test
    public void execute_validNewCommand_success() {
        NewCommand newCommand = new NewCommand(VALID_COMMAND_WORD, VALID_COMMAND_ACTION);
        CommandObject commandToAdd = new CommandObject(new CommandWord(VALID_COMMAND_ACTION),
                new CommandAction(VALID_COMMAND_WORD));
        String expectedMessage = String.format(NewCommand.SHOWING_NEW_COMMAND_MESSAGE + VALID_COMMAND_WORD,
                VALID_COMMAND_ACTION);

        expectedModel.addCommand(commandToAdd);

        assertCommandSuccess(newCommand, model, expectedMessage, expectedModel);
    }


}
