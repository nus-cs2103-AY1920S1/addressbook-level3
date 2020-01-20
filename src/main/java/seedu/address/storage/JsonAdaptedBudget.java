package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Money;
import seedu.address.model.finance.Spending;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Jackson-friendly version of {@link Budget}.
 */
public class JsonAdaptedBudget {

    private final String name;
    private final String amount;
    private final List<JsonAdaptedSpending> spendings = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given {@code name, @code amount, @code remainingAmount, @code spendings}.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("name") String name, @JsonProperty("amount") String amount, @JsonProperty("spendings") List<JsonAdaptedSpending> spendings) {
        requireAllNonNull(name, amount, spendings);
        this.name = name;
        this.amount = amount;
        this.spendings.addAll(spendings);
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        name = source.getName();
        amount = source.getMoney().toString();
        for (Spending spending : source.getSpendings()) {
            spendings.add(new JsonAdaptedSpending(spending));
        }
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public List<JsonAdaptedSpending> getSpendings() {
        return spendings;
    }

    /**
     * Converts this Jackson-friendly adapted Budget object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Budget.
     */
    public Budget toModelType() throws IllegalValueException, ParseException {
        if (!Money.isValidAmount(amount)) {
            throw new IllegalValueException(Budget.MESSAGE_CONSTRAINTS);
        }
        BigDecimal resultDouble = new BigDecimal(amount);
        List<Spending> resultSpendings = new ArrayList<>();
        for (JsonAdaptedSpending spending : spendings) {
            resultSpendings.add(spending.toModelType());
        }
        return new Budget(name, new Money(resultDouble), resultSpendings);
    }

}
