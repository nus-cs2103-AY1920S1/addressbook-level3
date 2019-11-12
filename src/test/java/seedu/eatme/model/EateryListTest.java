package seedu.eatme.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_CHEAP;
import static seedu.eatme.testutil.Assert.assertThrows;
import static seedu.eatme.testutil.TypicalEateries.POPEYES;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.exceptions.DuplicateEateryException;
import seedu.eatme.testutil.EateryBuilder;

public class EateryListTest {

    private final EateryList eateryList = new EateryList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eateryList.getEateryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eateryList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEateryList_replacesData() {
        EateryList newData = getTypicalOpenEateryList();
        eateryList.resetData(newData);
        assertEquals(newData, eateryList);
    }

    @Test
    public void resetData_withDuplicateEateries_throwsDuplicateEateryException() {
        // Two eateries with the same identity fields
        Eatery editedAlice = new EateryBuilder(POPEYES).withTags(VALID_TAG_NO_PREFIX_CHEAP)
                .build();
        List<Eatery> newEateries = Arrays.asList(POPEYES, editedAlice);
        EateryListStub newData = new EateryListStub(newEateries);

        assertThrows(DuplicateEateryException.class, () -> eateryList.resetData(newData));
    }

    @Test
    public void hasEatery_nullEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eateryList.hasEatery(null));
    }

    @Test
    public void hasEatery_eateryNotInEateryList_returnsFalse() {
        assertFalse(eateryList.hasEatery(POPEYES));
    }

    @Test
    public void hasEatery_eateryInEateryList_returnsTrue() {
        eateryList.addEatery(POPEYES);
        assertTrue(eateryList.hasEatery(POPEYES));
    }

    @Test
    public void hasEatery_eateryWithSameIdentityFieldsInEateryList_returnsTrue() {
        eateryList.addEatery(POPEYES);
        Eatery editedAlice = new EateryBuilder(POPEYES).withTags(VALID_TAG_NO_PREFIX_CHEAP)
                .build();
        assertTrue(eateryList.hasEatery(editedAlice));
    }

    @Test
    public void getEateryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eateryList.getEateryList().remove(0));
    }
    /**
     * A stub ReadOnlyEateryList whose eateries list can violate interface constraints.
     */
    private static class EateryListStub implements ReadOnlyEateryList {
        private final ObservableList<Eatery> eateries = FXCollections.observableArrayList();
        private final ObservableList<Eatery> todos = FXCollections.observableArrayList();

        EateryListStub(Collection<Eatery> eateries) {
            this.eateries.setAll(eateries);
        }

        @Override
        public ObservableList<Eatery> getEateryList() {
            return eateries;
        }

        @Override
        public ObservableList<Eatery> getTodoList() {
            return todos;
        }
    }

}
