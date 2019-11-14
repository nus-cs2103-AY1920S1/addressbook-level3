package cs.f10.t1.nursetraverse.logic.commands;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.DESC_AMY;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.DESC_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_VISIT_TODO;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.assertCommandFailure;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.showPatientAtIndex;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.model.AppointmentBook;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.testutil.EditPatientDescriptorBuilder;
import cs.f10.t1.nursetraverse.testutil.PatientBuilder;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import cs.f10.t1.nursetraverse.testutil.TypicalIndexes;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                TypicalAppointments.getTypicalAppointmentBook());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient editedPatient = new PatientBuilder().build();
        //Ensure that this test case uses a Patient that doesn't have visits, because you cannot edit visits
        //and this testcase will fail if it uses a Patient with a visit
        //In this case we'll use the 4th patient, Daniel
        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(editedPatient).build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(4), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientBook(model.getStagedPatientBook()), new UserPrefs(),
                                               new AppointmentBook(model.getStagedAppointmentBook()));
        expectedModel.setPatient(model.getFilteredPatientList().get(3), editedPatient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPatient = Index.fromOneBased(model.getFilteredPatientList().size());
        Patient lastPatient = model.getFilteredPatientList().get(indexLastPatient.getZeroBased());

        PatientBuilder patientInList = new PatientBuilder(lastPatient);
        Patient editedPatient = patientInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).withVisitTodos(VALID_VISIT_TODO).build();

        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).withVisitTodos(VALID_VISIT_TODO).build();
        EditCommand editCommand = new EditCommand(indexLastPatient, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientBook(model.getStagedPatientBook()), new UserPrefs(),
                                               new AppointmentBook(model.getStagedAppointmentBook()));
        expectedModel.setPatient(lastPatient, editedPatient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand =
                new EditCommand(TypicalIndexes.INDEX_FIRST_PATIENT, new EditCommand.EditPatientDescriptor());
        Patient editedPatient = model.getFilteredPatientList().get(TypicalIndexes.INDEX_FIRST_PATIENT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientBook(model.getStagedPatientBook()), new UserPrefs(),
                                               new AppointmentBook(model.getStagedAppointmentBook()));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPatientAtIndex(model, TypicalIndexes.INDEX_FIRST_PATIENT);

        Patient patientInFilteredList = model.getFilteredPatientList()
                .get(TypicalIndexes.INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PATIENT,
                new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientBook(model.getStagedPatientBook()), new UserPrefs(),
                                               new AppointmentBook(model.getStagedAppointmentBook()));
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);
        expectedModel.setAppointments(patientInFilteredList, editedPatient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_samePatient_failure() {
        // Test for EditCommand bug in https://github.com/AY1920S1-CS2103-F10-1/main/issues/209
        // Try to edit an existing patient to have the same identity as another existing patient
        // Same identity = same name && (same phone || same email)

        // First, edit the patient to have the same name
        Patient firstPatient = model.getPatientByIndex(TypicalIndexes.INDEX_FIRST_PATIENT);
        Patient secondPatient = model.getPatientByIndex(TypicalIndexes.INDEX_SECOND_PATIENT);
        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(secondPatient)
                .withName(firstPatient.getName().toString()) // same name
                .build();
        EditCommand successfulEditCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_PATIENT, descriptor);

        PatientBook editedPatientBook = new PatientBook(model.getStagedPatientBook());
        Patient editedPatient = new PatientBuilder(secondPatient)
                .withName(firstPatient.getName().toString())
                .build();
        editedPatientBook.setPatient(secondPatient, editedPatient);
        Model expectedModel = new ModelManager(editedPatientBook, model.getUserPrefs(),
                model.getStagedAppointmentBook());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        assertCommandSuccess(successfulEditCommand, model, expectedMessage, expectedModel);

        // Then, try to edit the patient to also have the same phone
        secondPatient = model.getPatientByIndex(TypicalIndexes.INDEX_SECOND_PATIENT);
        descriptor = new EditPatientDescriptorBuilder(secondPatient)
                .withPhone(firstPatient.getPhone().toString()) // same phone
                .build();
        EditCommand failingEditCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_PATIENT, descriptor);

        assertCommandFailure(failingEditCommand, model, EditCommand.MESSAGE_DUPLICATE_PATIENT);

        // Try the same thing with the email
        secondPatient = model.getPatientByIndex(TypicalIndexes.INDEX_SECOND_PATIENT);
        descriptor = new EditPatientDescriptorBuilder(secondPatient)
                .withEmail(firstPatient.getEmail().toString()) // same email
                .build();
        failingEditCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_PATIENT, descriptor);

        assertCommandFailure(failingEditCommand, model, EditCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_duplicatePatientUnfilteredList_failure() {
        Patient firstPatient = model.getFilteredPatientList().get(TypicalIndexes.INDEX_FIRST_PATIENT.getZeroBased());
        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(firstPatient).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_PATIENT, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_duplicatePatientFilteredList_failure() {
        showPatientAtIndex(model, TypicalIndexes.INDEX_FIRST_PATIENT);

        // edit patient in filtered list into a duplicate in patient book
        Patient patientInList = model.getStagedPatientBook()
                .getPatientList().get(TypicalIndexes.INDEX_SECOND_PATIENT.getZeroBased());
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PATIENT,
                new EditPatientDescriptorBuilder(patientInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PATIENT, DESC_AMY);

        // same values -> returns true
        EditCommand.EditPatientDescriptor copyDescriptor = new EditCommand.EditPatientDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST_PATIENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND_PATIENT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_FIRST_PATIENT, DESC_BOB)));
    }

}
