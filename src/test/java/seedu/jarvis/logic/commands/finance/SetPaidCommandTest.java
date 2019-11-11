package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.finance.MonthlyLimit;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.model.finance.purchase.PurchaseDescription;
import seedu.jarvis.model.finance.purchase.PurchaseMoneySpent;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.model.viewstatus.ViewStatus;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.testutil.finance.PurchaseBuilder;

public class SetPaidCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), new UserPrefs(),
                new Planner(), new CoursePlanner());
    }

    /**
     * Verifies that checking {@code PaidCommand} for the availability of inverse execution returns true.
     */
    @BeforeEach
    public void hasInverseExecution() {
        Purchase validPurchase = new PurchaseBuilder().build();
        SetPaidCommand paidCommand = new SetPaidCommand(validPurchase);
        assertTrue(paidCommand.hasInverseExecution());
    }

    @Test
    public void constructor_nullPurchase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetPaidCommand(null));
    }

    @Test
    public void execute_purchaseAcceptedByModel_addSuccessful() throws CommandException {
        ModelStubAcceptingPurchaseAdded modelStub = new ModelStubAcceptingPurchaseAdded();
        Purchase validPurchase = new PurchaseBuilder().build();

        CommandResult commandResult = new SetPaidCommand(validPurchase).execute(modelStub);

        assertEquals(String.format(SetPaidCommand.MESSAGE_SUCCESS, validPurchase), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPurchase), modelStub.purchasesAdded);
    }

    @Test
    public void execute_purchaseAlreadyExists_throwsCommandException() {
        ModelStubAcceptingPurchaseAdded modelStub = new ModelStubAcceptingPurchaseAdded();
        modelStub.addPurchase(new PurchaseStub());

        SetPaidCommand setPaidCommand = new SetPaidCommand(new PurchaseStub());

        assertThrows(CommandException.class, () -> setPaidCommand.execute(modelStub));
    }

    /**
     * Ensures that the {@code CommandResult} with the appropriate message is returned from a successful inverse
     * execution, that the added purchase was deleted from the finance tracker.
     * */
    @Test
    public void executeInverse_success() {
        Purchase purchaseToAdd = new PurchaseBuilder().build();
        SetPaidCommand setPaidCommand = new SetPaidCommand(purchaseToAdd);

        String expectedMessage = String.format(SetPaidCommand.MESSAGE_SUCCESS,
                purchaseToAdd);

        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), new UserPrefs(), model.getPlanner(), model.getCoursePlanner());
        expectedModel.addPurchase(purchaseToAdd);

        assertCommandSuccess(setPaidCommand, model, expectedMessage, expectedModel);

        String inverseExpectedMessage = String.format(
                SetPaidCommand.MESSAGE_INVERSE_SUCCESS_DELETE, purchaseToAdd);

        assertCommandInverseSuccess(setPaidCommand, model, inverseExpectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Purchase movie = new PurchaseBuilder().withDescription("movie ticket").build();
        Purchase karaoke = new PurchaseBuilder().withDescription("karaoke night").build();
        SetPaidCommand addMovieCommand = new SetPaidCommand(movie);
        SetPaidCommand addKaraokeCommand = new SetPaidCommand(karaoke);

        // same object -> returns true
        assertTrue(addMovieCommand.equals(addMovieCommand));

        // same values -> returns true
        SetPaidCommand addMovieCommandCopy = new SetPaidCommand(movie);
        assertTrue(addMovieCommand.equals(addMovieCommandCopy));

        // different types -> returns false
        assertFalse(addMovieCommand.equals(1));

        // null -> returns false
        assertFalse(addMovieCommand.equals(null));

        // different purchase -> returns false
        assertFalse(addMovieCommand.equals(addKaraokeCommand));
    }

    /**
     * A Model stub that always accept the purchase being added.
     */
    private class ModelStubAcceptingPurchaseAdded extends ModelStub {
        final ArrayList<Purchase> purchasesAdded = new ArrayList<>();
        private ViewStatus viewStatus = new ViewStatus(ViewType.HOME_PAGE);

        @Override
        public void addPurchase(Purchase purchase) {
            requireNonNull(purchase);
            purchasesAdded.add(purchase);
        }

        @Override
        public boolean hasPurchase(Purchase purchase) {
            requireNonNull(purchase);
            return purchasesAdded.contains(purchase);
        }

        @Override
        public Optional<MonthlyLimit> getMonthlyLimit() {
            return model.getMonthlyLimit();
        }

        @Override
        public void setViewStatus(ViewType viewType) {
            viewStatus.setViewType(viewType);
        }

    }

    private static class PurchaseStub extends Purchase {
        public PurchaseStub() {
            super(new PurchaseDescription("lunch at Saizerya"), new PurchaseMoneySpent("5.00"),
                    LocalDate.parse("10/04/2019", Purchase.getDateFormat()));
        }
    }
}
