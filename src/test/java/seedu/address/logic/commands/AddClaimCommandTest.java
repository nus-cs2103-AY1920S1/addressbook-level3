package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FinSec;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.claim.Claim;
import seedu.address.testutil.ClaimBuilder;

public class AddClaimCommandTest {

    @Test
    public void constructor_nullClaim_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(null));
    }

    @Test
    public void execute_claimAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingClaimAdded modelStub = new ModelStubAcceptingClaimAdded();
        Claim validClaim = new ClaimBuilder().build();

        CommandResult commandResult = new AddClaimCommand(validClaim).execute(modelStub);

        assertEquals(String.format(AddClaimCommand.MESSAGE_SUCCESS, validClaim), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validClaim), modelStub.claimsAdded);
    }

    @Test
    public void execute_duplicateClaim_throwsCommandException() {
        Claim validClaim = new ClaimBuilder().build();
        AddClaimCommand addContactCommand = new AddClaimCommand(validClaim);
        ModelStub modelStub = new ModelStubWithClaim(validClaim);

        assertThrows(CommandException.class,
                AddClaimCommand.MESSAGE_DUPLICATE_CLAIM, () -> addContactCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Claim logistics = new ClaimBuilder().withDescription("Logistics").build();
        Claim transport = new ClaimBuilder().withDescription("Transport").build();
        AddClaimCommand addLogisticsCommand = new AddClaimCommand(logistics);
        AddClaimCommand addTransportCommand = new AddClaimCommand(transport);

        // same object -> returns true
        assertEquals(addLogisticsCommand, addLogisticsCommand);

        // same values -> returns true
        AddClaimCommand addAliceCommandCopy = new AddClaimCommand(logistics);
        assertEquals(addLogisticsCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addLogisticsCommand);

        // null -> returns false
        assertNotEquals(null, addLogisticsCommand);

        // different claims -> returns false
        assertNotEquals(addLogisticsCommand, addTransportCommand);
    }


    /**
     * A Model stub that contains a single claim.
     */
    private class ModelStubWithClaim extends ModelStub {
        private final Claim claim;

        ModelStubWithClaim(Claim claim) {
            requireNonNull(claim);
            this.claim = claim;
        }

        @Override
        public boolean hasClaim(Claim claim) {
            requireNonNull(claim);
            return this.claim.isSameClaim(claim);
        }

        @Override
        public boolean hasContactFor(Claim toAdd) {
            requireNonNull(toAdd);
            return true;
        }
    }

    /**
     * A Model stub that always accept the claim being added.
     */
    private class ModelStubAcceptingClaimAdded extends ModelStub {
        final ArrayList<Claim> claimsAdded = new ArrayList<>();

        @Override
        public boolean hasClaim(Claim claim) {
            requireNonNull(claim);
            return claimsAdded.stream().anyMatch(claim::isSameClaim);
        }

        @Override
        public void addClaim(Claim claim) {
            requireNonNull(claim);
            claimsAdded.add(claim);
        }

        @Override
        public ReadOnlyFinSec getFinSec() {
            return new FinSec();
        }

        @Override
        public boolean hasContactFor(Claim toAdd) {
            requireNonNull(toAdd);
            return true;
        }
    }
}
