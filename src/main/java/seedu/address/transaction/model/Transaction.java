package seedu.address.transaction.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import seedu.address.person.model.person.Person;

public class Transaction {
    private final DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
    private LocalDate date;
    private String description;
    private String category;
    private double amount;
    private Person person;
    private String name;
    private String id;
    private boolean isReimbursed;

    public Transaction(String date, String description, String category,
                       double amount, Person person, int i, boolean isReimbursed) {
        this.date = LocalDate.parse(date, myFormatter);
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.person = person;
        this.name = person.getName().toString();
        this.id = "" + i;
        this.isReimbursed = isReimbursed;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDate() {
        return date.format(myFormatter);
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

    public void updateStatus() {
        isReimbursed = true;
    }

    private String isOne(boolean isReimbursed) {
        return isReimbursed? "1": "0";
    }

    public String toWriteIntoFile() {
        String msg = this.date.format(myFormatter) + " | " + this.description + " | " + this.category +
                " | " + this.amount + " | " + this.person.getName() + " | " + isOne(this.isReimbursed);
        return msg;
    }

    public String toString() {
        String msg = "Date: " + this.date.format(myFormatter) + "\nDescription: " + this.description + "\nCategory: " + this.category +
                "\nAmount: $" + this.amount + "\nPaid by: " + this.person.getName();
        return msg;
    }

    public String getName() {
        return this.person.getName().toString();
    }

    public boolean getIsReimbursed() {
        return this.isReimbursed;
    }

    public boolean equals(Transaction editedTransaction) {
        return this.person.equals(editedTransaction.getPerson()) &&
                this.description.equals(editedTransaction.getDescription()) &&
                this.category.equals(editedTransaction.getCategory()) &&
                this.amount == editedTransaction.getAmount() &&
                this.getDate().equals(editedTransaction.getDate());

    }


}
