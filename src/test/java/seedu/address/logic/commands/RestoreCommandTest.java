package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BINITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BINITEM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.commons.util.PolicyBuilder;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

public class RestoreCommandTest {

    private Model personBinItemModel;
    private Model policyBinItemModel;

    @BeforeEach
    public void setUp() {
        ModelManager personModel = new ModelManager(new AddressBook(), new UserPrefs());
        personModel.addBinItem(new BinItem(new PersonBuilder().build()));
        personBinItemModel = new ModelManager(personModel.getAddressBook(), new UserPrefs());
        ModelManager policyModel = new ModelManager(new AddressBook(), new UserPrefs());
        policyModel.addBinItem(new BinItem(new PolicyBuilder().build()));
        policyBinItemModel = new ModelManager(policyModel.getAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullBinItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RestoreCommand(null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(personBinItemModel.getFilteredBinItemList().size() + 1);
        RestoreCommand restoreCommand = new RestoreCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, personBinItemModel, Messages.MESSAGE_INVALID_BIN_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Test restore for the case when bin item is a person.
     */
    @Test
    public void execute_validIndexRestoresPerson_success() {
        BinItem itemToRestore = personBinItemModel.getFilteredBinItemList().get(INDEX_FIRST_BINITEM.getZeroBased());
        RestoreCommand restoreCommand = new RestoreCommand(INDEX_FIRST_BINITEM);

        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_SUCCESS, itemToRestore);

        ModelManager expectedModel = new ModelManager(personBinItemModel.getAddressBook(), new UserPrefs());
        expectedModel.deleteBinItem(itemToRestore);
        Person personToRestore = (Person) itemToRestore.getItem();
        expectedModel.addPerson(new PersonBuilder(personToRestore).build());
        expectedModel.saveAddressBookState();

        assertCommandSuccess(restoreCommand, personBinItemModel, expectedMessage, expectedModel);
    }

    /**
     * Test restore for the case when bin item is a policy.
     */
    @Test
    public void execute_validIndexRestoresPolicy_success() {
        BinItem itemToRestore = policyBinItemModel.getFilteredBinItemList().get(INDEX_FIRST_BINITEM.getZeroBased());
        RestoreCommand restoreCommand = new RestoreCommand(INDEX_FIRST_BINITEM);

        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_SUCCESS, itemToRestore);

        ModelManager expectedModel = new ModelManager(policyBinItemModel.getAddressBook(), new UserPrefs());
        expectedModel.deleteBinItem(itemToRestore);
        Policy policyToRestore = (Policy) itemToRestore.getItem();
        expectedModel.addPolicy(new PolicyBuilder(policyToRestore).build());
        expectedModel.saveAddressBookState();

        assertCommandSuccess(restoreCommand, policyBinItemModel, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        RestoreCommand restoreFirstBinItemCommand = new RestoreCommand(INDEX_FIRST_BINITEM);
        RestoreCommand restoreSecondBinItemCommand = new RestoreCommand(INDEX_SECOND_BINITEM);

        // same object -> returns true
        assertTrue(restoreFirstBinItemCommand.equals(restoreFirstBinItemCommand));

        // same values -> returns true
        RestoreCommand restoreFirstBinItemCommandCopy = new RestoreCommand(INDEX_FIRST_BINITEM);
        assertTrue(restoreFirstBinItemCommand.equals(restoreFirstBinItemCommandCopy));

        // different types -> returns false
        assertFalse(restoreFirstBinItemCommand.equals(1));

        // null -> returns false
        assertFalse(restoreFirstBinItemCommand.equals(null));

        // different binItem -> returns false
        assertFalse(restoreFirstBinItemCommand.equals(restoreSecondBinItemCommand));
    }

}
