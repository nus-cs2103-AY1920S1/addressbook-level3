package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.model.viewstatus.ViewType.LIST_CCA;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.cca.TypicalCcaMilestones.GRADE_ONE;
import static seedu.jarvis.testutil.cca.TypicalCcaMilestones.TIGER;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaList;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.model.viewstatus.ViewStatus;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.testutil.cca.CcaBuilder;

/**
 * AddProgressTest basically checks just 2 scenarios - adding a new {@code CcaProgress} and adding
 * a duplicate {@code CcaProgress}.
 * Since this test is not an integration test, we can just use a model stub.
 */
public class AddProgressCommandTest {

    @Test
    public void execute_ccaProgressAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCcaAdded modelStub = new ModelStubAcceptingCcaAdded();
        Cca validCca = new CcaBuilder().build();
        new AddCcaCommand(validCca).execute(modelStub);
        Index index = Index.fromOneBased(1);
        CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
        ccaMilestoneList.setMilestones(List.of(TIGER));
        CommandResult commandResult = new AddProgressCommand(index, ccaMilestoneList).execute(modelStub);

        assertEquals(String.format(AddProgressCommand.MESSAGE_SUCCESS, index.getOneBased()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateCcaProgress_throwsCommandException() throws Exception {
        ModelStubAcceptingCcaAdded modelStub = new ModelStubAcceptingCcaAdded();
        Cca validCca = new CcaBuilder().build();
        new AddCcaCommand(validCca).execute(modelStub);
        Index index = Index.fromOneBased(1);
        CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
        ccaMilestoneList.setMilestones(List.of(TIGER));
        AddProgressCommand addProgressCommand = new AddProgressCommand(index, ccaMilestoneList);
        addProgressCommand.execute(modelStub);
        AddProgressCommand duplicateAddProgressCommand = new AddProgressCommand(index, ccaMilestoneList);

        assertThrows(CommandException.class,
                AddProgressCommand.MESSAGE_CCA_PROGRESS_ALREADY_SET, () ->
                        duplicateAddProgressCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        CcaMilestoneList canoeingMilestoneList = new CcaMilestoneList();
        canoeingMilestoneList.setMilestones(List.of(TIGER));
        CcaMilestoneList guitarMilestoneList = new CcaMilestoneList();
        guitarMilestoneList.setMilestones(List.of(GRADE_ONE));
        AddProgressCommand addCanoeingProgressCommand = new AddProgressCommand(Index.fromOneBased(1),
                canoeingMilestoneList);
        AddProgressCommand addGuitarProgressCommand = new AddProgressCommand(Index.fromOneBased(1),
                guitarMilestoneList);

        // same object -> returns true
        assertTrue(addCanoeingProgressCommand.equals(addCanoeingProgressCommand));

        // same values -> returns true
        AddProgressCommand addCanoeingProgressCommandCopy = new AddProgressCommand(Index.fromOneBased(1),
                canoeingMilestoneList);
        assertTrue(addCanoeingProgressCommand.equals(addCanoeingProgressCommandCopy));

        // different types -> returns false
        assertFalse(addCanoeingProgressCommand.equals(1));

        // null -> returns false
        assertFalse(addCanoeingProgressCommandCopy.equals(null));

        // different commands -> returns false
        assertFalse(addCanoeingProgressCommand.equals(addGuitarProgressCommand));
    }

    /**
     * A Model stub that always accept the Cca being added.
     */
    private class ModelStubAcceptingCcaAdded extends ModelStub {
        private final CcaList ccaList = new CcaList();
        private final CcaTracker ccaTracker = new CcaTracker(ccaList);
        private final ViewStatus viewStatus = new ViewStatus(LIST_CCA);

        @Override
        public boolean containsCca(Cca cca) {
            requireNonNull(cca);
            return ccaTracker.containsCca(cca);
        }

        @Override
        public void addCca(Cca cca) {
            requireNonNull(cca);
            ccaTracker.addCca(cca);
        }

        @Override
        public int getNumberOfCcas() {
            return ccaTracker.getNumberOfCcas();
        }

        @Override
        public boolean ccaContainsProgress(Index targetIndex) {
            return ccaTracker.ccaContainsProgress(targetIndex);
        }

        @Override
        public Cca getCca(Index index) {
            return ccaTracker.getCca(index);
        }

        @Override
        public void addProgress(Cca targetCca, CcaMilestoneList toAddCcaMilestoneList) {
            ccaTracker.addProgress(targetCca, toAddCcaMilestoneList);
        }

        @Override
        public void updateFilteredCcaList(Predicate<Cca> predicate) {
            ccaTracker.updateFilteredCcaList(predicate);
        }

        @Override
        public void setViewStatus(ViewType viewType) {
            requireNonNull(viewType);
            viewStatus.setViewType(viewType);
        }
    }
}
