package seedu.address.transaction.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.util.DummyNamedPerson;

public class Transaction {
    private LocalDate date;
    private String description;
    private String category;
    private double amount;
    private Person person;
    private final DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    public Transaction(String date, String description, String category,
                       double amount, String name) {
        this.date = LocalDate.parse(date, myFormatter);
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.person = new DummyNamedPerson(name).getDummy();
    }

    public Person getPerson() {
        return this.person;
    }

    public String toWriteIntoFile() {
        String msg = this.date.format(myFormatter) + " | " + this.description + " | " + this.category +
                " | " + this.amount + " | " + this.person.getName();
        return msg;
    }

    public String toString() {
        String msg = this.date.format(myFormatter) + " | " + this.description + " | " + this.category +
                " | " + this.amount + " | " + this.person.getName();
        return msg;
    }
}
