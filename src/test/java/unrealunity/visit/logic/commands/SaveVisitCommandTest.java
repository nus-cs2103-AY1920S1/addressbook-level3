package unrealunity.visit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.model.AddressBook;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.ModelManager;
import unrealunity.visit.model.UserPrefs;
import unrealunity.visit.model.person.Person;
import unrealunity.visit.model.person.VisitReport;
import unrealunity.visit.testutil.PersonBuilder;
import unrealunity.visit.testutil.TypicalIndexes;
import unrealunity.visit.testutil.TypicalPersons;
import unrealunity.visit.testutil.TypicalVisits;


public class SaveVisitCommandTest {
    private static final int NEW_REPORT = -1;
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private VisitReport report = TypicalVisits.REPORT_2;

    @Test
    public void execute_saveVisitUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withVisitList(TypicalVisits.getLongTypicalVisitList(firstPerson.getName().toString())).build();

        SaveVisitCommand saveVisitCommand = new SaveVisitCommand(
            TypicalIndexes.INDEX_FIRST_PERSON.getOneBased(), NEW_REPORT,
                report.date, report.getMedication(), report.getDiagnosis(), report.getRemarks());

        String expectedMessage = String.format(SaveVisitCommand.MESSAGE_SAVE_VISIT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        CommandTestUtil.assertCommandSuccess(saveVisitCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SaveVisitCommand saveVisitCommand = new SaveVisitCommand(outOfBoundIndex.getOneBased(), NEW_REPORT,
                report.date, report.getMedication(), report.getDiagnosis(), report.getRemarks());

        CommandTestUtil.assertCommandFailure(saveVisitCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        SaveVisitCommand saveVisitCommand = new SaveVisitCommand(outOfBoundIndex.getOneBased(),
                NEW_REPORT, report.date,
                report.getMedication(), report.getDiagnosis(), report.getRemarks());
        CommandTestUtil.assertCommandFailure(saveVisitCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {

        SaveVisitCommand saveVisitCommand =
                new SaveVisitCommand(1, NEW_REPORT, "12/12/2012", "meds", "diagnosis", "remarks");

        // same object -> returns true
        assertTrue(saveVisitCommand.equals(saveVisitCommand));

        // same values -> returns true
        SaveVisitCommand saveVisitCommand2 =
                new SaveVisitCommand(1, NEW_REPORT, "12/12/2012", "meds", "diagnosis", "remarks");

        assertTrue(saveVisitCommand.equals(saveVisitCommand2));

        // different types -> returns false
        assertFalse(saveVisitCommand.equals(1));

        // null -> returns false
        assertFalse(saveVisitCommand2.equals(null));

        // different idx -> returns false
        SaveVisitCommand saveVisitCommandIdx =
                new SaveVisitCommand(2, NEW_REPORT, "12/12/2012", "meds", "diagnosis", "remarks");
        assertFalse(saveVisitCommand2.equals(saveVisitCommandIdx));

        SaveVisitCommand saveVisitCommandDate =
                new SaveVisitCommand(1, NEW_REPORT, "13/12/2012", "meds", "diagnosis", "remarks");
        assertFalse(saveVisitCommand2.equals(saveVisitCommandDate));

        SaveVisitCommand saveVisitCommandMeds =
                new SaveVisitCommand(1, NEW_REPORT, "12/12/2012", "medicine", "diagnosis", "remarks");
        assertFalse(saveVisitCommand2.equals(saveVisitCommandMeds));

        SaveVisitCommand saveVisitCommandDiagnosis =
                new SaveVisitCommand(1, NEW_REPORT, "12/12/2012", "meds", "dg", "remarks");
        assertFalse(saveVisitCommand2.equals(saveVisitCommandDiagnosis));

        SaveVisitCommand saveVisitCommandRemark =
                new SaveVisitCommand(1, NEW_REPORT, "12/12/2012", "meds", "dg", "rm");

        assertFalse(saveVisitCommand2.equals(saveVisitCommandRemark));

    }

}
