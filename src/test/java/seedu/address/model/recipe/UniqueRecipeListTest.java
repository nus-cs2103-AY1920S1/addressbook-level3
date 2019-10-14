package seedu.address.model.recipe;

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

import seedu.address.model.recipe.exceptions.DuplicateRecipeException;
import seedu.address.model.recipe.exceptions.RecipeNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueRecipeListTest {

    private final UniqueRecipeList uniqueRecipeList = new UniqueRecipeList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueRecipeList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueRecipeList.add(ALICE);
        assertTrue(uniqueRecipeList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRecipeList.add(ALICE);
        Recipe editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueRecipeList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueRecipeList.add(ALICE);
        assertThrows(DuplicateRecipeException.class, () -> uniqueRecipeList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipe(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipe(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(RecipeNotFoundException.class, () -> uniqueRecipeList.setRecipe(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.setRecipe(ALICE, ALICE);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(ALICE);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueRecipeList.add(ALICE);
        Recipe editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueRecipeList.setRecipe(ALICE, editedAlice);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(editedAlice);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.setRecipe(ALICE, BOB);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BOB);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.add(BOB);
        assertThrows(DuplicateRecipeException.class, () -> uniqueRecipeList.setRecipe(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(RecipeNotFoundException.class, () -> uniqueRecipeList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.remove(ALICE);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipes((UniqueRecipeList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueRecipeList.add(ALICE);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BOB);
        uniqueRecipeList.setRecipes(expectedUniqueRecipeList);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipes((List<Recipe>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueRecipeList.add(ALICE);
        List<Recipe> recipeList = Collections.singletonList(BOB);
        uniqueRecipeList.setRecipes(recipeList);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BOB);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Recipe> listWithDuplicateRecipes = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateRecipeException.class, () -> uniqueRecipeList.setRecipes(listWithDuplicateRecipes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueRecipeList.asUnmodifiableObservableList().remove(0));
    }
}
