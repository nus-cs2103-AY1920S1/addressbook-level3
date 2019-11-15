package dukecooks.logic.commands.profile;

import static dukecooks.testutil.profile.TypicalProfiles.getTypicalProfiles;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.profile.UserProfile;
import dukecooks.model.profile.person.Person;
import dukecooks.testutil.profile.EditPersonDescriptorBuilder;
import dukecooks.testutil.profile.PersonBuilder;

public class EditPersonCommandTest {

    private Model model = new ModelManager(getTypicalProfiles(), new UserPrefs());

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Person person = model.getFilteredPersonList().get(0);

        PersonBuilder personInList = new PersonBuilder(person);
        Person editedPerson = personInList.withName(CommandTestUtil.VALID_NAME_AMY).build();

        EditProfileCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_AMY).build();
        EditProfileCommand editProfileCommand = new EditProfileCommand(descriptor, false, false);

        String expectedMessage = String.format(EditProfileCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new UserProfile(model.getUserProfile()), new UserPrefs());
        expectedModel.setPerson(person, editedPerson);

        CommandTestUtil.assertCommandSuccess(editProfileCommand, model, expectedMessage, expectedModel);
    }
}
