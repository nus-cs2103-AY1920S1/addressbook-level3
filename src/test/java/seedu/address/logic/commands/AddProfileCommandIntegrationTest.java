package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalProfiles.getTypicalProfiles;

import org.junit.jupiter.api.BeforeEach;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the RecipeModel) for {@code AddProfileCommand}.
 */
public class AddProfileCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProfiles(), null, null, null, new UserPrefs());
    }

}
