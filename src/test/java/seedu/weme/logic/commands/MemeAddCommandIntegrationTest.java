package seedu.weme.logic.commands;

import static seedu.weme.logic.commands.CommandTestUtil.assertAddCommandSuccess;
import static seedu.weme.testutil.TypicalMemeBook.getTypicalMemeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.meme.Meme;
import seedu.weme.statistics.StatsManager;
import seedu.weme.testutil.MemeBuilder;
import seedu.weme.testutil.TestUtil;
import seedu.weme.testutil.UserPrefsBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code MemeAddCommand}.
 */
public class MemeAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMemeBook(), new UserPrefsBuilder().build(), new StatsManager());
        TestUtil.clearSandBoxFolder();
    }

    @Test
    public void execute_newMeme_success() throws Exception {
        Meme validMeme = new MemeBuilder().build();

        assertAddCommandSuccess(new MemeAddCommand(validMeme), model,
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
