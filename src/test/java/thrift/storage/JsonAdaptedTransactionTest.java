package thrift.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thrift.storage.JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT;
import static thrift.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import thrift.commons.exceptions.IllegalValueException;
import thrift.model.transaction.Description;
import thrift.model.transaction.Value;
import thrift.testutil.TypicalTransactions;

public class JsonAdaptedTransactionTest {
    private static final String INVALID_VALUE = ".00";
    private static final String INVALID_TAG = "A+";

    private static final String VALID_TYPE = "expense";
    private static final String VALID_DESCRIPTION = TypicalTransactions.LAKSA.getDescription().toString();
    private static final String VALID_VALUE = TypicalTransactions.LAKSA.getValue().toString();
    private static final String VALID_REMARK = "This is valid!";
    private static final String VALID_DATE = "10/10/2010";
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalTransactions.LAKSA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());


    @Test
    public void toModelType_validTransactionDetails_returnsTransaction() throws Exception {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(TypicalTransactions.PENANG_LAKSA);
        assertEquals(TypicalTransactions.PENANG_LAKSA.toString(), transaction.toModelType().toString());
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_TYPE, null, VALID_VALUE,
                        VALID_REMARK, VALID_DATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidValue_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_TYPE, VALID_DESCRIPTION, INVALID_VALUE,
                        VALID_REMARK, VALID_DATE, VALID_TAGS);
        String expectedMessage = Value.VALUE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullValue_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_TYPE, VALID_DESCRIPTION, null,
                        VALID_REMARK, VALID_DATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Value.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_TYPE, VALID_DESCRIPTION, VALID_VALUE,
                        VALID_REMARK, VALID_DATE, invalidTags);
        assertThrows(IllegalValueException.class, transaction::toModelType);
    }

}
