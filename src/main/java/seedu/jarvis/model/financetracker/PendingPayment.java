package seedu.jarvis.model.financetracker;

/**
 * Creates a instance object of a payment owed to a person by the user or by a person to the user.
 */
public class PendingPayment {
    private String personName;
    private double moneyAmount;
    private String description;
    private PaymentDirection paymentDirection;

    /**
     * Constructs a pending payment with the name of the other person involved and the amount involved.
     * @param personName of the other person involved in the transaction
     * @param moneyAmount the amount involved in the transaction
     */
    public PendingPayment(String personName, double moneyAmount, String description) {
        this.personName = personName;
        this.moneyAmount = moneyAmount;
        this.description = description;
    }

    /**
     * Sets the payment direction.
     */
    public void setPersonOwesUser() {
        this.paymentDirection = PaymentDirection.USEROWES;
    }

    public void setUserInDebt() {
        this.paymentDirection = PaymentDirection.USERINDEBTTO;
    }

    /**
     * GETTER METHODS
     */
    public String getPersonName() {
        return this.personName;
    }

    public double getMoneyAmount() {
        return this.moneyAmount;
    }

    public String getDescription() {
        return this.description;
    }

    public PaymentDirection getPaymentDirection() {
        return this.paymentDirection;
    }

    @Override
    public String toString() {
        String str = this.personName + ", " + this.moneyAmount;
        return str;
    }
}
