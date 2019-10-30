package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAutoExpenses.FOOD_AUTO_EXPENSE;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import seedu.address.testutil.AutoExpenseBuilder;

public class AutoExpenseTest {

    private static final Faker faker = new Faker();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        AutoExpense autoExpense = new AutoExpenseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> autoExpense.getTags().remove(VALID_TAG_FOOD));
    }

    @Test
    public void isSameAutoExpense() {
        // same object -> returns true
        assertTrue(FOOD_AUTO_EXPENSE.equals(FOOD_AUTO_EXPENSE));

        // null -> returns false
        assertFalse(FOOD_AUTO_EXPENSE.equals(null));

        // different desc -> returns false
        AutoExpense editedFoodAutoExp = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE).withDesc(faker.lorem().sentence())
                .build();
        assertFalse(FOOD_AUTO_EXPENSE.equals(editedFoodAutoExp));

        // // different name -> returns false
        // editedFoodAutoExp = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE).withDesc(VALID_DESC_CLOTHING_EXPENSE).build();
        // assertFalse(FOOD_AUTO_EXPENSE.isSamePerson(editedFoodAutoExp));

        // // same name, same phone, different attributes -> returns true
        // editedFoodAutoExp = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE).withEmail(VALID_EMAIL_BOB)
        //         .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FOOD).build();
        // assertTrue(FOOD_AUTO_EXPENSE.isSamePerson(editedFoodAutoExp));

        // // same name, same email, different attributes -> returns true
        // editedFoodAutoExp = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE).withPhone(VALID_PHONE_BOB)
        //         .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FOOD).build();
        // assertTrue(FOOD_AUTO_EXPENSE.isSamePerson(editedFoodAutoExp));

        // // same name, same phone, same email, different attributes -> returns true
        // editedFoodAutoExp = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE).withAddress(VALID_ADDRESS_BOB)
        //         .withTags(VALID_TAG_FOOD).build();
        // assertTrue(FOOD_AUTO_EXPENSE.isSamePerson(editedFoodAutoExp));
        // }

        // @Test
        // public void equals() {
        // // same values -> returns true
        // Person FOOD_AUTO_EXPENSECopy = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE).build();
        // assertTrue(FOOD_AUTO_EXPENSE.equals(FOOD_AUTO_EXPENSECopy));

        // // same object -> returns true
        // assertTrue(FOOD_AUTO_EXPENSE.equals(FOOD_AUTO_EXPENSE));

        // // null -> returns false
        // assertFalse(FOOD_AUTO_EXPENSE.equals(null));

        // // different type -> returns false
        // assertFalse(FOOD_AUTO_EXPENSE.equals(5));

        // // different person -> returns false
        // assertFalse(FOOD_AUTO_EXPENSE.equals(BOB));

        // // different name -> returns false
        // Person editedFOOD_AUTO_EXPENSE = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE)
        // .withDesc(VALID_DESC_CLOTHING_EXPENSE)
        //         .build();
        // assertFalse(FOOD_AUTO_EXPENSE.equals(editedAlice));

        // // different phone -> returns false
        // editedFOOD_AUTO_EXPENSE = new AutoExpenseBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        // assertFalse(FOOD_AUTO_EXPENSE.equals(editedAlice));

        // // different email -> returns false
        // editedAlice = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE).withEmail(VALID_EMAIL_BOB).build();
        // assertFalse(FOOD_AUTO_EXPENSE.equals(editedAlice));

        // // different address -> returns false
        // editedAlice = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE).withAddress(VALID_ADDRESS_BOB).build();
        // assertFalse(FOOD_AUTO_EXPENSE.equals(editedAlice));

        // // different tags -> returns false
        // editedAlice = new AutoExpenseBuilder(FOOD_AUTO_EXPENSE).withTags(VALID_TAG_FOOD).build();
        // assertFalse(FOOD_AUTO_EXPENSE.equals(editedAlice));
    }
}
