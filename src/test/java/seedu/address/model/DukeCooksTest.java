package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalDukeCooks;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.exceptions.DuplicateRecipeException;
import seedu.address.testutil.PersonBuilder;

public class DukeCooksTest {

    private final DukeCooks dukeCooks = new DukeCooks();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), dukeCooks.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dukeCooks.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDukeCooks_replacesData() {
        DukeCooks newData = getTypicalDukeCooks();
        dukeCooks.resetData(newData);
        assertEquals(newData, dukeCooks);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two recipes with the same identity fields
        Recipe editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Recipe> newRecipes = Arrays.asList(ALICE, editedAlice);
        DukeCooksStub newData = new DukeCooksStub(newRecipes);

        assertThrows(DuplicateRecipeException.class, () -> dukeCooks.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dukeCooks.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInDukeCooks_returnsFalse() {
        assertFalse(dukeCooks.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInDukeCooks_returnsTrue() {
        dukeCooks.addPerson(ALICE);
        assertTrue(dukeCooks.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInDukeCooks_returnsTrue() {
        dukeCooks.addPerson(ALICE);
        Recipe editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(dukeCooks.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> dukeCooks.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyDukeCooks whose recipes list can violate interface constraints.
     */
    private static class DukeCooksStub implements ReadOnlyDukeCooks {
        private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();

        DukeCooksStub(Collection<Recipe> recipes) {
            this.recipes.setAll(recipes);
        }

        @Override
        public ObservableList<Recipe> getPersonList() {
            return recipes;
        }
    }

}
