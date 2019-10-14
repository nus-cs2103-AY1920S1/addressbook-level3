package seedu.ichifund.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalPersons.ALICE;
import static seedu.ichifund.testutil.TypicalPersons.getTypicalFundBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.person.Person;
import seedu.ichifund.model.person.exceptions.DuplicatePersonException;
import seedu.ichifund.testutil.BudgetBuilder;
import seedu.ichifund.testutil.PersonBuilder;

public class FundBookTest {

    private final FundBook fundBook = new FundBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), fundBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> fundBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyFundBook_replacesData() {
        FundBook newData = getTypicalFundBook();
        fundBook.resetData(newData);
        assertEquals(newData, fundBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        Budget budget = new BudgetBuilder().build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Budget> budgets = Collections.singletonList(budget);
        FundBookStub newData = new FundBookStub(newPersons, budgets);

        assertThrows(DuplicatePersonException.class, () -> fundBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> fundBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInFundBook_returnsFalse() {
        assertFalse(fundBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInFundBook_returnsTrue() {
        fundBook.addPerson(ALICE);
        assertTrue(fundBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInFundBook_returnsTrue() {
        fundBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(fundBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> fundBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyFundBook whose persons list can violate interface constraints.
     */
    private static class FundBookStub implements ReadOnlyFundBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Budget> budgets = FXCollections.observableArrayList();

        FundBookStub(Collection<Person> persons, Collection<Budget> budgets) {
            this.persons.setAll(persons);
            this.budgets.setAll(budgets);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Budget> getBudgetList() {
            return budgets;
        }
    }

}
