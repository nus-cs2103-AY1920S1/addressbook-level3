package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.budget.Budget;
import seedu.address.model.spending.Spending;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_SPENDING = "Spendings list contains duplicate Spending(s).";

    private final List<JsonAdaptedSpending> spendings = new ArrayList<>();
    private String budget;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given spendings.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("budget") String budget,
            @JsonProperty("spendings") List<JsonAdaptedSpending> spendings) {
        this.spendings.addAll(spendings);
        this.budget = budget;
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        spendings.addAll(source.getSpendingList().stream().map(JsonAdaptedSpending::new).collect(Collectors.toList()));
        budget = "" + source.getBudget().getValue();
    }

    /**
     * Converts this MoneyGoWhere into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedSpending jsonAdaptedSpending : spendings) {
            Spending spending = jsonAdaptedSpending.toModelType();
            if (addressBook.hasSpending(spending)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SPENDING);
            }
            addressBook.addSpending(spending);
        }

        addressBook.setBudget(new Budget(budget));

        return addressBook;
    }

}
