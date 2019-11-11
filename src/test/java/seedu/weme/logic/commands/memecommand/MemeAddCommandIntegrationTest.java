package seedu.weme.logic.commands.memecommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertAddCommandSuccess;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.commands.CommandTestUtil;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.meme.Meme;
import seedu.weme.testutil.MemeBuilder;
import seedu.weme.testutil.TestUtil;
import seedu.weme.testutil.UserPrefsBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code MemeAddCommand}.
 */
public class MemeAddCommandIntegrationTest extends ApplicationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalWeme(), new UserPrefsBuilder().build());
        TestUtil.clearSandBoxFolder();
    }

    @Test
    public void execute_newMeme_success() throws Exception {
        Meme validMeme = new MemeBuilder().build();

        CommandTestUtil.assertAddCommandSuccess(new MemeAddCommand(validMeme), model,
                String.format(MemeAddCommand.MESSAGE_SUCCESS, validMeme), validMeme);
    }

    @Test
    public void execute_duplicateMeme_success() throws Exception {
        Meme validMeme = new MemeBuilder().build();
        model.addMeme(validMeme);

        assertAddCommandSuccess(new MemeAddCommand(validMeme), model,
                String.format(MemeAddCommand.MESSAGE_SUCCESS, validMeme), validMeme);
    }

}
