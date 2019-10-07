package calofit.testutil;

import calofit.model.AddressBook;
import calofit.model.meal.Meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static calofit.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Meal} objects to be used in tests.
 */
public class TypicalMeals {

    public static final Meal ALICE = new MealBuilder().withName("Alice Pauline")
            .withTags("friends").build();
    public static final Meal BENSON = new MealBuilder().withName("Benson Meier")
            .withTags("owesMoney", "friends").build();
    public static final Meal CARL = new MealBuilder().withName("Carl Kurz").build();
    public static final Meal DANIEL = new MealBuilder().withName("Daniel Meier").withTags("friends").build();
    public static final Meal ELLE = new MealBuilder().withName("Elle Meyer").build();
    public static final Meal FIONA = new MealBuilder().withName("Fiona Kunz").build();
    public static final Meal GEORGE = new MealBuilder().withName("George Best").build();

    // Manually added
    public static final Meal HOON = new MealBuilder().withName("Hoon Meier").build();
    public static final Meal IDA = new MealBuilder().withName("Ida Mueller").build();

    // Manually added - Meal's details found in {@code CommandTestUtil}
    public static final Meal AMY = new MealBuilder().withName(VALID_NAME_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Meal BOB = new MealBuilder().withName(VALID_NAME_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMeals() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Meal meal : getTypicalMeals()) {
            ab.addMeal(meal);
        }
        return ab;
    }

    public static List<Meal> getTypicalMeals() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
