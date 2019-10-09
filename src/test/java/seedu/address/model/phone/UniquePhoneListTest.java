package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;
import static seedu.address.testutil.TypicalPhones.IPHONETWO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.phone.exceptions.DuplicatePhoneException;
import seedu.address.model.phone.exceptions.PhoneNotFoundException;
import seedu.address.testutil.PhoneBuilder;

class UniquePhoneListTest {

    private UniquePhoneList uniquePhoneList = new UniquePhoneList();

    @Test
    void contains_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePhoneList.contains(null));
    }

    @Test
    void contains_phoneNotInList_returnsFalse() {
        assertFalse(uniquePhoneList.contains(IPHONEONE));
    }

    @Test
    void contains_phoneInList_returnsTrue() {
        uniquePhoneList.add(IPHONEONE);
        assertTrue(uniquePhoneList.contains(IPHONEONE));
    }

    @Test
    void contains_phoneWithSameIdentityFieldsInList_returnsTrue() {
        uniquePhoneList.add(IPHONEONE);
        assertTrue(uniquePhoneList.contains(new PhoneBuilder(IPHONEONE).withBrand("Not iPhone").build()));
    }

    @Test
    void add_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePhoneList.add(null));
    }

    @Test
    void add_duplicatePhone_throwsDuplicatePhoneException() {
        uniquePhoneList.add(IPHONEONE);
        assertThrows(DuplicatePhoneException.class, () -> uniquePhoneList.add(IPHONEONE));
    }

    @Test
    void setPhone_nullTargetPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePhoneList.setPhone(null, IPHONEONE));
    }

    @Test
    void setPhone_nullEditedPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePhoneList.setPhone(IPHONEONE, null));
    }

    @Test
    void setPhone_targetPhoneNotInList_throwsPhoneNotFoundException() {
        assertThrows(PhoneNotFoundException.class, () -> uniquePhoneList.setPhone(IPHONETWO, IPHONEONE));
    }

    @Test
    void setPhone_editedPhoneIsSamePhone_success() {
        uniquePhoneList.add(IPHONEONE);
        uniquePhoneList.setPhone(IPHONEONE, IPHONEONE);
        UniquePhoneList expectedUniquePhoneList = new UniquePhoneList();
        expectedUniquePhoneList.add(IPHONEONE);
        assertEquals(uniquePhoneList, expectedUniquePhoneList);
    }

    @Test
    void setPhone_editedPhoneHasSameIdentity_success() {
        uniquePhoneList.add(IPHONEONE);
        Phone editedPhone = new PhoneBuilder(IPHONEONE).withBrand("Not iPhone").build();
        uniquePhoneList.setPhone(IPHONEONE, editedPhone);
        UniquePhoneList expectedUniquePhoneList = new UniquePhoneList();
        expectedUniquePhoneList.add(editedPhone);
        assertEquals(uniquePhoneList, expectedUniquePhoneList);
    }

    @Test
    void setPhone_editedPhoneHasDifferentIdentity_success() {
        uniquePhoneList.add(IPHONEONE);
        Phone editedPhone = IPHONETWO;
        uniquePhoneList.setPhone(IPHONEONE, editedPhone);
        UniquePhoneList expectedUniquePhoneList = new UniquePhoneList();
        expectedUniquePhoneList.add(editedPhone);
        assertEquals(uniquePhoneList, expectedUniquePhoneList);
    }

    @Test
    void setPhone_editedPhoneHasNonUniqueIdentity_throwsDuplicatePhoneException() {
        uniquePhoneList.add(IPHONEONE);
        uniquePhoneList.add(IPHONETWO);
        assertThrows(DuplicatePhoneException.class, () -> uniquePhoneList.setPhone(IPHONEONE, IPHONETWO));
    }

    @Test
    void remove_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePhoneList.remove(null));
    }

    @Test
    void remove_phoneDoesNotExist_throwsPhoneNotFoundException() {
        assertThrows(PhoneNotFoundException.class, () -> uniquePhoneList.remove(IPHONEONE));
    }

    @Test
    void remove_existingPhone_removesPhone() {
        uniquePhoneList.add(IPHONEONE);
        uniquePhoneList.remove(IPHONEONE);
        UniquePhoneList expectedUniquePhoneList = new UniquePhoneList();
        assertEquals(uniquePhoneList, expectedUniquePhoneList);
    }

    @Test
    void setPhones_nullUniquePhoneList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePhoneList.setPhones((UniquePhoneList) null));
    }

    @Test
    void setPhones_uniquePhoneList_replacesOwnListWithProvidedUniquePhoneList() {
        uniquePhoneList.add(IPHONETWO);
        UniquePhoneList expectedUniquePhoneList = new UniquePhoneList();
        expectedUniquePhoneList.add(IPHONEONE);
        uniquePhoneList.setPhones(expectedUniquePhoneList);
        assertEquals(uniquePhoneList, expectedUniquePhoneList);
    }

    @Test
    void setPhones_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePhoneList.setPhones((List<Phone>) null));
    }

    @Test
    void setPhones_list_replacesOwnListWithProvidedList() {
        List<Phone> phoneList = Collections.singletonList(IPHONETWO);
        uniquePhoneList.add(IPHONEONE);
        uniquePhoneList.setPhones(phoneList);
        UniquePhoneList expectedUniquePhoneList = new UniquePhoneList();
        expectedUniquePhoneList.add(IPHONETWO);
        assertEquals(uniquePhoneList, expectedUniquePhoneList);
    }

    @Test
    void setPhones_listWithDuplicatePhones_throwsDuplicatePhoneException() {
        List<Phone> listWithDuplicatePhones = Arrays.asList(IPHONEONE, IPHONEONE);
        assertThrows(DuplicatePhoneException.class, () -> uniquePhoneList.setPhones(listWithDuplicatePhones));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniquePhoneList.asUnmodifiableObservableList().remove(0));
    }

}
