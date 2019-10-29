package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.model.category.Category;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.*;
import seedu.address.model.util.Date;

public class LedgerOperationBuilder {
    public static final String DEFAULT_AMOUNT = "100";
    public static final String DEFAULT_DATE = "10102019";
    public static final String DEFAULT_DESCRIPTION = "kbbq dinner";
    public static final String DEFAULT_NAME = "Joel Loong";

    private Description description;
    private Amount amount;
    private Date date;
    private Person person;
    private UniquePersonList people;

    public LedgerOperationBuilder() {
        amount = new Amount(Double.parseDouble(DEFAULT_AMOUNT));
        date = new Date(DEFAULT_DATE);
        description = new Description(DEFAULT_DESCRIPTION);
        person = new Person(new Name(DEFAULT_NAME));
        people = new UniquePersonList();
    }

    /**
     * Initializes the LedgerOperationBuildre with the data of {@code toCopy}.
     */
    public LedgerOperationBuilder(LedgerOperation toCopy) {
        amount = toCopy.getAmount();
        description = toCopy.getDescription();
        date = toCopy.getDate();
    }

    public LedgerOperationBuilder withAmount(String amount) {
        this.amount = new Amount(Double.parseDouble(amount));
        return this;
    }

    public LedgerOperationBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code LedgerOperation} that we are building.
     */
    public LedgerOperationBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public LedgerOperationBuilder withPerson(String name) {
        this.person = new Person(new Name(name));
        return this;
    }

    public LedgerOperationBuilder addPerson(String name) {
        Person person = new Person(new Name(name));
        people.add(person);
        return this;
    }

    /**
     * Builds Split Transaction
     */
    public LedgerOperation asSplit(int... shares) {
        List<Integer> shareList = Arrays.stream(shares).boxed().collect(Collectors.toList());
        assert shareList.size() == people.size() : "shares cannot be split equally among people";
        return new Split(amount, date, description, shareList, people);
    }

    /**
     * Builds LendMoney Transaction
     */
    public LedgerOperation asLendMoney() {
        return new LendMoney(person, amount, date, description);
    }

    /**
     * Builds ReceiveMoney Transaction
     */
    public LedgerOperation asReceiveMoney() {
        return new ReceiveMoney(person, amount, date, description);
    }
}
