package unrealunity.visit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.AddressBook;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.ModelManager;
import unrealunity.visit.model.UserPrefs;
import unrealunity.visit.model.person.Person;
import unrealunity.visit.testutil.PersonBuilder;
import unrealunity.visit.testutil.TypicalIndexes;
import unrealunity.visit.testutil.TypicalPersons;
import unrealunity.visit.testutil.TypicalVisits;

public class DeleteVisitCommandTest {

    private static final int VALID_REPORT_INDEX = 3;
    private static final int INVALID_REPORT_INDEX = 0;
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteVisitCommand_success() {
        Person firstPerson = model.getFilteredPersonList().get(TypicalIndexes.INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withVisitList(TypicalVisits.getLongTypicalVisitList(firstPerson.getName().toString())).build();

        DeleteVisitCommand deleteVisitCommand = new DeleteVisitCommand(TypicalIndexes.INDEX_SECOND_PERSON,
                VALID_REPORT_INDEX);

        String expectedMessage = String.format(DeleteVisitCommand.MESSAGE_DELETE_VISIT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, editedPerson.getVisitList().getObservableRecords());

        try {
            CommandResult result = deleteVisitCommand.execute(model);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_invalidReportIndex_failure() {

        DeleteVisitCommand deleteVisitCommand = new DeleteVisitCommand(TypicalIndexes.INDEX_SECOND_PERSON,
                INVALID_REPORT_INDEX);

        DeleteVisitCommand deleteVisitCommand1 = new DeleteVisitCommand(TypicalIndexes.INDEX_SECOND_PERSON,
                model.getFilteredPersonList()
                        .get(TypicalIndexes.INDEX_SECOND_PERSON.getZeroBased()).getVisitList().getRecords().size() + 1);

        CommandTestUtil.assertCommandFailure(deleteVisitCommand, model, Messages.MESSAGE_INVALID_REPORT_INDEX);
        CommandTestUtil.assertCommandFailure(deleteVisitCommand1, model, Messages.MESSAGE_INVALID_REPORT_INDEX);
    }

    @Test
    public void equals() {
        DeleteVisitCommand deleteFirstCommand = new DeleteVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON, 1);
        DeleteVisitCommand deleteSecondCommand = new DeleteVisitCommand(TypicalIndexes.INDEX_SECOND_PERSON, 1);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteVisitCommand deleteFirstCommandCopy = new DeleteVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON, 1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        DeleteCommand deleteFirstCommandNoIndex = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandNoIndex));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
