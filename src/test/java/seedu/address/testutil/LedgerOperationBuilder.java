package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.LendMoney;
import seedu.address.model.transaction.ReceiveMoney;
import seedu.address.model.transaction.Split;
import seedu.address.model.util.Date;

/**
 * Utility class to build {@code Split}, {@code LendMoney} and {@code ReceiveMoney} operations
 */
public class LedgerOperationBuilder {
    public static final String DEFAULT_AMOUNT = "100";
    public static final String DEFAULT_DATE = "10102019";
    public static final String DEFAULT_DESCRIPTION = "kbbq dinner";
    public static final String DEFAULT_NAME1 = "Joel Loong";
    public static final String DEFAULT_NAME2 = "Diung YUchen";
    public static final String DEFAULT_NAME3 = "Bertrand";

    private Description description;
    private Amount amount;
    private Date date;
    private Person person;
    private UniquePersonList people;

    public LedgerOperationBuilder() {
        amount = new Amount(Double.parseDouble(DEFAULT_AMOUNT));
        date = new Date(DEFAULT_DATE);
        description = new Description(DEFAULT_DESCRIPTION);
        person = new Person(new Name(DEFAULT_NAME1));
        people = new UniquePersonList();
    }

    /**
     * Initializes the LedgerOperationBuildre with the data of {@code toCopy}.
     */
    public LedgerOperationBuilder(LedgerOperation toCopy) {
        amount = toCopy.getAmount().makePositive();
        description = toCopy.getDescription();
        date = toCopy.getDate();
        people = toCopy.getPeopleInvolved();
        if (people.size() == 1) {
            person = people.asUnmodifiableObservableList().get(0);
        }
    }

    /**
     * Sets the {@code amount} of the {@code LedgerOperation} that we are building.
     */
    public LedgerOperationBuilder withAmount(String amount) {
        this.amount = new Amount(Double.parseDouble(amount));
        return this;
    }

    /**
     * Sets the {@code date} of the {@code LedgerOperation} that we are building.
     */
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

    /**
     * Sets the {@code target} of the {@code LedgerOperation} that we are building
     * as a new Person with {@code name}
     */
    public LedgerOperationBuilder withPerson(String name) {
        this.person = new Person(new Name(name));
        return this;
    }

    /**
     * Sets the {@code target} of the {@code LedgerOperation} that we are building.
     */
    public LedgerOperationBuilder withPerson(Person bob) {
        this.person = bob;
        return this;
    }

    /**
     * Adds a new {@code Person} with {@code name} into the {@code UniquePersonList}
     * of the {@code LedgerOperation} that we are building.
     */
    public LedgerOperationBuilder addPerson(String name) {
        Person person = new Person(new Name(name));
        return this.addPerson(person);
    }

    /**
     * Adds a {@code Person} into the {@code UniquePersonList} of the {@code LedgerOperation} that we are building.
     */
    public LedgerOperationBuilder addPerson(Person person) {
        people.add(person);
        return this;
    }

    public UniquePersonList getPeople() {
        return people;
    }

    /**
     * Builds Split Transaction
     */
    public Split asSplit(int... shares) {
        List<Integer> shareList = Arrays.stream(shares).boxed().collect(Collectors.toList());
        assert shareList.size() == people.size() + 1 : "shares cannot be split equally among people";
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
     * @return
     */
    public ReceiveMoney asReceiveMoney() {
        return new ReceiveMoney(person, amount, date, description);
    }


}
