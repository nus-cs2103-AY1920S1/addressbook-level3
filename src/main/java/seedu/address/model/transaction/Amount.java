package seedu.address.model.transaction;

/**
 * Amount in terms of cents
 */
public class Amount {

    private int amount;

    public Amount(double amount) {
        this.amount = (int) Math.floor(amount * 100);
    }

    public Amount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns amount in cents
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Adds this.amount by amount.
     * @param amount Amount to be added.
     * @return New amount after addition.
     */
    public Amount addAmount(Amount amount) {
        final int newAmount = this.amount + amount.amount;
        return new Amount(newAmount);
    }

    /**
     * Subtracts this.amount by amount.
     * @param amount Amount to be subtracted.
     * @return New amount after subtraction.
     */
    public Amount subtractAmount(Amount amount) {
        final int newAmount = this.amount - amount.amount;
        return new Amount(newAmount);
    }

    /**
     * Divides amount by number of people.
     * @param numOfPeople Number of people for amount to be divided.
     * @return Equally (TO BE CHANGED) divided amount.
     */
    public Amount divideAmount(int numOfPeople) {
        final int newAmount = this.amount / numOfPeople;
        return new Amount(newAmount);
    }

    public void updateAmount(Amount amount) {
        this.amount = amount.amount;
    }

    @Override
    public String toString() {
        return String.format("%.2f", amount / 100.0);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof Amount
                && amount == ((Amount) obj).amount);
    }
}
