package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COVERAGE_UNASSIGNED_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_UNASSIGNED_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_AGE_UNASSIGNED_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_UNASSIGNED_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_UNASSIGNED_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_AGE_UNASSIGNED_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPolicyAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_POLICY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_POLICY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.BinItemBuilder;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.commons.util.PolicyBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

public class DeletePolicyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelWithUnreferencedPolicy;
    private Index indexOfUnreferencedPolicy;

    @BeforeEach
    public void setUpModelWithUnreferencedPolicy() {
        // Creating and adding a policy that is unreferenced by any persons
        Policy policyWithoutReference = new PolicyBuilder()
            .withName(VALID_NAME_UNASSIGNED_INSURANCE)
            .withDescription(VALID_DESCRIPTION_UNASSIGNED_INSURANCE)
            .withCoverage(VALID_COVERAGE_UNASSIGNED_INSURANCE)
            .withStartAge(VALID_START_AGE_UNASSIGNED_INSURANCE)
            .withEndAge(VALID_END_AGE_UNASSIGNED_INSURANCE)
            .withPrice(VALID_PRICE_UNASSIGNED_INSURANCE)
            .withCriteria()
            .withTags()
            .build();
        ModelManager modelToTest = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelToTest.addPolicy(policyWithoutReference);

        // Create a new ModelManager to reset statefulAddressBookList
        modelWithUnreferencedPolicy = new ModelManager(modelToTest.getAddressBook(), new UserPrefs());
        indexOfUnreferencedPolicy = Index.fromOneBased(modelWithUnreferencedPolicy.getFilteredPolicyList().size());
    }

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePolicyCommand(null));
    }

    /**
     * Test on unfiltered list for the case when a deleted policy is not referenced by persons in addressbook.
     */
    @Test
    public void execute_validIndexUnfilteredListWithoutPolicyReference_success() {
        Policy policyToDelete = modelWithUnreferencedPolicy.getFilteredPolicyList().get(indexOfUnreferencedPolicy.getZeroBased());
        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(indexOfUnreferencedPolicy);

        String expectedMessage = String.format(DeletePolicyCommand.MESSAGE_DELETE_POLICY_SUCCESS, policyToDelete);

        ModelManager expectedModel = new ModelManager(modelWithUnreferencedPolicy.getAddressBook(), new UserPrefs());
        expectedModel.deletePolicy(policyToDelete);
        BinItem policyToBin = new BinItem(policyToDelete);
        expectedModel.addBinItem(policyToBin);
        expectedModel.saveAddressBookState();

        assertCommandSuccess(deletePolicyCommand, modelWithUnreferencedPolicy, expectedMessage, expectedModel);
    }

    /**
     * Test on unfiltered list for the case when a deleted policy is referenced by persons in addressbook. This is more
     * complex since it involves removing references from both person list and binitem list.
     */
    @Test
    public void execute_validIndexUnfilteredListWithPolicyReference_success() {
        Policy policyToDelete = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(INDEX_FIRST_POLICY);

        String expectedMessage = String.format(DeletePolicyCommand.MESSAGE_DELETE_POLICY_SUCCESS, policyToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePolicy(policyToDelete);

        // Remove references to this policy in persons
        for (Person p : expectedModel.getAddressBook().getPersonList()) {
            if (p.hasPolicy(policyToDelete)) {
                Person editedPerson = new PersonBuilder(p).removePolicies(policyToDelete).build();
                expectedModel.setPerson(p, editedPerson);
            }
        }

        // Remove references to this policy in bin
        for (BinItem b : expectedModel.getAddressBook().getBinItemList()) {
            if (b.getItem() instanceof Person) {
                Person p = (Person) b.getItem();
                if (p.hasPolicy(policyToDelete)) {
                    Person editedPerson = new PersonBuilder(p).removePolicies(policyToDelete).build();
                    BinItem editedBinItem = new BinItemBuilder(b).withItem(editedPerson).build();
                    expectedModel.setBinItem(b, editedBinItem);
                }
            }
        }

        BinItem policyToBin = new BinItem(policyToDelete);
        expectedModel.addBinItem(policyToBin);
        expectedModel.saveAddressBookState();

        assertCommandSuccess(deletePolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPolicyList().size() + 1);
        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(outOfBoundIndex);

        assertCommandFailure(deletePolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    /**
     * Test on filtered list for the case when a deleted policy is not referenced by persons in addressbook.
     */
    @Test
    public void execute_validIndexFilteredListWithoutPolicyReference_success() {
        showPolicyAtIndex(modelWithUnreferencedPolicy, indexOfUnreferencedPolicy);

        Policy policyToDelete = modelWithUnreferencedPolicy.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(INDEX_FIRST_POLICY);

        String expectedMessage = String.format(DeletePolicyCommand.MESSAGE_DELETE_POLICY_SUCCESS, policyToDelete);

        Model expectedModel = new ModelManager(modelWithUnreferencedPolicy.getAddressBook(), new UserPrefs());
        expectedModel.deletePolicy(policyToDelete);
        BinItem policyToBin = new BinItem(policyToDelete);
        expectedModel.addBinItem(policyToBin);
        expectedModel.saveAddressBookState();
        showNoPolicy(expectedModel);

        assertCommandSuccess(deletePolicyCommand, modelWithUnreferencedPolicy, expectedMessage, expectedModel);
    }

    /**
     * Test on filtered list for the case when a deleted policy is referenced by persons in addressbook. This is more
     * complex since it involves removing references from both person list and binitem list.
     */
    @Test
    public void execute_validIndexFilteredListWithPolicyReference_success() {
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);

        Policy policyToDelete = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(INDEX_FIRST_POLICY);

        String expectedMessage = String.format(DeletePolicyCommand.MESSAGE_DELETE_POLICY_SUCCESS, policyToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePolicy(policyToDelete);

        // Remove references to this policy in persons
        for (Person p : expectedModel.getAddressBook().getPersonList()) {
            if (p.hasPolicy(policyToDelete)) {
                Person editedPerson = new PersonBuilder(p).removePolicies(policyToDelete).build();
                expectedModel.setPerson(p, editedPerson);
            }
        }

        // Remove references to this policy in bin
        for (BinItem b : expectedModel.getAddressBook().getBinItemList()) {
            if (b.getItem() instanceof Person) {
                Person p = (Person) b.getItem();
                if (p.hasPolicy(policyToDelete)) {
                    Person editedPerson = new PersonBuilder(p).removePolicies(policyToDelete).build();
                    BinItem editedBinItem = new BinItemBuilder(b).withItem(editedPerson).build();
                    expectedModel.setBinItem(b, editedBinItem);
                }
            }
        }

        BinItem policyToBin = new BinItem(policyToDelete);
        expectedModel.addBinItem(policyToBin);
        expectedModel.saveAddressBookState();
        showNoPolicy(expectedModel);

        assertCommandSuccess(deletePolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);

        Index outOfBoundIndex = INDEX_SECOND_POLICY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPolicyList().size());

        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(outOfBoundIndex);

        assertCommandFailure(deletePolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePolicyCommand deletePolicyFirstCommand = new DeletePolicyCommand(INDEX_FIRST_POLICY);
        DeletePolicyCommand deletePolicySecondCommand = new DeletePolicyCommand(INDEX_SECOND_POLICY);

        // same object -> returns true
        assertTrue(deletePolicyFirstCommand.equals(deletePolicyFirstCommand));

        // same values -> returns true
        DeletePolicyCommand deletePolicyFirstCommandCopy = new DeletePolicyCommand(INDEX_FIRST_POLICY);
        assertTrue(deletePolicyFirstCommand.equals(deletePolicyFirstCommandCopy));

        // different types -> returns false
        assertFalse(deletePolicyFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deletePolicyFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deletePolicyFirstCommand.equals(deletePolicySecondCommand));
    }

    private void showNoPolicy(Model model) {
        model.updateFilteredPolicyList(p -> false);

        assertTrue(model.getFilteredPolicyList().isEmpty());
    }

}
