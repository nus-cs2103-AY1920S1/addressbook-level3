package seedu.billboard.model.person;

import java.text.DecimalFormat;

public class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain a float number and it should not be blank";

//    public static final String VALIDATION_REGEX = "^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$";


    public float amount;

    public Amount(String amount) {
        this.amount = Float.parseFloat(amount);
    }

    public static boolean isValidAmount(String test) {
        try {
            System.out.println(test);
            Float.parseFloat(test);
            return true;
        }
        catch (NumberFormatException e) {
            System.out.println("not number");
            return false;
        }
//        return test.matches(VALIDATION_REGEX);
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
