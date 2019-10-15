package seedu.savenus.model.food;

import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_RESTRICTIONS;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_EQUALS_TO;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_LESS_THAN;

import java.util.List;
import java.util.function.Predicate;

/**
 * Creates a new FoodFilter to filter all Foods not following the defined quantifiers.
 */
public class FoodFilter implements Predicate<Food> {

    private List<String> fieldList;
    private FieldComparator fieldComparator;

    /**
     * Constructs a Simple FoodFilter based on the fields given.
     * @param fieldList the list of fields given.
     */
    public FoodFilter(List<String> fieldList) {
        this.fieldList = fieldList;
        this.fieldComparator = new FieldComparator();
    }

    @Override
    public boolean test(Food food) {
        return doesFoodPassTest(food);
    }

    /**
     * Checks if the food fulfills certain quantifiers by testing it with all quantifiers.
     * @param food the food to be tested.
     * @return true if the food passes the tests. False if otherwise.
     */
    public boolean doesFoodPassTest(Food food) {
        for (int i = 0; i < fieldList.size(); i = i + 3) {
            String field = fieldList.get(i);
            String quantifier = fieldList.get(i + 1);
            String value = fieldList.get(i + 2);
            Field dummyField = createDummyField(field, value);
            Field thisField = food.getField(field);
            int comparisonFactor = fieldComparator.compare(thisField, dummyField);
            if (!quantifierMatches(quantifier, comparisonFactor)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the comparison factor matches the quantifier given.
     * @param quantifier the quantifier, being less, equals or more.
     * @param comparisonFactor the comparison factor, represented by an Integer.
     * @return true if the comparison matches the quantifier. False if otherwise.
     */
    public boolean quantifierMatches(String quantifier, int comparisonFactor) {
        if (quantifier.equals(QUANTIFY_EQUALS_TO)) {
            return comparisonFactor == 0;
        } else if (quantifier.equals(QUANTIFY_LESS_THAN)) {
            return comparisonFactor < 0;
        } else {
            return comparisonFactor > 0;
        }
    }

    /**
     * Creates a dummy field for comparison.
     * @param field the specific dummy field to be created.
     * @param value the value of the dummy field.
     * @return a simple dummy field.
     */
    public Field createDummyField(String field, String value) {
        switch (field) {
        case FIELD_NAME_CATEGORY:
            return new Category(value);

        case FIELD_NAME_DESCRIPTION:
            return new Description(value);

        case FIELD_NAME_LOCATION:
            return new Location(value);

        case FIELD_NAME_NAME:
            return new Name(value);

        case FIELD_NAME_OPENING_HOURS:
            return new OpeningHours(value);

        case FIELD_NAME_PRICE:
            return new Price(value);

        case FIELD_NAME_RESTRICTIONS:
            return new Restrictions(value);

        default:
            return null;
        }
    }

}
