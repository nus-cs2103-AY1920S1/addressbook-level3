package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class UniqueListTest {

    private UniqueList<IdentifiableStub> uniqueList = new UniqueList<>();

    @Test
    void contains_nullObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.contains(null));
    }

    @Test
    void contains_objectNotInList_returnsFalse() {
        uniqueList.add(new IdentifiableStub(0));
        assertFalse(uniqueList.contains(new IdentifiableStub(1)));
    }

    @Test
    void contains_objectInList_returnsTrue() {
        IdentifiableStub obj = new IdentifiableStub(1);
        uniqueList.add(obj);
        assertTrue(uniqueList.contains(obj));
    }

    @Test
    void add_nullObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.add(null));
    }

    @Test
    void add_duplicateObject_throwsDuplicateIdentityException() {
        uniqueList.add(new IdentifiableStub(1));
        assertThrows(DuplicateIdentityException.class, () -> uniqueList.add(new IdentifiableStub(1)));
    }

    @Test
    void add_uniqueObject_objectAdded() {
        uniqueList.add(new IdentifiableStub(0));
        IdentifiableStub obj = new IdentifiableStub(1);
        assertFalse(uniqueList.contains(obj));
        uniqueList.add(obj);
        assertTrue(uniqueList.contains(obj));
    }

    @Test
    void set_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.set(null, new IdentifiableStub(1)));
    }

    @Test
    void set_nullEditedObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.set(new IdentifiableStub(1), null));
    }

    @Test
    void set_targetNotInList_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () ->
                uniqueList.set(new IdentifiableStub(1), new IdentifiableStub(0)));
    }

    @Test
    void set_targetNotSameAsEditedObjectAndEditedObjectIsDuplicate_throwsDuplicateIdentityException() {
        IdentifiableStub target = new IdentifiableStub(0);
        uniqueList.add(target);
        IdentifiableStub editedObject = new IdentifiableStub(1);
        uniqueList.add(new IdentifiableStub(1));
        assertThrows(DuplicateIdentityException.class, () -> uniqueList.set(target, editedObject));
    }

    @Test
    void set_targetNotSameAsEditedObjectAndEditedObjectIsNotDuplicate_replaceTargetWithEditedObject() {
        IdentifiableStub target = new IdentifiableStub(0);
        uniqueList.add(target);
        IdentifiableStub editedObject = new IdentifiableStub(1);
        uniqueList.set(target, editedObject);
        assertTrue(uniqueList.contains(editedObject));
    }

    @Test
    void set_targetSameAsEditedObject_replaceTargetWithEditedObject() {
        IdentifiableStub target = new IdentifiableStub(0);
        uniqueList.add(target);
        IdentifiableStub editedObject = new IdentifiableStub(0);
        uniqueList.set(target, editedObject);
        assertTrue(uniqueList.contains(editedObject));
    }

    @Test
    void remove_nullObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.remove(null));
    }

    @Test
    void remove_objectNotInList_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> uniqueList.remove(new IdentifiableStub(0)));
    }

    @Test
    void remove_objectInList_objectRemoved() {
        IdentifiableStub obj = new IdentifiableStub(0);
        uniqueList.add(obj);
        assertTrue(uniqueList.contains(obj));
        uniqueList.remove(obj);
        assertFalse(uniqueList.contains(obj));
    }

    @Test
    void setList_nullUniqueList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.setList((UniqueList<IdentifiableStub>) null));
    }

    @Test
    void setList_uniqueList_uniqueListIsReplaced() {
        UniqueList<IdentifiableStub> inputList = new UniqueList<>();
        IdentifiableStub obj = new IdentifiableStub(0);
        inputList.add(obj);
        uniqueList.setList(inputList);
        assertTrue(uniqueList.contains(obj));
    }

    @Test
    void setList_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.setList((List<IdentifiableStub>) null));
    }

    @Test
    void setList_listWithDuplicates_throwsDuplicateIdentityException() {
        List<IdentifiableStub> duplicateList =
                Arrays.asList(new IdentifiableStub(0), new IdentifiableStub(0));
        assertThrows(DuplicateIdentityException.class, () -> uniqueList.setList(duplicateList));
    }

    @Test
    void setList_listWithNoDuplicates_listIsReplaced() {
        IdentifiableStub obj1 = new IdentifiableStub(0);
        IdentifiableStub obj2 = new IdentifiableStub(1);
        List<IdentifiableStub> nonDuplicateList =
                Arrays.asList(obj1, obj2);
        uniqueList.setList(nonDuplicateList);
        assertTrue(uniqueList.contains(obj1));
        assertTrue(uniqueList.contains(obj2));
    }

    @Test
    void asUnmodifiableObservableList_modify_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueList.asUnmodifiableObservableList().remove(0));
    }

    private static class IdentifiableStub implements Identifiable<IdentifiableStub> {

        private int identity;

        private IdentifiableStub(int identity) {
            this.identity = identity;
        }

        @Override
        public boolean isSameAs(IdentifiableStub other) {
            return this.identity == other.identity;
        }

    }

}
