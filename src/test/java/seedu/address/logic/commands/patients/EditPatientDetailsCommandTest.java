package seedu.address.logic.commands.patients;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_EDITED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditPatientDetailsCommandTest {

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        Person personToEdit = model.getFilteredPatientList().get(0);

        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(personToEdit, BOB);

        String expectedMessage = String.format(EditPatientDetailsCommand.MESSAGE_PATIENT_IN_QUEUE, BOB);;

        assertCommandFailure(editPatientDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_allFieldsSpecifiedExceptIdUnfilteredList_success() {
        Model model = TestUtil.getTypicalModelManager();
        Person personToEdit = model.getFilteredPatientList().get(0);
        Person editedPerson = new PersonBuilder(BOB).withPatientId(personToEdit.getReferenceId().toString()).build();

        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(personToEdit, editedPerson);

        String expectedMessage = String.format(EditPatientDetailsCommand.MESSAGE_PATIENT_IN_QUEUE, editedPerson);

        assertCommandFailure(editPatientDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_allFieldsSpecifiedAreSimiliarToOriginalExceptIdUnfilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        Person personToEdit = model.getFilteredPatientList().get(0);
        Person editedPerson = model.getFilteredPatientList().get(0);

        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(personToEdit, editedPerson);
        assertCommandFailure(editPatientDetailsCommand, model,
                MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();

        Index indexLastPerson = Index.fromOneBased(model.getFilteredPatientList().size());
        Person lastPerson = model.getFilteredPatientList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                                      .withTags(VALID_TAG_HUSBAND).build();

        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(lastPerson, editedPerson);

        String expectedMessage = String.format(EditPatientDetailsCommand.MESSAGE_PATIENT_IN_QUEUE, editedPerson);

        assertCommandFailure(editPatientDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(ALICE, ALICE);
        assertCommandFailure(editPatientDetailsCommand, model,
                MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_filteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(
            personInFilteredList, editedPerson);

        String expectedMessage = EditPatientDetailsCommand.MESSAGE_PATIENT_IN_QUEUE;

        assertCommandFailure(editPatientDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        Person firstPerson = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPatientList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withPatientId(secondPerson.getReferenceId().toString()).build();
        EditPatientDetailsCommand editPatientDetailsCommand = new EditPatientDetailsCommand(firstPerson, editedPerson);

        assertCommandFailure(editPatientDetailsCommand, model, EditPatientDetailsCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person firstPersonInList = model.getPatientAddressBook()
                .getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonInList = model.getPatientAddressBook()
                .getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
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
