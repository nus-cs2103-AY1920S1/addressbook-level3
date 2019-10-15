package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalProfiles.getTypicalProfiles;

import org.junit.jupiter.api.BeforeEach;

import seedu.address.profile.Model;
import seedu.address.profile.ModelManager;
import seedu.address.profile.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code AddProfileCommand}.
 */
public class AddProfileCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProfiles(), null, new UserPrefs());
    }

}
