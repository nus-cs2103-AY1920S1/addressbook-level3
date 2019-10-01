package seedu.address.transaction.model;

public class Transaction {
    private String dateTime;
    private String description;
    private String category;
    private double amount;
    private String person;

    public Transaction(String dataTime, String description, String category,
                       double amount, String person) {
        this.dateTime = dataTime;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.person = person;
    }

    public String toWriteIntoFile() {
        String msg = this.dateTime + " | " + this.description + " | " + this.category +
                " | " + this.amount + " | " + this.person;
        return msg;
    }

    public String toString() {
        String msg = this.dateTime + " | " + this.description + " | " + this.category +
                " | " + this.amount + " | " + this.person;
        return msg;
    }
}
