package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.CopyException;
import seedu.address.testutil.SerializableTestClass;

public class CopyUtilTest {

    @Test
    public void copyObject_noExceptionThrown() throws CopyException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        SerializableTestClass copy = CopyUtil.deepCopy(serializableTestClass);

        assertEquals(serializableTestClass, copy);
        assertNotSame(serializableTestClass, copy);
    }
}
