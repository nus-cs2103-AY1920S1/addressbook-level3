package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

public class NameContainsCloseExpiryDatePredicate implements Predicate<Person> {
    private final int numberOfDays;

    public NameContainsCloseExpiryDatePredicate(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public boolean test(Person person) {
        String temp = "11/11/1111"; //groceryItem.getExpiryDate();
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
