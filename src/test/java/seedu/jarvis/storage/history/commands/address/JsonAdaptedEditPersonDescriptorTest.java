package seedu.jarvis.storage.history.commands.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.address.PersonBuilder.*;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.EditAddressCommand.EditPersonDescriptor;
import seedu.jarvis.testutil.address.EditPersonDescriptorBuilder;

/**
 * Tests the behaviour of {@code JsonAdaptedEditPersonDescriptor}.
 */
public class JsonAdaptedEditPersonDescriptorTest {
    private final String VALID_NAME = DEFAULT_NAME;
    private final String VALID_PHONE = DEFAULT_PHONE;
    private final String VALID_EMAIL = DEFAULT_EMAIL;
    private final String VALID_ADDRESS = DEFAULT_ADDRESS;
    private final String VALID_TAG = "Tag";

    @Test
    public void toModelType_validDescriptor_returnsEditPersonDescriptor() throws Exception {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME)
                .withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL)
                .withAddress(VALID_ADDRESS)
                .withTags(VALID_TAG)
                .build();
        JsonAdaptedEditPersonDescriptor jsonAdaptedEditPersonDescriptor = new JsonAdaptedEditPersonDescriptor(
                descriptor);
        assertEquals(descriptor, jsonAdaptedEditPersonDescriptor.toModelType());
    }
}
