package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalPersons.ALICE;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;
import static seedu.jarvis.testutil.cca.TypicalCcas.GUITAR_ENSEMBLE;
import static seedu.jarvis.testutil.cca.TypicalEquipmentList.CANOEING_EQUIPMENT_LIST;

import org.junit.jupiter.api.Test;
import seedu.jarvis.model.cca.exceptions.CcaNotFoundException;
import seedu.jarvis.model.cca.exceptions.DuplicateCcaException;
import seedu.jarvis.testutil.cca.CcaBuilder;

public class CcaListTest {

    private final CcaList ccaList = new CcaList();

    @Test
    public void contains_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaList.contains(null));
    }

    @Test
    public void contains_ccaNotInList_returnsFalse() {
        assertFalse(ccaList.contains(CANOEING));
    }


    @Test
    public void contains_ccaInList_returnsTrue() {
        ccaList.addCca(CANOEING);
        assertTrue(ccaList.contains(CANOEING));
    }

    @Test
    public void contains_ccaWithSameIdentityFieldsInList_returnsTrue() {
        ccaList.addCca(CANOEING);
        Cca editedCanoeing = new CcaBuilder(CANOEING).withEquipmentList(CANOEING_EQUIPMENT_LIST).build();
        assertTrue(ccaList.contains(editedCanoeing));
    }

    @Test
    public void add_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaList.addCca(null));
    }

    @Test
    public void add_duplicateCca_throwsDuplicateCcaException() {
        ccaList.addCca(CANOEING);
        assertThrows(DuplicateCcaException.class, () -> ccaList.addCca(CANOEING));
    }

    @Test
    public void setCca_nullTargetCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaList.updateCca(null, CANOEING));
    }

    @Test
    public void setPerson_nullEditedCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaList.updateCca(CANOEING, null));
    }

    @Test
    public void setCca_targetCcaNotInList_throwsCcaNotFoundException() {
        assertThrows(CcaNotFoundException.class, () -> ccaList.updateCca(CANOEING, CANOEING));
    }

    @Test
    public void setCca_editedCcaIsSameCca_success() {
        ccaList.addCca(CANOEING);
        ccaList.updateCca(CANOEING, CANOEING);
        CcaList expectedCcaList = new CcaList();
        expectedCcaList.addCca(CANOEING);
        assertEquals(expectedCcaList, ccaList);
    }

    @Test
    public void setCca_editedCcaHasSameIdentity_success() {
        ccaList.addCca(CANOEING);
        Cca editedCanoeing = new CcaBuilder(CANOEING).withEquipmentList(CANOEING_EQUIPMENT_LIST).build();
        ccaList.updateCca(CANOEING, editedCanoeing);
        CcaList expectedCcaList = new CcaList();
        expectedCcaList.addCca(editedCanoeing);
        assertEquals(expectedCcaList, ccaList);
    }

    @Test
    public void setCca_editedCcaHasDifferentIdentity_success() {
        ccaList.addCca(CANOEING);
        ccaList.updateCca(CANOEING, GUITAR_ENSEMBLE);
        CcaList expectedCcaList = new CcaList();
        expectedCcaList.addCca(GUITAR_ENSEMBLE);
        assertEquals(expectedCcaList, ccaList);
    }

    @Test
    public void setCca_editedCcaHasNonUniqueIdentity_throwsDuplicateCcaException() {
        ccaList.addCca(CANOEING);
        ccaList.addCca(GUITAR_ENSEMBLE);
        assertThrows(DuplicateCcaException.class, () -> ccaList.updateCca(CANOEING, GUITAR_ENSEMBLE));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> ccaList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        ccaList.add(ALICE);
        ccaList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, ccaList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_ccaList_replacesOwnListWithProvidedUniquePersonList() {
        ccaList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        ccaList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, ccaList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        ccaList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        ccaList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, ccaList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> ccaList.setPersons(listWithDuplicatePersons));
    }

}
