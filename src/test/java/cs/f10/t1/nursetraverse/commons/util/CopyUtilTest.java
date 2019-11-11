package cs.f10.t1.nursetraverse.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.exceptions.CopyError;
import cs.f10.t1.nursetraverse.testutil.Mutable;
import cs.f10.t1.nursetraverse.testutil.SerializableTestClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}
