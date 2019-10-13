package seedu.jarvis.storage.history.commands.address;

import static org.junit.jupiter.api.Assertions.*;

import static seedu.jarvis.testutil.address.TypicalPersons.AMY;
import static seedu.jarvis.testutil.address.TypicalPersons.BENSON;
import static seedu.jarvis.testutil.address.TypicalPersons.CARL;
import static seedu.jarvis.testutil.address.TypicalPersons.DANIEL;
import static seedu.jarvis.testutil.address.TypicalPersons.ELLE;
import static seedu.jarvis.testutil.address.TypicalPersons.FIONA;
import static seedu.jarvis.testutil.address.TypicalPersons.GEORGE;
import static seedu.jarvis.testutil.history.TypicalCommands.*;

import org.junit.jupiter.api.Test;

public class JsonAdaptedAddAddressCommandTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    @Test
    public void toModelType_addValidPerson_returnsAddCommand() throws Exception {
        JsonAdaptedAddAddressCommand jsonAdaptedAddAddressCommand = new JsonAdaptedAddAddressCommand(ADD_AMY);
        assertEquals(ADD_AMY, jsonAdaptedAddAddressCommand.toModelType());
    }

}
