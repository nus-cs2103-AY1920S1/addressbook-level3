package seedu.address.model.transaction;

/**
 * Amount in terms of cents
 */
public class Amount {

    public int amount;

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

    public Amount addAmount(Amount amount) {
        final int newAmount = this.amount + amount.amount;
        return new Amount(newAmount);
    }

    public Amount subtractAmount(Amount amount) {
        final int newAmount = this.amount - amount.amount;
        return new Amount(newAmount);
    }

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

}
