package seedu.address.transaction.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import seedu.address.person.model.person.Person;

public class Transaction {
    private LocalDate date;
    private String description;
    private String category;
    private double amount;
    private Person person;
    private String name;
    private String id;
    private final DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    public Transaction(String date, String description, String category,
                       double amount, Person person, int i) {
        this.date = LocalDate.parse(date, myFormatter);
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.person = person;
        this.name = person.getName().toString();
        this.id = "" + i;
    }

    public Person getPerson() {
        return this.person;
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

    public void setPerson(Person person) {
        this.person = person;
    }

    public String toWriteIntoFile() {
        String msg = this.date.format(myFormatter) + " | " + this.description + " | " + this.category +
                " | " + this.amount + " | " + this.person.getName();
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


    public boolean equals(Transaction editedTransaction) {
        return this.person.equals(editedTransaction.getPerson()) &&
                this.description.equals(editedTransaction.getDescription()) &&
                this.category.equals(editedTransaction.getCategory()) &&
                this.amount == editedTransaction.getAmount() &&
                this.getDate().equals(editedTransaction.getDate());

    }

}
