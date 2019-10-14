package seedu.address.model.food;

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
        return getDiffDays(date) <= numberOfDays;
    }

    public int getDiffDays(Date date) {
        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();
        long diff = date.getTime() - current.getTime();
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        return diffDays;
    }
}
