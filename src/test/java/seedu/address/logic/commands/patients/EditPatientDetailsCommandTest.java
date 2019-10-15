package seedu.address.logic.commands.patients;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditPatientDetailsCommandTest {

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Model model = TestUtil.getTypicalModelManager();
        Person personToEdit = model.getFilteredPersonList().get(0);

        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(personToEdit, BOB);

        Model expectedModel = TestUtil.getTypicalModelManager();
        expectedModel.setPerson(personToEdit, BOB);
        String expectedMessage = String.format(EditPatientDetailsCommand.MESSAGE_EDIT_PERSON_SUCCESS, BOB);

        assertCommandSuccess(editPatientDetailsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedExceptIdUnfilteredList_success() {
        Model model = TestUtil.getTypicalModelManager();
        Person personToEdit = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(BOB).withId(personToEdit.getReferenceId().toString()).build();

        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(personToEdit, editedPerson);

        Model expectedModel = TestUtil.getTypicalModelManager();
        expectedModel.setPerson(personToEdit, editedPerson);
        String expectedMessage = String.format(EditPatientDetailsCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        assertCommandSuccess(editPatientDetailsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Model model = TestUtil.getTypicalModelManager();

        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                                      .withTags(VALID_TAG_HUSBAND).build();

        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(lastPerson, editedPerson);

        String expectedMessage = String.format(EditPatientDetailsCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = TestUtil.getTypicalModelManager();
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editPatientDetailsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(ALICE, ALICE);
        assertCommandFailure(editPatientDetailsCommand, model, EditPatientDetailsCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_filteredList_success() {
        Model model = TestUtil.getTypicalModelManager();
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(
            personInFilteredList, editedPerson);

        String expectedMessage = String.format(EditPatientDetailsCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = TestUtil.getTypicalModelManager();
        expectedModel.setPerson(personInFilteredList, editedPerson);

        assertCommandSuccess(editPatientDetailsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withId(secondPerson.getReferenceId().toString()).build();
        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(firstPerson, editedPerson);

        assertCommandFailure(editPatientDetailsCommand, model, EditPatientDetailsCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person firstPersonInList = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(
            firstPersonInList, secondPersonInList);

        assertCommandFailure(editPatientDetailsCommand, model, EditPatientDetailsCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void equals() {
        final EditPatientDetailsCommand standardCommand = new EditPatientDetailsCommand(ALICE, BOB);

        // same values -> returns true
        assertTrue(standardCommand.equals(new EditPatientDetailsCommand(ALICE, BOB)));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ExitCommand()));

        // different person to edit -> returns false
        assertFalse(standardCommand.equals(new EditPatientDetailsCommand(ALICE, CARL)));

        // different edited person -> returns false
        assertFalse(standardCommand.equals(new EditPatientDetailsCommand(CARL, BOB)));
    }

}
