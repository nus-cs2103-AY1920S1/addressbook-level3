package dukecooks.logic.commands.profile;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.profile.TypicalProfiles.getTypicalProfiles;

import org.junit.jupiter.api.Test;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.profile.UserProfile;

public class DeleteProfileCommandTest {

    @Test
    public void execute_emptyProfile_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new DeleteProfileCommand(), model,
                DeleteProfileCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyProfile_success() {
        Model model = new ModelManager(getTypicalProfiles(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalProfiles(), new UserPrefs());
        expectedModel.setUserProfile(new UserProfile());

        assertCommandSuccess(new DeleteProfileCommand(), model, DeleteProfileCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
