//package seedu.guilttrip.storage;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.guilttrip.storage.JsonAdaptedEntry.MISSING_FIELD_MESSAGE_FORMAT;
//import static seedu.guilttrip.testutil.Assert.assertThrows;
//import static seedu.guilttrip.testutil.TypicalEntries.BENSON;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.commons.exceptions.IllegalValueException;
//import seedu.guilttrip.model.entry.Description;
//
//public class JsonAdaptedEntryTest {
//    private static final String INVALID_NAME = "R@chel";
//    private static final String INVALID_PHONE = "+651234";
//    private static final String INVALID_ADDRESS = " ";
//    private static final String INVALID_EMAIL = "example.com";
//    private static final String INVALID_TAG = "#friend";
//
//    private static final String VALID_NAME = BENSON.getName().toString();
////    private static final String VALID_PHONE = BENSON.getPhone().toString();
////    private static final String VALID_EMAIL = BENSON.getEmail().toString();
////    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
//    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
//            .map(JsonAdaptedTag::new)
//            .collect(Collectors.toList());
//
//    @Test
//    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
//        JsonAdaptedEntry entry = new JsonAdaptedEntry(BENSON);
//        assertEquals(BENSON, entry.toModelType());
//    }
//
//    @Test
//    public void toModelType_invalidName_throwsIllegalValueException() {
//        JsonAdaptedEntry entry =
//                new JsonAdaptedEntry(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, entry::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullName_throwsIllegalValueException() {
//        JsonAdaptedEntry entry = new JsonAdaptedEntry(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, entry::toModelType);
//    }
//
////    @Test
////    public void toModelType_invalidPhone_throwsIllegalValueException() {
////        JsonAdaptedEntry entry =
////                new JsonAdaptedEntry(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
////        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
////        assertThrows(IllegalValueException.class, expectedMessage, entry::toModelType);
////    }
////
////    @Test
////    public void toModelType_nullPhone_throwsIllegalValueException() {
////        JsonAdaptedEntry entry = new JsonAdaptedEntry(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
////        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
////        assertThrows(IllegalValueException.class, expectedMessage, entry::toModelType);
////    }
////
////    @Test
////    public void toModelType_invalidEmail_throwsIllegalValueException() {
////        JsonAdaptedEntry entry =
////                new JsonAdaptedEntry(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
////        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
////        assertThrows(IllegalValueException.class, expectedMessage, entry::toModelType);
////    }
////
////    @Test
////    public void toModelType_nullEmail_throwsIllegalValueException() {
////        JsonAdaptedEntry entry = new JsonAdaptedEntry(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
////        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
////        assertThrows(IllegalValueException.class, expectedMessage, entry::toModelType);
////    }
////
////    @Test
////    public void toModelType_invalidAddress_throwsIllegalValueException() {
////        JsonAdaptedEntry entry =
////                new JsonAdaptedEntry(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
////        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
////        assertThrows(IllegalValueException.class, expectedMessage, entry::toModelType);
////    }
////
////    @Test
////    public void toModelType_nullAddress_throwsIllegalValueException() {
////        JsonAdaptedEntry entry = new JsonAdaptedEntry(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
////        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
////        assertThrows(IllegalValueException.class, expectedMessage, entry::toModelType);
////    }
//
//    @Test
//    public void toModelType_invalidTags_throwsIllegalValueException() {
//        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
//        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
//        JsonAdaptedEntry entry =
//                new JsonAdaptedEntry(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
//        assertThrows(IllegalValueException.class, entry::toModelType);
//    }
//
//}
