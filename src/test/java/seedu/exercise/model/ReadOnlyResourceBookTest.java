package seedu.exercise.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.typicalutil.TypicalExercises;

public class ReadOnlyResourceBookTest {

    private ReadOnlyResourceBook<Exercise> resource;

    @BeforeEach
    public void setUp() {
        resource = TypicalExercises.getTypicalExerciseBook();
    }

    @Test
    public void getResourceIndex_itemNotPresentInList_returnsNegativeInt() {
        assertTrue(resource.getResourceIndex(TypicalExercises.NOT_ADDED_TO_ANY_LIST_EXERCISE) < 0);
    }

    @Test
    public void getResourceList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> resource.getSortedResourceList().remove(0));
    }
}
