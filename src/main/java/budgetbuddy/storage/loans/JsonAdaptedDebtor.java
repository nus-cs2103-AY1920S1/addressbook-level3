package budgetbuddy.storage.loans;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.person.Person;

/**
 * Jackson-friendly version of {@link budgetbuddy.model.loan.Debtor}.
 */
public class JsonAdaptedDebtor {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Debtor's %s field is missing!";
    public static final String CREDITOR_AMOUNT_NUMBERS_MISMATCH =
            "The number of creditors' names does not match the number of creditors' amounts.";

    private final String debtorName;
    private final List<String> creditorsNames = new ArrayList<>();
    private final List<Long> creditorsAmounts = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDebtor} with the given debtor details.
     */
    @JsonCreator
    public JsonAdaptedDebtor(@JsonProperty("debtorName") String debtorName,
                             @JsonProperty("creditorsNames") List<String> creditorsNames,
                             @JsonProperty("creditorsAmounts") List<Long> creditorsAmounts) {
        requireAllNonNull(debtorName, creditorsNames, creditorsAmounts);
        this.debtorName = debtorName;
        this.creditorsNames.addAll(creditorsNames);
        this.creditorsAmounts.addAll(creditorsAmounts);
    }

    /**
     * Converts a given {@code Debtor} into this class for Jackson use.
     */
    public JsonAdaptedDebtor(Debtor source) {
        debtorName = source.getDebtor().getName().toString();
        source.getCreditors().forEach((person, amount) -> {
            creditorsNames.add(person.getName().toString());
            creditorsAmounts.add(amount.toLong());
        });
    }

    /**
     * Converts this Jackson-friendly adapted debtor object into the model's {@code Debtor} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted debtor.
     */
    public Debtor toModelType() throws IllegalValueException {
        Person debtor = new Person(getValidatedName(debtorName));
        HashMap<Person, Amount> creditors = new HashMap<>();
        if (creditorsNames.size() != creditorsAmounts.size()) {
            throw new IllegalValueException(CREDITOR_AMOUNT_NUMBERS_MISMATCH);
        }
        for (int i = 0; i < creditorsNames.size(); i++) {
            creditors.put(
                    new Person(getValidatedName(creditorsNames.get(i))),
                    getValidatedAmount(creditorsAmounts.get(i)));
        }
        return new Debtor(debtor, creditors);
    }

    /**
     * Validates and converts the given name into the model's {@code Name} object.
     * @param name The name as a string.
     * @return The validated and converted name.
     * @throws IllegalValueException If validation fails.
     */
    private Name getValidatedName(String name) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    /**
     * Validates and converts the given amount into the model's {@code Amount} object.
     * @param amount The amount as a {@code long}.
     * @return The validated and converted amount.
     * @throws IllegalValueException If validation fails.
     */
    private Amount getValidatedAmount(Long amount) throws IllegalValueException {
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        } else if (amount <= 0) {
            throw new IllegalValueException(Loan.MESSAGE_AMOUNT_POSITIVE_CONSTRAINT);
        }
        return new Amount(amount);
    }
}
