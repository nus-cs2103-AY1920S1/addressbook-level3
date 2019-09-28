package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMemes.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meme.Meme;
import seedu.address.testutil.MemeBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newMeme_success() {
        Meme validMeme = new MemeBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addMeme(validMeme);

        assertCommandSuccess(new AddCommand(validMeme), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validMeme), expectedModel);
    }

    @Test
    public void execute_duplicateMeme_throwsCommandException() {
        Meme memeInList = model.getAddressBook().getMemeList().get(0);
        assertCommandFailure(new AddCommand(memeInList), model, AddCommand.MESSAGE_DUPLICATE_MEME);
    }

}
