package seedu.weme.logic.commands;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.meme.Meme;
import seedu.weme.testutil.MemeBuilder;
import seedu.weme.testutil.MemeUtil;
import seedu.weme.testutil.TestUtil;
import seedu.weme.testutil.UserPrefsBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code MemeAddCommand}.
 */
public class MemeAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMemeBook(), new UserPrefsBuilder().build());
        TestUtil.clearSandBoxFolder();
    }

    @Test
    public void execute_newMeme_success() throws Exception {
        Meme validMeme = new MemeBuilder().build();

        Model expectedModel = new ModelManager(model.getMemeBook(), model.getUserPrefs());
        Meme addedMeme = MemeUtil.generateCopiedMeme(validMeme, expectedModel.getMemeImagePath());
        expectedModel.addMeme(addedMeme);
        expectedModel.commitMemeBook();

        assertCommandSuccess(new MemeAddCommand(validMeme), model,
                String.format(MemeAddCommand.MESSAGE_SUCCESS, addedMeme), expectedModel);
    }

    @Test
    public void execute_duplicateMeme_throwsCommandException() throws Exception {
        Meme validMeme = new MemeBuilder().build();
        Meme addedMeme = MemeUtil.generateCopiedMeme(validMeme, model.getMemeImagePath());
        model.addMeme(addedMeme);

        assertCommandFailure(new MemeAddCommand(validMeme), model, MemeAddCommand.MESSAGE_DUPLICATE_MEME);
    }

}
