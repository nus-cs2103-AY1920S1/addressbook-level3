package seedu.jarvis.storage.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.exceptions.IllegalValueException;

/**
 * Tests the behaviour {@code JsonAdaptedIndex}.
 */
public class JsonAdaptedIndexTest {
    private static final Index VALID_INDEX = Index.fromZeroBased(1);

    @Test
    public void toModelType_validIndex_returnsIndex() throws Exception {
        JsonAdaptedIndex jsonAdaptedIndex = new JsonAdaptedIndex(VALID_INDEX);
        assertEquals(VALID_INDEX, jsonAdaptedIndex.toModelType());
    }

    @Test
    public void toModelType_invalidZeroBasedIndex_throwsIllegalValueException() {
        int invalidIndex = -1;
        JsonAdaptedIndex jsonAdaptedIndex = new JsonAdaptedIndex(invalidIndex);
        assertThrows(IllegalValueException.class, JsonAdaptedIndex.MESSAGE_INVALID_INDEX,
                jsonAdaptedIndex::toModelType);
    }
}
