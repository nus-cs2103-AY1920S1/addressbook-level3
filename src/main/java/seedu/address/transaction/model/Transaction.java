package seedu.address.transaction.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.person.model.person.Person;

/**
 * Represents a transaction with its specified attributes.
 */
public class Transaction {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private LocalDate date;
    private String description;
    private String category;
    private double amount;
    private Person person;
    private String name;
    private String id;
    private boolean isReimbursed;

    /**
     * Initializes a Transaction with the given information.
     * @param date date of the transaction done.
     * @param description short description of the transaction.
     * @param category category of the transaction.
     * @param amount amount of money spent or gained for that transaction.
     * @param person person from the data base that is accountable for that transaction.
     * @param id id of the person to be used in populating the UI table.
     * @param isReimbursed boolean of whether the transaction amount paid by the person has been reimbursed.
     */
    public Transaction(String date, String description, String category,
                       double amount, Person person, int id, boolean isReimbursed) {
        this.date = LocalDate.parse(date, DATE_TIME_FORMATTER);
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.person = person;
        this.name = person.getName().toString();
        this.id = "" + id;
        this.isReimbursed = isReimbursed;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDate() {
        return date.format(DATE_TIME_FORMATTER);
    }

    public LocalDate getDateObject() {
        return this.date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = "" + i;
    }

    public boolean getStatus() {
        return this.isReimbursed;
    }

    public String getName() {
        return this.person.getName().toString();
    }

    public boolean getIsReimbursed() {
        return this.isReimbursed;
    }

    public void updateStatus() {
        isReimbursed = true;
    }

    private String isOne(boolean isReimbursed) {
        return isReimbursed ? "1" : "0";
    }

    /**
     *Formats the Transaction object to be written into a text file.
     * @return Formatted String.
     */
    public String toWriteIntoFile() {
        String msg = this.date.format(DATE_TIME_FORMATTER) + " | " + this.description + " | " + this.category
                + " | " + this.amount + " | " + this.person.getName() + " | " + isOne(this.isReimbursed);
        return msg;
    }

    /**
     * Formats the Transaction object to be shown as a response.
     * @return Formatted String.
     */
    public String toString() {
        String msg = "Date: " + this.date.format(DATE_TIME_FORMATTER) + "\nDescription: " + this.description
                + "\nCategory: "
                + this.category + "\nAmount: $" + this.amount + "\nPaid by: " + this.person.getName();
        return msg;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Transaction)) {
            return false;
        }
        Transaction editedTransaction = (Transaction) other;
        return this.person.equals(editedTransaction.getPerson())
                && this.description.equals(editedTransaction.getDescription())
                && this.category.equals(editedTransaction.getCategory())
                && this.amount == editedTransaction.getAmount()
                && this.getDate().equals(editedTransaction.getDate());
    }
}
