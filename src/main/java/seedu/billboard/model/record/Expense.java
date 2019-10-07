package seedu.billboard.model.record;

import seedu.billboard.commons.exceptions.DataConversionException;

/**
 * The class Expense extends the Abstract class Record, since Expense is a Record.
 */
public class Expense extends Record {
    private static final String EXPENSE_TYPE = "expense";
    private int amount;

    /**
     * Constructor for Expense.
     * Throw an error if there is no amount description or format is wrong.
     *
     * @param description  Description for the Expense.
     * @param amount  Amount for the Expense Record.
     * @throws DataConversionException  If details missing or in wrong format.
     */
    public Expense(String description, String amount) throws DataConversionException {
        super(description);
        try {
            if (amount.isBlank()) {
            //throw new DataConversionException();
            }
            this.amount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            //throw new DataConversionException();
        }
    }

    /**
     * Constructor for Expense.
     * Throw error if there is no amount description or wrong format.
     *
     * @param desc  Description for the Expense Record.
     * @throws DataConversionException  If details missing or in wrong format.
     */
    public Expense(String desc) throws DataConversionException {
        super();
        if (desc.trim().isBlank()) {
            //throw new DataConversionException(EXPENSE_TYPE);
        }
        try {
            String[] details = desc.split("/", 2);
            description = details[0];
            amount = Integer.parseInt(details[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            //throw new DataConversionException(EXPENSE_TYPE);
        }
    }

    /**
     * Constructor for Expense Record.
     * Throw error if there is no time description or wrong format.
     *
     * @param recordDetails  Details for the Expense Record.
     * @throws DataConversionException  If details missing or in wrong format.
     */
    public Expense(String[] recordDetails) throws DataConversionException {
        super();
        try {
            description = recordDetails[1];
            amount = Integer.parseInt(recordDetails[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            //throw new DataConversionException(super());
        }
    }

    public String getPrintableMsg() {
        return "EX | " + description + " | " + amount;
    }

    @Override
    public String toString() {
        return String.format("[EX] %s (amount: %d)", description, amount);
    }
}
