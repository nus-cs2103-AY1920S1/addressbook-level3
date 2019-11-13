package budgetbuddy.model.attributes;

/**
 * Represents an amount with a positive/negative sign.
 */
public class SignedAmount extends Amount {
    private boolean isPositive;

    public SignedAmount(long amount, boolean isPositive) {
        super(amount);
        this.isPositive = isPositive;
    }

    public boolean isPositive() {
        return isPositive;
    }

    @Override
    public int compareTo(Amount other) {
        return Long.compare(amount, other.amount);
    }

    @Override
    public String toString() {
        String sign = isPositive ? "+" : "-";
        return sign + String.format("%s%d.%02d", CURRENCY_SIGN, amount / 100, amount % 100);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SignedAmount // instanceof handles nulls
                && amount == ((SignedAmount) other).amount
                && isPositive == ((SignedAmount) other).isPositive);
    }
}
