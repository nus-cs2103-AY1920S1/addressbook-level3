package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
import static organice.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static organice.testutil.TypicalNrics.NRIC_FIRST_PERSON;
import static organice.testutil.TypicalNrics.NRIC_SECOND_PERSON;
import static organice.testutil.TypicalNrics.NRIC_THIRD_PERSON;
import static organice.testutil.TypicalPersons.DOCTOR_ALICE;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import organice.logic.commands.EditCommand.EditPersonDescriptor;
import organice.model.AddressBook;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Person;
import organice.testutil.DonorBuilder;
import organice.testutil.EditPersonDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = DOCTOR_ALICE;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(editedPerson.getNric(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Integer intLastPerson = model.getFilteredPersonList().size() - 1;
        Person lastPerson = model.getFilteredPersonList().get(intLastPerson);
        Nric nricLastPerson = lastPerson.getNric();

        DonorBuilder personInList = new DonorBuilder((Donor) lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_PATIENT_BOB).withPhone(VALID_PHONE_PATIENT_BOB).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_PATIENT_BOB)
                .withPhone(VALID_PHONE_PATIENT_BOB).build();
        EditCommand editCommand = new EditCommand(nricLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(NRIC_THIRD_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getOneBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(NRIC_FIRST_PERSON, DESC_DOCTOR_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_DOCTOR_AMY);
        EditCommand commandWithSameValues = new EditCommand(NRIC_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(NRIC_SECOND_PERSON, DESC_DOCTOR_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(NRIC_FIRST_PERSON, DESC_PATIENT_BOB)));
    }

}
