package budgetbuddy.storage.accounts;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.TransactionList;


/**
 * Jackson-friendly version of {@link budgetbuddy.model.account.Account}.
 */
public class JsonAdaptedAccount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Account's %s field is missing!";

    private final String name;
    private final String description;
    private final String transactionList;

    /**
     * Constructs a {@code JsonAdaptedAccount} with the given account details.
     */
    public JsonAdaptedAccount(@JsonProperty("name") String name,
                           @JsonProperty("description") String description,
                           @JsonProperty("transactionList") String transactionList) {
        requireAllNonNull(name, description, transactionList);
        this.name = name;
        this.description = description;
        this.transactionList = transactionList;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedAccount(Account source) {
        name = source.getName().toString();
        description = source.getDescription().toString();
        // TODO !!!
        transactionList = source.getTransactionList().toString();
    }

    /**
     * Converts this Jackson-friendly adapted account object into the model's {@code Account} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted account.
     */
    public Account toModelType() throws IllegalValueException {
        return new Account(checkName(), checkDescription(), checkTransactionList());
    }

    /**
     * Checks that adapted name can be converted into model's {@code Name} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Name checkName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    /**
     * Checks that adapted description can be converted into model's {@code Description} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Description checkDescription() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(description);
    }

    /**
     * Checks that adapted transactionList can be converted into model's {@code transactionList} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private TransactionList checkTransactionList() throws IllegalValueException {
        if (transactionList == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TransactionList.class.getSimpleName()));
        }
        if (!Description.isValidDescription(transactionList)) {
            throw new IllegalValueException(TransactionList.MESSAGE_CONSTRAINTS);
        }
        // TODO Deserialise transactions...
        return new TransactionList();
    }
}
