package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPhones.IPHONE_JSON_TEST;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;

public class JsonAdaptedPhoneTest {

    private static final String INVALID_IDENTITY_NUM = "aaaaaaa";
    private static final String INVALID_SERIAL_NUM = "";
    private static final String INVALID_NAME = "AA#!@!#!##!#";
    private static final String INVALID_BRAND = " ";
    private static final String INVALID_CAPACITY = "0000000132313";
    private static final String INVALID_COLOUR = " ";
    private static final String INVALID_COST = "$313.1231313131321331";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_IDENTITY_NUM = IPHONE_JSON_TEST.getIdentityNumber().value;
    private static final String VALID_SERIAL_NUM = IPHONE_JSON_TEST.getSerialNumber().value;
    private static final String VALID_NAME = IPHONE_JSON_TEST.getPhoneName().fullName;
    private static final String VALID_BRAND = IPHONE_JSON_TEST.getBrand().value;
    private static final String VALID_CAPACITY = IPHONE_JSON_TEST.getCapacity().toString().split("GB")[0];
    private static final String VALID_COLOUR = IPHONE_JSON_TEST.getColour().value;
    private static final String VALID_COST = IPHONE_JSON_TEST.getCost().value;
    private static final List<JsonAdaptedTag> VALID_TAGS = IPHONE_JSON_TEST.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPhoneDetails_returnsPhone() throws Exception {
        JsonAdaptedPhone phone = new JsonAdaptedPhone(IPHONE_JSON_TEST);
        assertEquals(IPHONE_JSON_TEST, phone.toModelType());
    }

    @Test
    public void toModelType_invalidIdentityNum_throwsIllegalValueException() {
        JsonAdaptedPhone phone = new JsonAdaptedPhone(INVALID_IDENTITY_NUM, VALID_SERIAL_NUM,
                VALID_NAME, VALID_BRAND, VALID_CAPACITY, VALID_COLOUR, VALID_COST, VALID_TAGS);
        String expectedMessage = IdentityNumber.MESSAGE_CONSTRAINTS;

        assertThrows(IllegalValueException.class, expectedMessage, phone::toModelType);
    }

    @Test
    public void toModelType_invalidSerialNum_throwsIllegalValueException() {
        JsonAdaptedPhone phone = new JsonAdaptedPhone(VALID_IDENTITY_NUM, INVALID_SERIAL_NUM,
                VALID_NAME, VALID_BRAND, VALID_CAPACITY, VALID_COLOUR, VALID_COST, VALID_TAGS);
        String expectedMessage = SerialNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, phone::toModelType);
    }

    @Test
    public void toModelType_invalidPhoneName_throwsIllegalValueException() {
        JsonAdaptedPhone phone = new JsonAdaptedPhone(VALID_IDENTITY_NUM, VALID_SERIAL_NUM,
                INVALID_NAME, VALID_BRAND, VALID_CAPACITY, VALID_COLOUR, VALID_COST, VALID_TAGS);
        String expectedMessage = PhoneName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, phone::toModelType);
    }

    @Test
    public void toModelType_invalidBrand_throwsIllegalValueException() {
        JsonAdaptedPhone phone = new JsonAdaptedPhone(VALID_IDENTITY_NUM, VALID_SERIAL_NUM,
                VALID_NAME, INVALID_BRAND, VALID_CAPACITY, VALID_COLOUR, VALID_COST, VALID_TAGS);
        String expectedMessage = Brand.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, phone::toModelType);
    }

    @Test
    public void toModelType_invalidCapacity_throwsIllegalValueException() {
        JsonAdaptedPhone phone = new JsonAdaptedPhone(VALID_IDENTITY_NUM, VALID_SERIAL_NUM,
                VALID_NAME, VALID_BRAND, INVALID_CAPACITY, VALID_COLOUR, VALID_COST, VALID_TAGS);
        String expectedMessage = Capacity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, phone::toModelType);
    }

    @Test
    public void toModelType_invalidColour_throwsIllegalValueException() {
        JsonAdaptedPhone phone = new JsonAdaptedPhone(VALID_IDENTITY_NUM, VALID_SERIAL_NUM,
                VALID_NAME, VALID_BRAND, VALID_CAPACITY, INVALID_COLOUR, VALID_COST, VALID_TAGS);
        String expectedMessage = Colour.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, phone::toModelType);
    }

    @Test
    public void toModelType_invalidCost_throwsIllegalValueException() {
        JsonAdaptedPhone phone = new JsonAdaptedPhone(VALID_IDENTITY_NUM, VALID_SERIAL_NUM,
                VALID_NAME, VALID_BRAND, VALID_CAPACITY, VALID_COLOUR, INVALID_COST, VALID_TAGS);
        String expectedMessage = Cost.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, phone::toModelType);
    }

    @Test
    public void toModelType_invalidTag_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPhone phone = new JsonAdaptedPhone(VALID_IDENTITY_NUM, VALID_SERIAL_NUM,
                VALID_NAME, VALID_BRAND, VALID_CAPACITY, VALID_COLOUR, VALID_COST, invalidTags);
        assertThrows(IllegalValueException.class, phone::toModelType);
    }




}
