package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Views.FIRST_VIEW;
import static seedu.address.testutil.Views.SECOND_VIEW;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.View;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.UniqueClaimsList;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactsList;
import seedu.address.model.income.Income;
import seedu.address.model.income.UniqueIncomesList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code GotoCommand}.
 */
//@@author {lawncegoh}
class GotoCommandTest extends GotoCommand {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GotoCommand(null));
    }

    @Test
    public void execute_invalidView_throwsCommandException() throws CommandException {
        ModelStubForValidView model = new ModelStubForValidView();
        View view = new View("lost", 6);
        assertThrows(CommandException.class, () -> new GotoCommand(view).execute(model));
    }

    @Test
    public void execute_validContactView_gotoSuccess() throws CommandException {
        ModelStubForValidView model = new ModelStubForValidView();
        View view = new View("contacts", 1);
        CommandResult commandResult = new GotoCommand(view).execute(model);
        assertEquals(MESSAGE_SUCCESS_CONTACTS, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validClaimView_gotoSuccess() throws CommandException {
        ModelStubForValidView model = new ModelStubForValidView();
        View view = new View("claims", 2);
        CommandResult commandResult = new GotoCommand(view).execute(model);
        assertEquals(MESSAGE_SUCCESS_CLAIMS, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validIncomeView_gotoSuccess() throws CommandException {
        ModelStubForValidView model = new ModelStubForValidView();
        View view = new View("incomes", 3);
        CommandResult commandResult = new GotoCommand(view).execute(model);
        assertEquals(MESSAGE_SUCCESS_INCOMES, commandResult.getFeedbackToUser());
    }

    @Test
    public void equalsForEqualOnes() {
        GotoCommand gotoFirstCommand = new GotoCommand(FIRST_VIEW);
        // same object -> returns true
        assertTrue(gotoFirstCommand.equals(gotoFirstCommand));

        // same values -> returns true
        GotoCommand gotoFirstCommandCopy = new GotoCommand(FIRST_VIEW);
        assertEquals(gotoFirstCommand, gotoFirstCommandCopy);
    }
    @Test
    public void equalsForNotEqualOnes() {
        GotoCommand gotoFirstCommand = new GotoCommand(FIRST_VIEW);
        GotoCommand gotoSecondCommand = new GotoCommand(SECOND_VIEW);

        assertNotEquals(1, gotoFirstCommand);

        // different types -> returns false
        assertFalse(gotoFirstCommand.equals(1));
        // different types -> returns false;

        // null -> returns false
        assertFalse(gotoFirstCommand.equals(null));

        // different gotoCommands -> returns false
        assertFalse(gotoFirstCommand.equals(gotoSecondCommand));
    }

    /**
     * Returns a modelstub that has the updatefilteredlist method "activated"
     */
    private class ModelStubForValidView extends ModelStub {
        private UniqueIncomesList incomesList = new UniqueIncomesList();
        private FilteredList<Income> filteredIncomes = new FilteredList<>(incomesList.asUnmodifiableObservableList());
        private UniqueContactsList contactsList = new UniqueContactsList();
        private FilteredList<Contact> filteredContacts =
                new FilteredList<>(contactsList.asUnmodifiableObservableList());
        private UniqueClaimsList claimsList = new UniqueClaimsList();
        private FilteredList<Claim> filteredClaims = new FilteredList<>(claimsList.asUnmodifiableObservableList());

        @Override
        public void updateFilteredClaimList(Predicate<Claim> predicate) {
            filteredClaims.setPredicate(p -> true);
        }

        @Override
        public void updateFilteredIncomeList(Predicate<Income> predicate) {
            filteredIncomes.setPredicate(p -> true);
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            filteredContacts.setPredicate(p -> true);
        }
    }
}
