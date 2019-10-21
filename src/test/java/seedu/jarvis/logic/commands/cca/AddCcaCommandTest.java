package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaList;
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.testutil.cca.CcaBuilder;

/**
 * AddCcaCommandTest basically checks just 3 scenarios - adding null, adding a new {@code Cca} and adding
 * a duplicate {@code Cca}.
 * Since this test is not an integration test, we can just use a model stub.
 */
public class AddCcaCommandTest {

    @Test
    public void constructor_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCcaCommand(null));
    }

    @Test
    public void execute_ccaAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCcaAdded modelStub = new ModelStubAcceptingCcaAdded();
        Cca validCca = new CcaBuilder().build();

        CommandResult commandResult = new AddCcaCommand(validCca).execute(modelStub);

        assertEquals(String.format(AddCcaCommand.MESSAGE_SUCCESS, validCca), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateCca_throwsCommandException() {
        Cca validCca = new CcaBuilder().build();
        AddCcaCommand addCcaCommand = new AddCcaCommand(validCca);
        ModelStub modelStub = new ModelStubWithCca(validCca);

        assertThrows(CommandException.class,
                AddCcaCommand.MESSAGE_DUPLICATE_CCA, () -> addCcaCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Cca canoeing = new CcaBuilder().withName("Canoeing").build();
        Cca guitarEnsemble = new CcaBuilder().withName("Guitar ensemble").build();
        AddCcaCommand addCanoeingCommand = new AddCcaCommand((canoeing));
        AddCcaCommand addGuitarCommand = new AddCcaCommand((guitarEnsemble));

        // same object -> returns true
        assertTrue(addCanoeingCommand.equals(addCanoeingCommand));

        // same values -> returns true
        AddCcaCommand addCanoeingCommandCopy = new AddCcaCommand(canoeing);
        assertTrue(addCanoeingCommand.equals(addCanoeingCommandCopy));

        // different types -> returns false
        assertFalse(addCanoeingCommand.equals(1));

        // null -> returns false
        assertFalse(addCanoeingCommand.equals(null));

        // different person -> returns false
        assertFalse(addCanoeingCommand.equals(addGuitarCommand));
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithCca extends ModelStub {
        private final Cca cca;

        ModelStubWithCca(Cca cca) {
            requireNonNull(cca);
            this.cca = cca;
        }

        @Override
        public boolean containsCca(Cca cca) {
            requireNonNull(cca);
            return this.cca.isSameCca(cca);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingCcaAdded extends ModelStub {
        private final CcaList ccaList = new CcaList();

        @Override
        public boolean containsCca(Cca cca) {
            requireNonNull(cca);
            return ccaList.containsCca(cca);
        }

        @Override
        public void addCca(Cca cca) {
            requireNonNull(cca);
            ccaList.addCca(cca);
        }
    }
}
