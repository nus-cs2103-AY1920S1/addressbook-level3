package seedu.jarvis.model.financeTracker;

public class Purchase {
    private String description;
    private double moneySpent;
    private PersonPaid personPaid = null;

    public Purchase(String description, double moneySpent) {
        this.description =  description;
        this.moneySpent = moneySpent;
    }

    public void setPersonPaid(PersonPaid p) {
        this.personPaid = p;
    }

    /**
     * GETTER METHODS
     */
    public String getDescription() {
        return this.description;
    }

    public double getMoneySpent() {
        return this.moneySpent;
    }

    public PersonPaid getPersonPaid() {
        return this.personPaid;
    }

    @Override
    public String toString() {
        String str = "";
        if (personPaid == null) {
            str = this.description + " (" + this.moneySpent + ")";
        } else {
            str = this.description + " paid to " + this.personPaid + " (" + this.moneySpent + ")";
        }
        return str;
    }
}
