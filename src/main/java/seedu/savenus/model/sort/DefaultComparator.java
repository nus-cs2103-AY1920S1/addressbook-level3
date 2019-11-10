package seedu.savenus.model.sort;

import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;

import java.util.ArrayList;
import java.util.Arrays;

//@@author seanlowjk
/**
 * A Simple Comparator that compares by category, name and then price.
 */
public class DefaultComparator extends FoodComparator {

    public DefaultComparator() {
        super(new ArrayList<>(Arrays.asList(FIELD_NAME_CATEGORY, "ASC", FIELD_NAME_NAME, "ASC",
                FIELD_NAME_PRICE, "ASC")));
    }

}
