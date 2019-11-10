package seedu.sugarmummy.model.recmf;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_SORT_ASC;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_SORT_DES;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Encapsulates a {@code} Comparator to specially handle sorting of foods.
 */
public class FoodComparator implements Comparator<Food> {

    public static final String MESSAGE_CONSTRAINTS = "The sorting order should be one of the following types:\n"
            + "(Sort by) fn: food name; ft: food type; ca: calorie; gi: glycemic index; su: sugar; fa: fat";
    public static final String MESSAGE_PREFIX_CONSTRAINTS = "The sorting order can only be "
            + "either ascending or descending:\n"
            + "Namely, " + PREFIX_SORT_ASC + "and " + PREFIX_SORT_DES + " cannot be both present.";

    public static final String DEFAULT_SORT_ORDER_STRING = SortOrderType.SORT_ORDER_FOOD_TYPE.getSortOrderString();

    private static final Map<String, SortOrderType> sortOrderTypes = Arrays.stream(SortOrderType.values())
            .collect(Collectors.toMap(type -> type.getSortOrderString(), type -> type));

    private Comparator<Food> foodComparator;

    private FoodComparator(Comparator<Food> foodComparator) {
        this.foodComparator = foodComparator;
    }

    /**
     * This is the only public constructor specifying the sort order represented by a {@code String}.
     */
    public FoodComparator(String sortOrderString) {
        requireNonNull(sortOrderString);
        checkArgument(isValidSortOrderType(sortOrderString), MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_CONSTRAINTS);

        assert sortOrderTypes.containsKey(sortOrderString);

        SortOrderType sortOrderType = sortOrderTypes.get(sortOrderString);
        switch (sortOrderType) {
        case SORT_ORDER_FOOD_NAME:
            this.foodComparator = (food1, food2) -> food1.getFoodName().compareTo(food2.getFoodName());
            break;
        case SORT_ORDER_FOOD_TYPE:
            this.foodComparator = (food1, food2) -> food1.getFoodType().compareTo(food2.getFoodType());
            break;
        case SORT_ORDER_CALORIE:
            this.foodComparator = (food1, food2) -> food1.getCalorie().compareTo(food2.getCalorie());
            break;
        case SORT_ORDER_GI:
            this.foodComparator = (food1, food2) -> food1.getGi().compareTo(food2.getGi());
            break;
        case SORT_ORDER_SUGAR:
            this.foodComparator = (food1, food2) -> food1.getSugar().compareTo(food2.getSugar());
            break;
        case SORT_ORDER_FAT:
            this.foodComparator = (food1, food2) -> food1.getFat().compareTo(food2.getFat());
            break;
        default:
            assert false : sortOrderTypes.toString() + "does not fit in any sort order case";
            break;
        }
    }

    /**
     * Returns true if a given string is a valid food name. A name is considered valid if it does not contain special
     * characters and its length is less than 30 characters.
     */
    private static boolean isValidSortOrderType(String test) {
        return sortOrderTypes.containsKey(test);
    }

    @Override
    public FoodComparator reversed() {
        return new FoodComparator(this.foodComparator.reversed());
    }

    @Override
    public int compare(Food food1, Food food2) {
        return foodComparator.compare(food1, food2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FoodComparator) {
            FoodComparator another = (FoodComparator) obj;
            return this.foodComparator.equals(another.foodComparator);
        } else {
            return false;
        }
    }

    /**
     * Enumerates all possible sort order types for sorting foods.
     */
    private enum SortOrderType {
        SORT_ORDER_FOOD_NAME("fn"),
        SORT_ORDER_FOOD_TYPE("ft"),
        SORT_ORDER_CALORIE("ca"),
        SORT_ORDER_GI("gi"),
        SORT_ORDER_SUGAR("su"),
        SORT_ORDER_FAT("fa");

        private final String sortOrderString;

        SortOrderType(String sortOrderString) {
            this.sortOrderString = sortOrderString;
        }

        String getSortOrderString() {
            return sortOrderString;
        }
    }

}
