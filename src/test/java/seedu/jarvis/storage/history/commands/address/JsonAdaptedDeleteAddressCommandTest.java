package seedu.jarvis.storage.history.commands.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.jarvis.testutil.address.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.DeleteAddressCommand;

/**
 * Tests the behaviour {@code JsonAdaptedDeleteAddressCommand}.
 */
public class JsonAdaptedDeleteAddressCommandTest {
    @Test
    public void toModelType_validPersonValidIndex_returnsDeleteAddressCommand() throws Exception {
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON, ALICE);
        assertEquals(deleteAddressCommand, new JsonAdaptedDeleteAddressCommand(deleteAddressCommand).toModelType());
    }

    @Test
    public void toModelType_validPersonNullIndex_returnsDeleteAddressCommand() throws Exception {
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON);
        assertEquals(deleteAddressCommand, new JsonAdaptedDeleteAddressCommand(deleteAddressCommand).toModelType());
    }

}
