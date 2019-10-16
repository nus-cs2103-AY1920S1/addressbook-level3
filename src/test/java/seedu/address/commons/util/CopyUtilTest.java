package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.CopyError;
import seedu.address.testutil.SerializableTestClass;

public class CopyUtilTest {

    @Test
    public void deepCopy_noExceptionThrown() throws CopyError {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        SerializableTestClass copy = CopyUtil.deepCopy(serializableTestClass);

        assertEquals(serializableTestClass, copy);
        assertNotSame(serializableTestClass, copy);
    }

    @Test
    public void deepCopyOfObservableList() {
        // Test with immutable elements
        ObservableList<Integer> originalInts = FXCollections.observableArrayList(1, 2, 3);
        ObservableList<Integer> copiedInts = CopyUtil.deepCopyOfObservableList(originalInts);

        assertEquals(originalInts, copiedInts);
        assertNotSame(originalInts, copiedInts);

        originalInts.clear();
        assertEquals(copiedInts.get(0), 1);
        copiedInts.add(4);
        assertTrue(originalInts.isEmpty());

        // Test with mutable elements
        ObservableList<Mutable> originalMutables = FXCollections.observableArrayList(new Mutable());
        ObservableList<Mutable> copiedMutables = CopyUtil.deepCopyOfObservableList(originalMutables);

        assertEquals(originalMutables, copiedMutables);
        assertNotSame(originalMutables, copiedMutables);

        copiedMutables.get(0).setData(100);
        assertTrue(originalMutables.get(0).getData() == 0);
        originalMutables.get(0).setData(50);
        assertTrue(copiedMutables.get(0).getData() == 100);
        assertTrue(originalMutables.get(0).getData() == 50);
    }

    private static class Mutable {
        private int data = 0;

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof Mutable) {
                Mutable o = (Mutable) other;
                return this.getData() == o.getData();
            } else {
                return false;
            }
        }
    }
}
