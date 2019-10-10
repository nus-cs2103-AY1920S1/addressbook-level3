package seedu.billboard.model.expense;

import java.text.DecimalFormat;

public class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain a float number and it should not be blank";

//TODO: Add parsing money logic
    public float amount;

    public Amount(String amount) {
        this.amount = Float.parseFloat(amount);
    }

    public static boolean isValidAmount(String test) {
        try {
            Float.parseFloat(test);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amount == ((Amount) other).amount); // state check
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return String.valueOf(decimalFormat.format(amount));
    }
}
