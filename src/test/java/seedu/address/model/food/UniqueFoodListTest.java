package seedu.address.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.food.exceptions.DuplicateFoodException;
import seedu.address.model.food.exceptions.FoodNotFoundException;
import seedu.address.testutil.FoodBuilder;

public class UniqueFoodListTest {

    private final UniqueFoodList uniqueFoodList = new UniqueFoodList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueFoodList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueFoodList.add(ALICE);
        assertTrue(uniqueFoodList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFoodList.add(ALICE);
        Food editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueFoodList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueFoodList.add(ALICE);
        assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.add(ALICE));
    }

    @Test
    public void setFood_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(null, ALICE));
    }

    @Test
    public void setFood_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(ALICE, null));
    }

    @Test
    public void setFood_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.setFood(ALICE, ALICE));
    }

    @Test
    public void setFood_editedPersonIsSamePerson_success() {
        uniqueFoodList.add(ALICE);
        uniqueFoodList.setFood(ALICE, ALICE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(ALICE);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedPersonHasSameIdentity_success() {
        uniqueFoodList.add(ALICE);
        Food editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueFoodList.setFood(ALICE, editedAlice);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(editedAlice);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedPersonHasDifferentIdentity_success() {
        uniqueFoodList.add(ALICE);
        uniqueFoodList.setFood(ALICE, BOB);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(BOB);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueFoodList.add(ALICE);
        uniqueFoodList.add(BOB);
        assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFood(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueFoodList.add(ALICE);
        uniqueFoodList.remove(ALICE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoods_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((UniqueFoodList) null));
    }

    @Test
    public void setFoods_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueFoodList.add(ALICE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(BOB);
        uniqueFoodList.setFoods(expectedUniqueFoodList);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoods_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((List<Food>) null));
    }

    @Test
    public void setFoods_list_replacesOwnListWithProvidedList() {
        uniqueFoodList.add(ALICE);
        List<Food> foodList = Collections.singletonList(BOB);
        uniqueFoodList.setFoods(foodList);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(BOB);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoods_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Food> listWithDuplicateFoods = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFoods(listWithDuplicateFoods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFoodList.asUnmodifiableObservableList().remove(0));
    }
}
