package seedu.jarvis.storage.history.commands.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.jarvis.testutil.address.PersonBuilder.DEFAULT_ADDRESS;
import static seedu.jarvis.testutil.address.PersonBuilder.DEFAULT_EMAIL;
import static seedu.jarvis.testutil.address.PersonBuilder.DEFAULT_NAME;
import static seedu.jarvis.testutil.address.PersonBuilder.DEFAULT_PHONE;
import static seedu.jarvis.testutil.address.TypicalPersons.ALICE;
import static seedu.jarvis.testutil.address.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.address.EditAddressCommand;
import seedu.jarvis.logic.commands.address.EditAddressCommand.EditPersonDescriptor;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.testutil.address.EditPersonDescriptorBuilder;

/**
 * Tests the behaviour of {@code JsonAdaptedEditAddressCommand}.
 */
public class JsonAdaptedEditAddressCommandTest {
    private static final Index VALID_INDEX = INDEX_FIRST_PERSON;
    private static final EditPersonDescriptor VALID_DESCRIPTOR = new EditPersonDescriptorBuilder()
            .withName(DEFAULT_NAME)
            .withPhone(DEFAULT_PHONE)
            .withEmail(DEFAULT_EMAIL)
            .withAddress(DEFAULT_ADDRESS)
            .build();
    private static final Person ORIGINAL_PERSON = AMY;
    private static final Person EDITED_PERSON = ALICE;

    @Test
    public void toModelType_validIndexValidDescriptorValidOriginalPersonValidEditedPerson_returnsEditAddressCommand()
            throws Exception {
        EditAddressCommand editAddressCommand = new EditAddressCommand(VALID_INDEX, VALID_DESCRIPTOR, ORIGINAL_PERSON,
                EDITED_PERSON);
        assertEquals(editAddressCommand, new JsonAdaptedEditAddressCommand(editAddressCommand).toModelType());
    }

    @Test
    public void toModelType_validIndexValidDescriptorNullOriginalPersonNullEditedPerson_returnsEditAddressCommand()
            throws Exception {
        EditAddressCommand editAddressCommand = new EditAddressCommand(VALID_INDEX, VALID_DESCRIPTOR);
        assertEquals(editAddressCommand, new JsonAdaptedEditAddressCommand(editAddressCommand).toModelType());
    }

    @Test
    public void toModelType_validIndexValidDescriptorInvalidPersons_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, JsonAdaptedEditAddressCommand.MESSAGE_INVALID_FIELDS, () ->
                new JsonAdaptedEditAddressCommand(new EditAddressCommand(VALID_INDEX, VALID_DESCRIPTOR, null,
                        EDITED_PERSON)).toModelType());
        assertThrows(IllegalValueException.class, JsonAdaptedEditAddressCommand.MESSAGE_INVALID_FIELDS, () ->
                new JsonAdaptedEditAddressCommand(new EditAddressCommand(VALID_INDEX, VALID_DESCRIPTOR, ORIGINAL_PERSON,
                        null)).toModelType());
    }
}
