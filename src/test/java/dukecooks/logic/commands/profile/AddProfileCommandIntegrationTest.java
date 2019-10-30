package dukecooks.logic.commands.profile;

import static dukecooks.testutil.profile.TypicalProfiles.getTypicalProfiles;

import org.junit.jupiter.api.BeforeEach;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the RecipeModel) for {@code AddProfileCommand}.
 */
public class AddProfileCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProfiles(), null, null, null, null,
                null, null, new UserPrefs());
    }

}
