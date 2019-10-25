package seedu.ifridge.model.food;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsCloseExpiryDatePredicate implements Predicate<GroceryItem> {
    private final int numberOfDays;

    public NameContainsCloseExpiryDatePredicate(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public boolean test(GroceryItem groceryItem) {
        String temp = groceryItem.getExpiryDate().toString();
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isExpiring(date);
    }
    /**
     * Checks if an item has expired or is expiring.\
     * @param date expiry date
     */
    public boolean isExpiring(Date date) {
        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();
        int diffDays = (int) ((date.getTime() - current.getTime()) / (24 * 60 * 60 * 1000));
        return diffDays <= numberOfDays || date.before(current);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate); // instanceof handles nulls
    }
}
